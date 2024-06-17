package com.ez.sisemp.empleado.dao;

import com.ez.sisemp.empleado.entity.EmpleadoDashboardEntity;
import com.ez.sisemp.empleado.model.EmpleadoDashboard;
import com.ez.sisemp.shared.config.MySQLConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.sql.SQLException;
import java.util.List;

public class EmpleadoDashboardDao {
    private EntityManagerFactory emf;

    public EmpleadoDashboardDao() {
        this.emf = Persistence.createEntityManagerFactory("devUnit");
    }

/*
    private static final String SQL_GET_TOTAL_EMPLEADOS = "SELECT COUNT(*) FROM empleado";
    private static final String SQL_GET_PROMEDIO_EDAD = "SELECT FLOOR(AVG(DATEDIFF(NOW(), fecha_nacimiento) / 365.25)) AS avg_age FROM empleado;";
    private static final String SQL_GET_MAYOR_SALARIO = "SELECT MAX(salario) FROM empleado";
    private static final String SQL_GET_TOTAL_DEPARTAMENTOS = "SELECT COUNT(DISTINCT id_departamento) FROM empleado"; //TODO
*/

    public EmpleadoDashboardEntity get(){
        EntityManager entityManager = emf.createEntityManager();
        try {
            EmpleadoDashboardEntity dashboard = new EmpleadoDashboardEntity();
            dashboard.setTotalEmpleados(getTotalEmpleados());
            dashboard.setMayorSalario(getMayorSalario());
            dashboard.setPromedioEdad(getPromedioEdad());
            dashboard.setTotalDepartamentos(getTotalDepartamentos());
            return dashboard;
        } finally {
            entityManager.close();
        }

    }

    public int getTotalEmpleados()
    {
        EntityManager entityManager =  emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(e) FROM EmpleadoEntity e";
            Query query = entityManager.createQuery(jpql);
            Long count = (Long) query.getSingleResult();
            return count != null ? count.intValue() : 0;
        } finally {
            entityManager.close();
        }

    }

    public int getPromedioEdad()
    {
        EntityManager entityManager = emf.createEntityManager();
        try {
            String jpql = "SELECT FUNCTION('DATEDIFF', CURRENT_DATE(), e.fechaNacimiento) FROM EmpleadoEntity e";
            Query query = entityManager.createQuery(jpql);
            List<Integer> differences = query.getResultList();

            // Calcular el promedio de las diferencias obtenidas
            double sum = 0;
            for (Integer difference : differences) {
                sum += difference;
            }
            double avgDifference = sum / differences.size();

            // Convertir la diferencia promedio a aÃ±os
            int avgAge = (int) Math.floor(avgDifference / 365.25);

            return avgAge;
        } finally {
            entityManager.close();
        }

    }

    public double getMayorSalario()
    {
        EntityManager entityManager = emf.createEntityManager();
        try {
            String jpql = "SELECT MAX(salario) FROM EmpleadoEntity e ";
            var query = entityManager.createQuery(jpql);
            Double maxSalary = (Double) query.getSingleResult();
            return maxSalary != null ? maxSalary : 0.0;  // Manejo de caso cuando maxSalary es null
        }
        finally {
            entityManager.close();
        }
    }

    public int getTotalDepartamentos()
    {
        EntityManager entityManager = emf.createEntityManager();
        try {
            String jpql = "SELECT COUNT(DISTINCT e.idDepartamento) FROM EmpleadoEntity e";
            var query = entityManager.createQuery(jpql);
            Long count = (Long) query.getSingleResult();
            return count != null ? count.intValue() : 0;  // Manejo de caso cuando count es null
        } finally {
            entityManager.close();
        }
    }
/*
    private static final String SQL_GET_TOTAL_EMPLEADOS = "SELECT COUNT(*) FROM empleado";
    private static final String SQL_GET_PROMEDIO_EDAD = "SELECT FLOOR(AVG(DATEDIFF(NOW(), fecha_nacimiento) / 365.25)) AS avg_age FROM empleado;";
    private static final String SQL_GET_MAYOR_SALARIO = "SELECT MAX(salario) FROM empleado";
    private static final String SQL_GET_TOTAL_DEPARTAMENTOS = "SELECT COUNT(DISTINCT id_departamento) FROM empleado"; //TODO

    public EmpleadoDashboard get() throws SQLException, ClassNotFoundException {
        return new EmpleadoDashboard(
            getTotalEmpleados(),
            getMayorSalario(),
            getPromedioEdad(),
            getTotalDepartamentos()
        );
    }

    public int getTotalEmpleados() throws SQLException, ClassNotFoundException {
        var result = MySQLConnection.executeQuery(SQL_GET_TOTAL_EMPLEADOS);
        result.next();
        return result.getInt(1);
    }
    public int getPromedioEdad() throws SQLException, ClassNotFoundException {
       var result = MySQLConnection.executeQuery(SQL_GET_PROMEDIO_EDAD);
       result.next();
       return result.getInt(1);
    }
    public double getMayorSalario() throws SQLException, ClassNotFoundException {
        var result = MySQLConnection.executeQuery(SQL_GET_MAYOR_SALARIO);
        result.next();
        return result.getDouble(1);
    }
    public int getTotalDepartamentos() {
        return 0;
    }
    */

}
