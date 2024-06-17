package com.ez.sisemp.empleado.business;

import com.ez.sisemp.empleado.dao.EmpleadoDao;
import com.ez.sisemp.empleado.dao.EmpleadoDashboardDao;
import com.ez.sisemp.empleado.entity.EmpleadoDashboardEntity;
import com.ez.sisemp.empleado.entity.EmpleadoEntity;
import com.ez.sisemp.empleado.exception.EmailAlreadyInUseException;
import com.ez.sisemp.empleado.exception.EmpleadosNotFoundException;
import com.ez.sisemp.empleado.model.Empleado;
import com.ez.sisemp.empleado.model.EmpleadoDashboard;
import com.ez.sisemp.parametro.dao.ParametroDao;
import com.ez.sisemp.shared.utils.EdadUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoBusiness {

    private final EmpleadoDao empleadoDao;
    private final EmpleadoDashboardDao empleadoDashboardDao;
    private final ParametroDao parametroDao;

    public EmpleadoBusiness(){
        this.empleadoDao = new EmpleadoDao();
        this.empleadoDashboardDao = new EmpleadoDashboardDao();
        this.parametroDao = new ParametroDao();
    }


    public void agregarEmpleadoJPA (EmpleadoEntity em) {
        em.setCodigoEmpleado(generarCodigoEmpleado());
        validarCampos(em);
        empleadoDao.agregarEmpleadoJPA(em);
    }
    public String generarCodigoEmpleado() {
        String codigo = "EMP" + (int) (Math.random() * 1000000);
        System.out.println("Generated Code: " + codigo);
        return codigo;
    }
    public void eliminarEmpleadoJpa(long id) throws ClassNotFoundException {
        empleadoDao.eliminarEmpleadoJpa(id);
    }
    public List<Empleado> obtenerEmpleadosJpa() {
        var empleados = empleadoDao.obtenerEmpleadosJPA();
        if(empleados.isEmpty()){
            throw new EmpleadosNotFoundException("No se encontraron empleados");
        }
        var empleadosToReturn = new ArrayList<Empleado>();
        empleados.forEach(
                e -> {
                    var empleadoRecord = mapToRecord(e);
                    empleadosToReturn.add(empleadoRecord);
                }
        );
        return empleadosToReturn;
    }
    private Empleado mapToRecord(EmpleadoEntity e) {
        var departamento = parametroDao.getById(e.getIdDepartamento());
        return new Empleado(
                Math.toIntExact(e.getId()),
                e.getCodigoEmpleado(),
                e.getNombres(),
                e.getApellidoPat(),
                e.getApellidoMat(),
                e.getIdDepartamento(),
                departamento.getNombre(),
                e.getCorreo(),
                EdadUtils.calcularEdad(e.getFechaNacimiento()),
                e.getSalario(),
                e.getFechaNacimiento(),
                e.getActivo()

                );
    }
    public EmpleadoDashboardEntity obtenerDatosDashboard() throws SQLException, ClassNotFoundException {
        return empleadoDashboardDao.get();
    }
    private void validarCampos (EmpleadoEntity empleado){
        if(StringUtils.isBlank(empleado.getCodigoEmpleado())){
            throw new IllegalArgumentException("El codigo del empleado no puede ser nulo");
        }
        if(StringUtils.isBlank(empleado.getNombres())){
            throw new IllegalArgumentException("El nombre del empleado no puede ser nulo");
        }
        if(StringUtils.isBlank(empleado.getApellidoPat())){
            throw new IllegalArgumentException("El apellido paterno del empleado no puede ser nulo");
        }
        if(StringUtils.isBlank(empleado.getCorreo())){
            throw new IllegalArgumentException("El correo del empleado no puede ser nulo");
        }
        if(StringUtils.isBlank(empleado.getFechaNacimiento().toString())){
            throw new IllegalArgumentException("La fecha de nacimiento del empleado no puede ser nula");
        }
        if(empleado.getSalario() < 0){
            throw new IllegalArgumentException("El salario del empleado no puede ser negativo");
        }
    }
}
