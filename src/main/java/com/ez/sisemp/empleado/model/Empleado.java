package com.ez.sisemp.empleado.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public record Empleado(
        Integer id,
        String codigoEmpleado,
        String nombres,
        String apellidoPat,
        String apellidoMat,
        Integer idDepartamento,
        String departamento,
        String correo,
        Integer edad,
        double salario,
        Date fechaNacimiento,
        int Activo
) implements Serializable {
    // Constructor 1
    public Empleado(Integer id, String codigoEmpleado, String nombres, String apellidoPat, String apellidoMat, String departamento, String correo, int edad, double salario, int Activo) {
        this(id, codigoEmpleado, nombres, apellidoPat, apellidoMat, null, departamento, correo, edad, salario, null, Activo);
    }

    // Constructor 2
    public Empleado(String codigoEmpleado, String nombres, String apellidoPat, String apellidoMat, String departamento, String correo, double salario, Date fechaNacimiento) {
        this(null, codigoEmpleado, nombres, apellidoPat, apellidoMat, null, departamento, correo, null, salario, fechaNacimiento, 0);
    }

    // Constructor 3
    public Empleado(String codigoEmpleado, String nombres, String apellidoPat, String apellidoMat, int idDepartamento, String correo, double salario, Date fechaNacimiento) {
        this(null, codigoEmpleado, nombres, apellidoPat, apellidoMat, idDepartamento, null, correo, null, salario, fechaNacimiento, 0);
    }

    // Constructor 4
    public Empleado(Integer id, String codigoEmpleado, String nombres, String apellidoPat, String apellidoMat, Integer idDepartamento, String correo, double salario, Date fechaNacimiento) {
        this(id, codigoEmpleado, nombres, apellidoPat, apellidoMat, idDepartamento, null, correo, null, salario, fechaNacimiento, 0);
    }

    public static List<String> getHeaders() {
        return List.of("Id", "Código", "Nombres", "Apellido Paterno", "Apellido Materno", "Departamento", "Correo", "Edad", "Salario", "Activo");
    }
}