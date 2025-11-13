package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.Huesped;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long> {

    Optional<Huesped> findByTipoDocAndNroDoc(String tipoDoc, String numeroDoc);
}
