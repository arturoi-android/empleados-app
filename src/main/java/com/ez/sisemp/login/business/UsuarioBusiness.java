package com.ez.sisemp.login.business;

import com.ez.sisemp.login.dao.UsuarioDao;
import com.ez.sisemp.login.entity.UsuarioEntity;
import com.ez.sisemp.login.model.Usuario;
import java.sql.SQLException;

public class UsuarioBusiness {

    private final UsuarioDao usuarioDao;

    public UsuarioBusiness() {
        this.usuarioDao = new UsuarioDao();
    }

    public Usuario login(String username, String password) throws SQLException, ClassNotFoundException {
        return usuarioDao.login(username, password);
    }
    public Usuario loginJPA(String username, String password) throws ClassNotFoundException {
        UsuarioEntity usuarioEntity = usuarioDao.loginJPA(username, password); // Obtener UsuarioEntity desde DAO
        return usuarioDao.convertirAUsuario(usuarioEntity); // Convertir UsuarioEntity a Usuario y lo devuelves
    }
}
