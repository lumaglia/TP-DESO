package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
