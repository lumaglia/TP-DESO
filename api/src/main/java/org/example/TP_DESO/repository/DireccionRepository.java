package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    Optional<Direccion> findByPaisAndCodigoPostalAndDomicilioAndDepto(String pais, String codigoPostal, String domicilio, String depto);

}
