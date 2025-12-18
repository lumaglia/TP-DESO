package org.example.TP_DESO.dto.CU12;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.PersonaJuridica;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.patterns.mappers.DireccionMapper;

@Getter
@Setter
public class PersonaJuridicaDTO extends ResponsablePagoDTO{
    String razonSocial;
    String cuit;
    String telefono;
    DireccionDTO direccion;

    public PersonaJuridicaDTO(Long id, String razonSocial, String cuit, String telefono, DireccionDTO direccion) {
        super(id);
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public PersonaJuridicaDTO(PersonaJuridica pj) {
        this.cuit = pj.getCuit();
        this.razonSocial = pj.getRazonSocial();
        this.telefono = pj.getTelefono();
        this.direccion = DireccionMapper.toDTO(pj.getDireccion());
        setId(pj.getId());
    }
}
