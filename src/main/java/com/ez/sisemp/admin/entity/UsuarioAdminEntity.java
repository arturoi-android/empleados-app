package com.ez.sisemp.admin.entity;

import com.ez.sisemp.admin.model.Usuario;
import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "usuario")
public class UsuarioAdminEntity {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    private String contrasena;

    @Column(name = "contrasena_anterior")
    private String contrasenaAnterior;

    @Column(name = "ultima_conexion")
    private Date ultimaConexion;
    @Column(name = "primer_nombre")
    private String primerNombre;

    @Column(name = "apellido_pat")
    private String apellidoPat;

    @Column(name = "foto_perfil")
    private String fotoPerfil;

    @Column(name = "id_rol")
    private String rol;

    private int active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getContrasenaAnterior() {
        return contrasenaAnterior;
    }

    public void setContrasenaAnterior(String contrasenaAnterior) {
        this.contrasenaAnterior = contrasenaAnterior;
    }

    public Date getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(Date ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getApellidoPat() {
        return apellidoPat;
    }

    public void setApellidoPat(String apellidoPat) {
        this.apellidoPat = apellidoPat;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }


    public UsuarioAdminEntity() {
    }
    public UsuarioAdminEntity(Integer id, String nombreUsuario, String contrasena, String contrasenaAnterior, Date ultimaConexion, int active, String primerNombre, String apellidoPat, String fotoPerfil, String rolDescripcion) {
            this.id = id;
            this.nombreUsuario = nombreUsuario;
            this.contrasena = contrasena;
            this.contrasenaAnterior = contrasenaAnterior;
            this.ultimaConexion = ultimaConexion;
            this.active = active;
            this.primerNombre = primerNombre;
            this.apellidoPat = apellidoPat;
            this.fotoPerfil = fotoPerfil;
            this.rol = rolDescripcion;
        }
    }








