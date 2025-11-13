package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "direccion")

@Getter
@Setter

public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String domicilio;
    private String depto;
    private String codigoPostal;
    private String localidad;
    private String provincia;
    private String pais;

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
