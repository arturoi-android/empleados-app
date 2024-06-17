package com.ez.sisemp.shared.utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionUtils {

    private static final String LOGIN_JSP = "/login/login.jsp";

    private SessionUtils(){}

    public static boolean validarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if(request.getSession().getAttribute("user") == null){
            request.getRequestDispatcher(LOGIN_JSP).forward(request, response);
            return false;
        }
        return true;
    }

    public static boolean cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect(request.getContextPath() + LOGIN_JSP);
        return false;
    }

}
