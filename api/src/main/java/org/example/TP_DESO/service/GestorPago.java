package org.example.TP_DESO.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class GestorPago {
    private static GestorPago singleton_instance;

    private GestorPago() {

    }

    public synchronized static GestorPago getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorPago();
        return singleton_instance;
    }

    public void registrarPago(){

    }

    public void mostrarCheques(){

    }

    public void mostrarIngresos(){

    }

    public void corroborarPago(){

    }
}
