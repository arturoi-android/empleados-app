package com.ez.sisemp.admin.dao;

import com.ez.sisemp.admin.entity.UsuarioAdminEntity;
import com.ez.sisemp.admin.model.Usuario;
import com.ez.sisemp.shared.config.MySQLConnection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AdminDao {

    private static final String GET_ALL_USERS = "SELECT * FROM usuario";
    private static final String JPQL_GET_ALL_USERS = "SELECT u FROM UsuarioAdminEntity u ";


    /*public List<Usuario> obtenerUsuarios() throws SQLException, ClassNotFoundException {
        List<Usuario> usuarios = new ArrayList<>();
        Statement stp = MySQLConnection.getConnection().createStatement();
        ResultSet rs = stp.executeQuery(GET_ALL_USERS);
        while(rs.next()){
            usuarios.add(mapRow(rs));
        }
        return usuarios;
    }*/
    public List<UsuarioAdminEntity> obtenerUsuariosJPA() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("devUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        var query = entityManager.createQuery(JPQL_GET_ALL_USERS, UsuarioAdminEntity.class);
        List<UsuarioAdminEntity> usuarioEntities = query.getResultList();
        return usuarioEntities.stream()
                .map(this::mapEntityToModel)
                .collect(Collectors.toList());
    }

    public UsuarioAdminEntity mapEntityToModel(UsuarioAdminEntity u){
        String rolDescripcion;
        if (u.getId() == 1) {
            rolDescripcion = "Administrador";
        } else {
            rolDescripcion = "Usuario";
       }        return new UsuarioAdminEntity(
                u.getId(),
                u.getNombreUsuario(),
                u.getContrasena(),
                u.getContrasenaAnterior(),
                u.getUltimaConexion(),
                u.getActive(),
                u.getPrimerNombre(),
                u.getApellidoPat(),
                u.getFotoPerfil(),
                rolDescripcion
        );
    }

    public Usuario mapRow(ResultSet rs) throws SQLException {

        String rolDescripcion;
        if(rs.getInt("id_rol") == 1){
            rolDescripcion = "Administrador";
        }else{
            rolDescripcion = "Usuario";
        }

        return new Usuario(
            rs.getInt("id"),
            rs.getString("nombre_usuario"),
            rs.getString("contrasena"),
            rs.getString("contrasena_anterior"),
            rs.getDate("ultima_conexion"),
            rs.getBoolean("active"),
            rs.getString("primer_nombre"),
            rs.getString("apellido_pat"),
            rs.getString("foto_perfil"),
            rolDescripcion
        );


    }



}
