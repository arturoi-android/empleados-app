package com.ez.sisemp.empleado.servlet;

import com.ez.sisemp.empleado.entity.EmpleadoEntity;
import com.ez.sisemp.empleado.dao.EmpleadoDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/empleado/editar")
public class EditarEmpleadoServlet extends HttpServlet {

    private EmpleadoDao empleadoDao = new EmpleadoDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmpleadoEntity empleado = null;
        String idString = req.getParameter("id");
        if (idString != null && !idString.isEmpty()) {
            try {
                Long id = Long.parseLong(idString);
                empleado = empleadoDao.getId(id);
                if (empleado == null) {
                    req.setAttribute("mensaje", "Empleado no encontrado");
                }
            } catch (NumberFormatException e) {
                req.setAttribute("mensaje", "ID de empleado no válido");
            }
        } else {
            req.setAttribute("mensaje", "ID de empleado no proporcionado");
        }
        req.setAttribute("empleado", empleado);
        req.getRequestDispatcher("/empleado/editar.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmpleadoEntity empleado = null;
        String idString = req.getParameter("id");
        if (idString != null && !idString.isEmpty()) {
            try {
                Long id = Long.parseLong(idString);
                empleado = empleadoDao.getId(id);
                if (empleado != null) {
                    // Actualizar los datos del empleado con los valores del formulario
                    empleado.setNombres(req.getParameter("nombres"));
                    empleado.setApellidoPat(req.getParameter("apellidoPat"));
                    empleado.setApellidoMat(req.getParameter("apellidoMat"));
                    empleado.setIdDepartamento(Integer.parseInt(req.getParameter("idDepartamento")));
                    empleado.setCorreo(req.getParameter("correo"));
                    empleado.setSalario(Double.parseDouble(req.getParameter("salario")));
                    empleado.setActivo(1);

                    empleadoDao.actualizarEmpleado(empleado);
                    req.setAttribute("mensaje", "Empleado actualizado exitosamente");
                    resp.sendRedirect(req.getContextPath() + "/empleado");
                } else {
                    req.setAttribute("mensaje", "Empleado no encontrado");
                }
            } catch (NumberFormatException e) {
                req.setAttribute("mensaje", "Error: ID de empleado no válido");
                e.printStackTrace();
            }
        } else {
            req.setAttribute("mensaje", "ID de empleado no proporcionado");
        }
    }
}
