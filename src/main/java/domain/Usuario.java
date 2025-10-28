package domain;

public class Usuario {
    private String usuario;
    private String contrasenna;

    public Usuario(String usuario, String contrasenna) {
        this.usuario = usuario;
        this.contrasenna = contrasenna;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenna() {
        return contrasenna;
    }


    @Override
    public String toString() {
        return
                usuario + ";" + contrasenna;

    }
}