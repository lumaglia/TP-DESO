package org.example.TP_DESO.service;

public class GestorReserva {
    private static GestorReserva singleton_instance;

    private GestorReserva() {

    }

    public synchronized static GestorReserva getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorReserva();
        return singleton_instance;
    }
}
