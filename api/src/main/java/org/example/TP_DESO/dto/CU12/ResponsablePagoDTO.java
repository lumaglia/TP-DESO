package org.example.TP_DESO.dto.CU12;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.TP_DESO.patterns.mappers.DireccionMapper;
import org.example.TP_DESO.domain.PersonaFisica;
import org.example.TP_DESO.domain.PersonaJuridica;
import org.example.TP_DESO.dto.DireccionDTO;

@Getter
@Setter
@NoArgsConstructor
public class ResponsablePagoDTO {
    private Long id;
    private String razonSocial;
    private String cuit;
    private String telefono;
    private DireccionDTO direccion;

    public ResponsablePagoDTO(PersonaFisica personaFisica){
        this.id = personaFisica.getId();
        this.telefono = personaFisica.getHuesped().getTelefono();
        this.cuit = personaFisica.getHuesped().getCuil();
        this.direccion = DireccionMapper.toDTO(personaFisica.getHuesped().getDireccion());
        this.razonSocial = "N/A";
    }
    public ResponsablePagoDTO(PersonaJuridica personaJuridica){
        this.id = personaJuridica.getId();
        this.razonSocial = personaJuridica.getRazonSocial();
        this.cuit = personaJuridica.getCuit();
        this.telefono = personaJuridica.getTelefono();
        this.direccion = DireccionMapper.toDTO(personaJuridica.getDireccion());
    }
}
