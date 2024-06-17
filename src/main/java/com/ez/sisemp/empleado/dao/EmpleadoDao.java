package com.ez.sisemp.empleado.dao;

import com.ez.sisemp.empleado.entity.EmpleadoEntity;
import com.ez.sisemp.empleado.model.Empleado;
import com.ez.sisemp.shared.config.MySQLConnection;
import jakarta.persistence.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDao{
    private static final String SQL_GET_ALL_EMPLEADOS = """
    SELECT 
        e.id, 
        e.codigo_empleado, 
        e.nombres, 
        e.apellido_pat, 
        e.apellido_mat, 
        d.nombre AS departamento, 
        e.correo, 
        FLOOR(DATEDIFF(NOW(), e.fecha_nacimiento) / 365.25) AS edad, 
        e.salario 
    FROM 
        empleado e
    INNER JOIN departamentos d ON d.id = e.id_departamento  
    WHERE 
        e.activo = 1;
    """;

    private static final String SQL_GET_ALL_EMPLEADOS_JPQL = """
            Select  e
            from EmpleadoEntity e
            """;
    private static final String SQL_DELETE_EMPLEADO_JPQL = "UPDATE EmpleadoEntity e SET e.activo = 0 WHERE e.id = :id";

    private static final String SQL_UPDATE_EMPLEADO_JPQL = "UPDATE EmpleadoEntity e SET e.nombres = :nombres, " +
            "e.apellidoPat = :apellidoPat, " +
            "e.apellidoMat = :apellidoMat, " +
            "e.idDepartamento = :idDepartamento, " +
            "e.correo = :correo, " +
            "e.salario = :salario " +
            "WHERE e.id = :id";
    private static final String JPQL_INSERT_EMPLEADO = "INSERT INTO EmpleadoEntity (codigoEmpleado, nombres, apellidoPat, apellidoMat, idDepartamento, correo, fechaNacimiento, salario) " +
            "VALUES (:codigoEmpleado, :nombres, :apellidoPat, :apellidoMat, :idDepartamento, :correo, :fechaNacimiento, :salario)";
    private static final String JPQL_UPDATE_EMPLEADO = "UPDATE EmpleadoEntity e SET e.nombres = :nombres, e.apellidoPat = :apellidoPat, e.apellidoMat = :apellidoMat, e.idDepartamento = :idDepartamento, e.correo = :correo, e.salario = :salario, e.activo = 1 WHERE e.id = :id";
    private static String SQL_DELETE_EMPLEADO = "UPDATE empleado set activo=0 WHERE id = ?;";
    private static String SQL_INSERT_EMPLEADO = "INSERT INTO empleado (codigo_empleado, nombres, apellido_pat, apellido_mat, id_departamento, correo, fecha_nacimiento, salario, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private static String SQL_GET_NEW_EMPLEADO_CODE = "SELECT CONCAT('EMP', LPAD(MAX(CAST(SUBSTRING(codigo_empleado, 4) AS UNSIGNED)) + 1, 4, '0')) AS next_emp_code FROM empleado;";

    private static final Logger log = LogManager.getLogger(EmpleadoDao.class);

    public List<EmpleadoEntity> obtenerEmpleadosJPA () {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("devUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var empleados = entityManager.createQuery(SQL_GET_ALL_EMPLEADOS_JPQL, EmpleadoEntity.class).getResultList();
        return empleados;
    }

    /* ELIMINAR */
    public void eliminarEmpleadoJpa(Long id) throws ClassNotFoundException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;

        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("devUnit");
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            var query = entityManager.createQuery(SQL_DELETE_EMPLEADO_JPQL);
            query.setParameter("id", id);
            int updatedRows = query.executeUpdate();

            if (updatedRows > 0) {
                System.out.println("Empleado eliminado exitosamente.");
            } else {
                System.out.println("No se encontró el empleado con el ID especificado.");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    /* AGREGAR */
    public void agregarEmpleadoJPA(EmpleadoEntity empleadoEntity){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("devUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(empleadoEntity);
            entityManager.getTransaction().commit();
        }
        finally {
            entityManager.close();
        }
    }

    /* MODIFICAR */
    public EmpleadoEntity getId(Long id) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("devUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var empleadoId = entityManager.find(EmpleadoEntity.class, id);
        entityManager.close();
        return empleadoId;
    }
    public void actualizarEmpleado(EmpleadoEntity empleado) {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("devUnit");
            entityManager = entityManagerFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            EmpleadoEntity empleadoActualizado = entityManager.find(EmpleadoEntity.class, empleado.getId());
            empleadoActualizado.setNombres(empleado.getNombres());
            empleadoActualizado.setApellidoPat(empleado.getApellidoPat());
            empleadoActualizado.setApellidoMat(empleado.getApellidoMat());
            empleadoActualizado.setIdDepartamento(empleado.getIdDepartamento());
            empleadoActualizado.setCorreo(empleado.getCorreo());
            empleadoActualizado.setSalario(empleado.getSalario());
            empleadoActualizado.setActivo(empleado.getActivo());

            entityManager.merge(empleadoActualizado);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    /* PROFESOR ITEMS */
    public Empleado mapResultSetToEmpleado(ResultSet resultSet) throws SQLException {
        return new Empleado(resultSet.getInt("id"),
                resultSet.getString("codigo_empleado"),
                resultSet.getString("nombres"),
                resultSet.getString("apellido_pat"),
                resultSet.getString("apellido_mat"),
                resultSet.getString("departamento"),
                resultSet.getString("correo"),
                resultSet.getInt("edad"),
                resultSet.getDouble("salario"),
                resultSet.getInt("Activo")
        );
    }

}
