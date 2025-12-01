package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.Estadia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;


@Repository
public interface EstadiaRepository extends JpaRepository<Estadia, Long> {

    ArrayList<Estadia> findByFechaInicioBetween(LocalDate desde, LocalDate hasta);


}
