package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, String> {
}
