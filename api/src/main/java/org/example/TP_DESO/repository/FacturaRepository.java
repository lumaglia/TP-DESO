package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FacturaRepository extends JpaRepository<Factura, String> {
    Optional<Factura> findByNroFactura(String numeroFactura);
    Optional<Factura> findByEstadia_idEstadia(long estadia_id);
}
