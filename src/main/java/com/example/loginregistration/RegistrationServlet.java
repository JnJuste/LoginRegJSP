package com.example.loginregistration;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/register")
public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*
        PrintWriter out = response.getWriter();
        out.print("Working");
        Testing Purposes*/

        String uname = request.getParameter("name");
        String uemail = request.getParameter("email");
        String upassword = request.getParameter("pass");
        String umobile = request.getParameter("contact");

        RequestDispatcher dispatcher = null;
        Connection conn = null;
        /*
        PrintWriter out = response.getWriter();
        out.println(uname);
        out.println(uemail);
        out.println(upassword);
        out.println(umobile);
        Testing Purposes*/

        //Validation of Backend
        if(uname == null || uname.isEmpty()){
            request.setAttribute("status", "invalidName");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        if(uemail == null || uemail.isEmpty()){
            request.setAttribute("status", "invalidEmail");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        if(upassword == null || upassword.isEmpty()){
            request.setAttribute("status", "invalidPassword");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }
        if(umobile == null || umobile.isEmpty()){
            request.setAttribute("status", "invalidMobilePhone");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        } else if(umobile.length() > 10)  {
            request.setAttribute("status", "invalidMobileLength");
            dispatcher = request.getRequestDispatcher("registration.jsp");
            dispatcher.forward(request, response);
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginregjsp?useSSL=false", "root", "AUCA@2023");
            PreparedStatement pst = conn.prepareStatement("INSERT INTO USERS (uname, upassword, uemail, umobile) VALUES (?,?,?,?) ");
            pst.setString(1, uname);
            pst.setString(2, upassword);
            pst.setString(3, uemail);
            pst.setString(4, umobile);

            int rowAffected = pst.executeUpdate();
            dispatcher = request.getRequestDispatcher("registration.jsp");
            if (rowAffected > 0) {
                request.setAttribute("status", "success");
            }else {
                request.setAttribute("status","failed");
            }
            dispatcher.forward(request, response);
        } catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
