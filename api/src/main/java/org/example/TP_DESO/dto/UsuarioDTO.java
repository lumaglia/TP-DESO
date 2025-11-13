package org.example.TP_DESO.dto;

public class UsuarioDTO {
    private String usuario;
    private String contrasenna;


    public UsuarioDTO(String usuario, String contrasenna) {
        this.usuario = usuario;
        this.contrasenna = contrasenna;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }

}