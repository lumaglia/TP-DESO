package org.example.TP_DESO.repository;

import org.example.TP_DESO.domain.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, String> {
    Habitacion getByNroHabitacion(String nroHabitacion);
}
