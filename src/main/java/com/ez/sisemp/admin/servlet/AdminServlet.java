package com.ez.sisemp.admin.servlet;

import com.ez.sisemp.admin.dao.AdminDao;
import com.ez.sisemp.shared.utils.SessionUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final String ADMIN_JSP = "/admin/admin.jsp";
    private static final String LOGIN_JSP = "/login/login.jsp";


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Prox

        String action = request.getParameter("action");
        if ("logout".equals(action)) {
            if (!SessionUtils.cerrarSesion(request, response)) {
                return;
            } else {
                return;
            }
        }

        if(!SessionUtils.validarSesion(request, response)){
            return;
        }

        AdminDao dao = new AdminDao();
        try {
            request.setAttribute("usuarios", dao.obtenerUsuariosJPA());
            request.getRequestDispatcher(ADMIN_JSP).forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

}
