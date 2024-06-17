package com.ez.sisemp.empleado.servlet;

import com.ez.sisemp.empleado.business.EmpleadoBusiness;
import com.ez.sisemp.empleado.entity.EmpleadoEntity;
import com.ez.sisemp.empleado.exception.EmailAlreadyInUseException;
import com.ez.sisemp.parametro.dao.ParametroDao;
import com.ez.sisemp.parametro.model.Departamento;
import com.ez.sisemp.shared.utils.SessionUtils;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/empleado/registrar")
public class RegistrarEmpleadoServlet extends HttpServlet {

    private EmpleadoBusiness empleadoBusiness;
    private ParametroDao parametroDao;

    @Override
    public void init() throws ServletException {
        super.init();
        empleadoBusiness = new EmpleadoBusiness();
        parametroDao = new ParametroDao();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (!SessionUtils.validarSesion(request, response)) {
            return;
        }
        loadDepartamentos(request);
        request.getRequestDispatcher("/empleado/registrar.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SessionUtils.validarSesion(request, response)) {
            return;
        }
        try {
            EmpleadoEntity empleado = createEmpleadoFromRequestJpa(request);
            empleadoBusiness.agregarEmpleadoJPA(empleado);

            request.setAttribute("msj", "Empleado registrado correctamente");
            response.sendRedirect(request.getContextPath() + "/empleado"); // Redireccionar a la página de empleados

        } catch (ParseException e) {
            handleParseException(request, response, e);
        } catch (EmailAlreadyInUseException e) {
            handleEmailAlreadyInUseException(request, response, e);
        } catch (Exception e) {
            throw new ServletException("Error al procesar la solicitud.", e);
        }
    }
    private EmpleadoEntity createEmpleadoFromRequestJpa(HttpServletRequest request) throws ParseException {
        String strDate = request.getParameter("fechaNacimiento");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        Date fechaNacimiento = sdf.parse(strDate);
        String codigoEmpleado = request.getParameter("codigoEmpleado");

        return new EmpleadoEntity(
                codigoEmpleado,
                request.getParameter("nombres"),
                request.getParameter("apellidoPat"),
                request.getParameter("apellidoMat"),
                Integer.parseInt(request.getParameter("idDepartamento")),
                request.getParameter("correo"),
                Double.parseDouble(request.getParameter("salario")),
                fechaNacimiento,
                1
        );
    }

    private void handleParseException(HttpServletRequest request, HttpServletResponse response, ParseException e) throws ServletException, IOException {
        loadDepartamentos(request);
        request.setAttribute("error", "Fecha Nacimiento no válido, el formato debe ser yyyy-MM-dd");
        request.getRequestDispatcher("/empleado/registrar.jsp").forward(request, response);
    }
    private void handleEmailAlreadyInUseException(HttpServletRequest request, HttpServletResponse response, EmailAlreadyInUseException e) throws ServletException, IOException {
        loadDepartamentos(request);
        request.setAttribute("error", e.getMessage());
        request.getRequestDispatcher("/empleado/registrar.jsp").forward(request, response);
    }
    private void loadDepartamentos(HttpServletRequest request)  {
        List<Departamento> departamentos = parametroDao.obtenerDepartamentos();
        request.setAttribute("departamentos", departamentos);
    }
}
