package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.Pks.DireccionId; // Importar la clase PK correcta

@Entity
@Table(name = "direccion")
@IdClass(DireccionId.class)
@Getter
@Setter
public class Direccion {

    @Id
    @Column (length = 50)
    private String domicilio;

    @Id
    @Column (length = 50)
    private String depto;

    @Id
    @Column (length = 10)
    private String codigoPostal;

    @Id
    @Column (length = 50)
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
