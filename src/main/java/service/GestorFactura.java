package service;

public class GestorFactura {

    private static GestorFactura singleton_instance;

    private GestorFactura() {

    }

    public static synchronized GestorFactura getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorFactura();
        return singleton_instance;
    }

}
