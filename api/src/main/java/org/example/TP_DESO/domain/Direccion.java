package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "direccion")
@IdClass(Direccion.class)
@Getter
@Setter

public class Direccion {

    @Id
    private String domicilio;

    @Id
    private String depto;

    @Id
    private String codigoPostal;

    @Id
    private String pais;

    private String localidad;
    private String provincia;


    public Direccion(){

    }
    public Direccion(String domicilio, String depto, String codigoPostal, String localidad, String provincia, String pais) {
        this.domicilio = domicilio;
        this.depto = depto;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }

    @Override
    public String toString() {
        return
                pais +
                        ";" + codigoPostal +
                        ";" + domicilio +
                        ";" + depto +
                        ";" + localidad +
                        ";" + provincia;
    }
}
