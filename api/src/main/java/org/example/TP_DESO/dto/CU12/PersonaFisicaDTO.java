package org.example.TP_DESO.dto.CU12;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.PersonaFisica;
import org.example.TP_DESO.dto.HuespedDTO;

@Getter
@Setter
public class PersonaFisicaDTO extends ResponsablePagoDTO{
    private String tipoDoc;
    private String nroDoc;

    public PersonaFisicaDTO(Long id, HuespedDTO huespedDTO) {
        super(id);
        this.tipoDoc = huespedDTO.getTipoDoc();
        this.nroDoc = huespedDTO.getNroDoc();
    }

    public PersonaFisicaDTO(PersonaFisica personaFisica) {
        this.nroDoc = personaFisica.getHuesped().getNroDoc();
        this.tipoDoc = personaFisica.getHuesped().getTipoDoc();
        setId(personaFisica.getId());
    }
}
