package com.ez.sisemp.login.dao;

import com.ez.sisemp.login.entity.UsuarioEntity;
import com.ez.sisemp.shared.config.MySQLConnection;
import com.ez.sisemp.login.exception.UserOrPassIncorrectException;
import com.ez.sisemp.login.model.Usuario;
import jakarta.persistence.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioDao {
    private static final String SQL_GET_USER_JPQL = "SELECT u FROM UsuarioEntity u WHERE u.nombreUsuario = :username AND u.contrasena = :password";

    private static final String SQL_GET_USER = "SELECT * FROM usuario WHERE nombre_usuario = ? AND contrasena = ?";
    // Método para autenticación usando JDBC
    public Usuario login(String username, String password) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = MySQLConnection.getConnection()
                .prepareStatement(SQL_GET_USER);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()) {
            return mapResultSetToUsuario(resultSet);
        } else {
            throw new UserOrPassIncorrectException("Usuario o contraseña incorrectos");
        }
    }
    public UsuarioEntity loginJPA(String username, String password) {
        EntityManager entityManager = null;

        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("devUnit");
            entityManager = entityManagerFactory.createEntityManager();

            TypedQuery<UsuarioEntity> query = entityManager.createQuery(SQL_GET_USER_JPQL, UsuarioEntity.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new UserOrPassIncorrectException("Usuario o contraseña incorrectos");
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public Usuario convertirAUsuario(UsuarioEntity usuarioEntity) {
        if (usuarioEntity == null) {
            return null;
        }
        return new Usuario(usuarioEntity.getId(),
                usuarioEntity.getNombreUsuario(),
                usuarioEntity.getContrasena(),
                usuarioEntity.getPrimerNombre(),
                usuarioEntity.getApellidoPat(),
                usuarioEntity.getFotoPerfil(),
                usuarioEntity.getIdRol());
    }

    private Usuario mapResultSetToUsuario(ResultSet resultSet) throws SQLException {
        return new Usuario(resultSet.getInt("id"),
                resultSet.getString("nombre_usuario"),
                resultSet.getString("contrasena"),
                resultSet.getString("primer_nombre"),
                resultSet.getString("apellido_pat"),
                resultSet.getString("foto_perfil"),
                resultSet.getInt("id_rol")
        );
    }
}
