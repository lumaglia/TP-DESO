package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.PersonaFisica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonaFisicaRepository extends JpaRepository<PersonaFisica, Long> {
    PersonaFisica findByHuesped_Cuil(String cuit);
}
