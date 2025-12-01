package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.NotaCredito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotaCreditoRepository extends JpaRepository<NotaCredito, String> {
}
