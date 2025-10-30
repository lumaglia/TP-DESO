package service;

public class GestorReserva {
    private static GestorReserva singleton_instance;

    private GestorReserva() {

    }

    public static synchronized GestorReserva getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorReserva();
        return singleton_instance;
    }
}
