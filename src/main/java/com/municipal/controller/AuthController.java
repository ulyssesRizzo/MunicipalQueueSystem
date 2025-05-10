package com.municipal.controller;

import com.municipal.dao.UserDAO;
import com.municipal.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/auth")
public class AuthController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = UserDAO.login(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", user);

            switch (user.getRole()) {
                case "admin":
                    response.sendRedirect("admin.jsp");
                    break;
                case "operador":
                    response.sendRedirect("ticket?view=operador");
                    break;
                case "usuario":
                    response.sendRedirect("usuario.jsp");
                    break;
                default:
                    response.sendRedirect("login.jsp?error=rol");
            }
        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }
}
