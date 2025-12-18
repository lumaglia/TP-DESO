package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.PersonaFisica;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface PersonaFisicaRepository extends JpaRepository<PersonaFisica, Long> {
    Optional<PersonaFisica> findByHuesped_Cuil(String cuit);
    Optional<PersonaFisica> findByHuesped_TipoDocAndHuesped_NroDoc(String tipoDoc, String nroDoc);
}
