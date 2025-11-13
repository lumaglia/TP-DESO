package org.example.TP_DESO.domain;

import java.util.List;

public class Habitacion {
    private String nroHabitacion;
    private float precioNoche;
    private int capacidad;
    private String tamanno;

    public Habitacion() {} //BORRAR CUANDO AVANZEMOS EL TP

    public Habitacion(String nro_Habitacion, float precio_Noche, int capacidad, String tamanno) {
        this.nroHabitacion = nro_Habitacion;
        this.precioNoche = precio_Noche;
        this.capacidad = capacidad;
        this.tamanno = tamanno;
    }
    
    static public void sortHabitacionesByPrice(List<Habitacion> lh){
        lh.sort((l, r) -> Float.compare(l.getPrecioNoche(), r.getPrecioNoche()));
    }

    public String getNroHabitacion() {
        return nroHabitacion;
    }

    public void setNroHabitacion(String nro_Habitacion) {
        this.nroHabitacion = nro_Habitacion;
    }

    public float getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecio_Noche(float precio_Noche) {
        this.precioNoche = precio_Noche;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTamanno() {
        return tamanno;
    }

    public void setTamanno(String tamanno) {
        this.tamanno = tamanno;
    }
    
}
