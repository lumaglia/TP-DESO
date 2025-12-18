package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.PersonaJuridica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaJuridicaRepository extends JpaRepository<PersonaJuridica, Long> {
    PersonaJuridica findByCuit(String cuit);
}
