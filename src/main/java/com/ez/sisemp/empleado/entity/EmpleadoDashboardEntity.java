package com.ez.sisemp.empleado.entity;
import jakarta.persistence.*;

@Entity
@Table
public class EmpleadoDashboardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_empleados")
    private int totalEmpleados;

    @Column(name = "mayor_salario")
    private double mayorSalario;

    @Column(name = "promedio_edad")
    private int promedioEdad;

    @Column(name = "total_departamentos")
    private int totalDepartamentos;

    // Constructor sin argumentos requerido por Hibernate
    public EmpleadoDashboardEntity() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotalEmpleados() {
        return totalEmpleados;
    }

    public void setTotalEmpleados(int totalEmpleados) {
        this.totalEmpleados = totalEmpleados;
    }

    public double getMayorSalario() {
        return mayorSalario;
    }

    public void setMayorSalario(double mayorSalario) {
        this.mayorSalario = mayorSalario;
    }

    public int getPromedioEdad() {
        return promedioEdad;
    }

    public void setPromedioEdad(int promedioEdad) {
        this.promedioEdad = promedioEdad;
    }

    public int getTotalDepartamentos() {
        return totalDepartamentos;
    }

    public void setTotalDepartamentos(int totalDepartamentos) {
        this.totalDepartamentos = totalDepartamentos;
    }


    public EmpleadoDashboardEntity(Long id, int totalEmpleados, double mayorSalario, int promedioEdad, int totalDepartamentos) {
        this.id = id;
        this.totalEmpleados = totalEmpleados;
        this.mayorSalario = mayorSalario;
        this.promedioEdad = promedioEdad;
        this.totalDepartamentos = totalDepartamentos;
    }
}