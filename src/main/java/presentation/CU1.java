package presentation;

import service.GestorUsuario;
import java.io.IOException;
import java.util.Scanner;

public class CU1 {
     public static void ejecucion () throws IOException{

         GestorUsuario gestor = new GestorUsuario();

         try{
             Scanner sc = new Scanner(System.in);
             while(true){
                 System.out.print("Introduzca su Usuario : ");
                 String nombre = sc.nextLine();
                 System.out.print("Introduzca su Contraseña : ");
                 String contrasenna = sc.nextLine();
                 if (gestor.autenticar(nombre,contrasenna)){
                     System.out.flush();
                     System.out.println("Bienvenido " + nombre);
                     break;
                 }
                 else{
                     System.out.println("El usuario o la contraseña no son válidos");
                     System.out.flush();
                 }
             }

         }catch (IOException e){
             System.out.println(e.getMessage());

         }
    }
}
