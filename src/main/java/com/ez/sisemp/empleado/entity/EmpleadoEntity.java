package com.ez.sisemp.empleado.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "empleado")
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "codigo_empleado")
    private String codigoEmpleado;
    private String nombres;
    @Column(name = "apellido_pat")
    private String apellidoPat;
    @Column(name = "apellido_mat")
    private String apellidoMat;
    @Column(name = "id_departamento")
    private Integer idDepartamento;
    @Column(name = "correo")
    private String correo;
    double salario;
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;
    @Column(name = "activo")
    private Integer activo;

    public EmpleadoEntity() {

    }

    public EmpleadoEntity(String codigoEmpleado, String nombres, String apellidoPat, String apellidoMat, Integer idDepartamento,String correo, double salario, Date fechaNacimiento) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombres = nombres;
        this.apellidoPat = apellidoPat;
        this.apellidoMat = apellidoMat;
        this.idDepartamento = idDepartamento;
        this.correo = correo;
        this.salario = salario;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = 1;
    }

    public EmpleadoEntity(String codigoEmpleado, String nombres, String apellidoPat, String apellidoMat, Integer idDepartamento, String correo, double salario, Date fechaNacimiento, Integer activo) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombres = nombres;
        this.apellidoPat = apellidoPat;
        this.apellidoMat = apellidoMat;
        this.idDepartamento = idDepartamento;
        this.correo = correo;
        this.salario = salario;
        this.fechaNacimiento = fechaNacimiento;
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPat() {
        return apellidoPat;
    }

    public void setApellidoPat(String apellidoPat) {
        this.apellidoPat = apellidoPat;
    }

    public String getApellidoMat() {
        return apellidoMat;
    }

    public void setApellidoMat(String apellidoMat) {
        this.apellidoMat = apellidoMat;
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }
}
