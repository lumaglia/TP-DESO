package presentation;

import domain.Huesped;

public class Main {
    public static void main(String[] args) {
        Huesped h = new Huesped.Builder().nombre("Hola").build();
        Huesped h2 = new Huesped.Builder().nombre("Holaaaa").build();
        System.out.println(h.getNombre());
        System.out.println(h2.getNombre());
    }
}