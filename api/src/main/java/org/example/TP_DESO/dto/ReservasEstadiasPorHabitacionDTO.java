package org.example.TP_DESO.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class ReservasEstadiasPorHabitacionDTO {


    private HabitacionAsociada habitacion;
    private ArrayList<EstadiasAsociadas> estadias;
    private ArrayList<ReservasAsociadas> reservas;


    public ReservasEstadiasPorHabitacionDTO(Habitacion h, List<Estadia> listaEstadias, List<Reserva> listaReservas) {
        this.habitacion = new HabitacionAsociada(h);
        this.reservas = toReservasAsociadas(listaReservas);
        this.estadias = toEstadiasAsociadas(listaEstadias);
    }


    @Getter @Setter
    public static class HabitacionAsociada {
        private String nroHabitacion;
        private String tipo;

        @JsonCreator
        public HabitacionAsociada(@JsonProperty("habitacion") Habitacion h) {
            this.nroHabitacion = h.getNroHabitacion();
            if (h instanceof IndividualEstandar) {
                this.tipo = "Individual Estandar";
            }
            if (h instanceof DobleEstandar) {
                this.tipo = "Doble Estandar";
            }
            if (h instanceof DobleSuperior) {
                this.tipo = "Doble Superior";
            }
            if (h instanceof SuperiorFamilyPlan) {
                this.tipo = "Superior Family Plan";
            }
            if (h instanceof SuiteDoble) {
                this.tipo = "Suite";
            }
        }
    }


    @Getter @Setter
    public static class EstadiasAsociadas {
        private LocalDate fechaInicio;
        private LocalDate fechaFin;

        @JsonCreator
        public EstadiasAsociadas(@JsonProperty("estadia") Estadia estadia) {
            this.fechaInicio = estadia.getFechaInicio();
            this.fechaFin = estadia.getFechaFin();
        }
    }


    @Getter @Setter
    public static class ReservasAsociadas {
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private String nombre;
        private String apellido;
        private boolean cancelada;

        @JsonCreator
        public ReservasAsociadas(@JsonProperty("reserva") Reserva r) {
            this.fechaInicio = r.getFechaInicio();
            this.fechaFin = r.getFechaFin();
            this.nombre = r.getNombre();
            this.apellido = r.getApellido();
            this.cancelada = r.isCancelada();
        }
    }


    public ArrayList<EstadiasAsociadas> toEstadiasAsociadas(List<Estadia> estadias) {
        return estadias.stream()
                .map(EstadiasAsociadas::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<ReservasAsociadas> toReservasAsociadas(List<Reserva> reservas) {
        return reservas.stream()
                .filter(r -> !r.isCancelada())
                .map(ReservasAsociadas::new)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}