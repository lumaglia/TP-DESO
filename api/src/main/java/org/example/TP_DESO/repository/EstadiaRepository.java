package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.Estadia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface EstadiaRepository extends JpaRepository<Estadia, Long> {

    ArrayList<Estadia> findByFechaFinAfterAndFechaInicioBefore(LocalDate desde, LocalDate hasta);
    Optional<Estadia> findByHabitacionNroHabitacionAndFechaFin(String nroHabitacion, LocalDate fechaFin);
    ArrayList<Estadia> findByHuespedesTipoDocAndHuespedesNroDoc(String tipoDoc, String nroDoc);

}
