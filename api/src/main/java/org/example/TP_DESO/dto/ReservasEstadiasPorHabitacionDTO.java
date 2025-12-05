package org.example.TP_DESO.dto;

import lombok.Getter;
import org.example.TP_DESO.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ReservasEstadiasPorHabitacionDTO {
    private HabitacionAsociada habitacion;
    private ArrayList<EstadiasAsociadas> estadias;
    private ArrayList<ReservasAsociadas> reservas;

    public static class HabitacionAsociada{
        private String nroHabitacion;
        private String tipo;

        public HabitacionAsociada(Habitacion h) {
            this.nroHabitacion = h.getNroHabitacion();
            if(h instanceof IndividualEstandar){
                this.tipo = "Individual Estandar";
            }
            if(h instanceof DobleEstandar){
                this.tipo = "Doble Estandar";
            }
            if(h instanceof DobleSuperior){
                this.tipo = "Doble Superior";
            }
            if(h instanceof SuperiorFamilyPlan){
                this.tipo = "Superior Family Plan";
            }
            if(h instanceof SuiteDoble){
                this.tipo = "Suite";
            }
        }
    }

    public static class EstadiasAsociadas{
        private LocalDate fechaInicio;
        private LocalDate fechaFin;

        public EstadiasAsociadas(Estadia estadia) {
            this.fechaInicio = estadia.getFechaInicio();
            this.fechaFin = estadia.getFechaFin();
        }
    }

    public static class ReservasAsociadas{
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private String nombre;
        private String apellido;
        private boolean cancelada;

        public ReservasAsociadas(Reserva r) {
            this.fechaInicio = r.getFechaInicio();
            this.fechaFin = r.getFechaFin();
            this.nombre = r.getNombre();
            this.apellido = r.getApellido();
            this.cancelada = r.isCancelada();
        }

    }

    public ReservasEstadiasPorHabitacionDTO(Habitacion h, List<Estadia> listaEstadias, List<Reserva> listaReservas) {
        this.habitacion = new HabitacionAsociada(h);
        this.reservas = toReservasAsociadas(listaReservas);
        this.estadias = toEstadiasAsociadas(listaEstadias);
    }

    public ArrayList<EstadiasAsociadas> toEstadiasAsociadas(List<Estadia> estadias) {
        ArrayList<EstadiasAsociadas> estadiasAsociadas = new ArrayList<>();
        for(Estadia e : estadias) estadiasAsociadas.add(new EstadiasAsociadas(e));
        return estadiasAsociadas;

    }

    public ArrayList<ReservasAsociadas> toReservasAsociadas(List<Reserva> reservas) {
        ArrayList<ReservasAsociadas> reservasAsociadas = new ArrayList<>();
        for(Reserva r : reservas) reservasAsociadas.add(new ReservasAsociadas(r));
        return reservasAsociadas;

    }
}
