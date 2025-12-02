package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConsumoRepository extends JpaRepository<Consumo, Long> {
}
