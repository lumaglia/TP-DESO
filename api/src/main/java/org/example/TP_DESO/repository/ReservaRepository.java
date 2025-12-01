package org.example.TP_DESO.repository;


import org.example.TP_DESO.domain.Reserva;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    ArrayList<Reserva> findByFechaInicioBetween(LocalDate desde, LocalDate hasta);
    ArrayList<Reserva> findByApellido(String apellido);
    ArrayList<Reserva> findByApellidoAndNombre(String apellido, String nombre);


}
