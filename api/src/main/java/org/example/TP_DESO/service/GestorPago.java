package org.example.TP_DESO.service;

public class GestorPago {
    private static GestorPago singleton_instance;

    private GestorPago() {

    }

    public synchronized static GestorPago getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorPago();
        return singleton_instance;
    }
}
