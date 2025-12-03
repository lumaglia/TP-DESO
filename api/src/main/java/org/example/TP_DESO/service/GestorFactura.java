package org.example.TP_DESO.service;

public class GestorFactura {

    private static GestorFactura singleton_instance;

    private GestorFactura() {

    }

    public synchronized static GestorFactura getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorFactura();
        return singleton_instance;
    }

    public void buscarResponsablePago(){

    }

    public void altaResponsablePago(){

    }

    public void modificarResponsablePago(){

    }

    public void bajaResponsablePago(){

    }

    public void generarFactura(){

    }

    public void mostrarFacturasResponsablePago(){

    }

    public void generarNotaCredito(){

    }

}
