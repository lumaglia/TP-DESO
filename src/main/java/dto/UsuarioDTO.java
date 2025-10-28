package dto;

import domain.Usuario;

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