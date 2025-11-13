package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")

@Getter
@Setter

public class Usuario {

    @Id
    private String usuario;

    private String contrasenna;

    public Usuario(String usuario, String contrasenna) {
        this.usuario = usuario;
        this.contrasenna = contrasenna;
    }

    public Usuario() {

    }


    @Override
    public String toString() {
        return
                usuario + ";" + contrasenna;

    }
}