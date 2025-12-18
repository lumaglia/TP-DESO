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
public abstract class ResponsablePagoDTO {
    private Long id;

    public ResponsablePagoDTO(Long id) {
        this.id = id;
    }
}
