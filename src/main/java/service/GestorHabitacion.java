package service;

public class GestorHabitacion {
    private static GestorHabitacion singleton_instance;

    private GestorHabitacion() {

    }

    public static GestorHabitacion getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorHabitacion();
        return singleton_instance;
    }
}
