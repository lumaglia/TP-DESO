package presentation;

import domain.Direccion;
import domain.Huesped;
import dao.HuespedCSV;
import domain.Usuario;
import dto.DireccionDTO;
import dto.DireccionDTOBuilder;
import dto.HuespedDTO;
import dto.HuespedDTOBuilder;
import exceptions.DocumentoYaExistente;
import service.GestorHuesped;
import service.GestorUsuario;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GestorHuesped gestorHuesped;
        GestorUsuario gestorUsuario;

        try {
            gestorHuesped = new GestorHuesped();
            gestorUsuario = new GestorUsuario();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("Bienvenido al sistema hotelero");
            System.out.println("1- Iniciar Sesión");
            System.out.println("2- Registrar nuevo usuario");
            System.out.println("3- Salir");
            System.out.print("\n");
            System.out.print("Ingrese la opcion deseada: ");
            int opcion;
            try {
                opcion = s.nextInt();
            } catch (InputMismatchException e) {
                opcion = -1;
            } finally {
                s.nextLine();
            }
            submenus:
            switch (opcion) {
                case 1: {
                    boolean valido = false;
                    boolean first = true;
                    String usuario = "", password = "";
                    while (!valido) {
                        if(!first) {
                            System.out.println("Nombre de usuario o contraseña incorrectos");
                            System.out.println("Ingrese 1 para reintentar o 2 para volver al menu");
                            try {
                                opcion = s.nextInt();
                            } catch (InputMismatchException e) {
                                continue;
                            }finally {
                                s.nextLine();
                            }
                            switch (opcion) {
                                case 1: break;
                                case 2: break submenus;
                                default: continue;
                            }
                        }
                        System.out.print("Ingrese el nombre de usuario: ");
                        usuario = s.nextLine();
                        System.out.print("Ingrese la contraseña: ");
                        password = s.nextLine();
                        valido = gestorUsuario.autenticar(usuario, password);
                        first = false;
                    }

                    while (true) {
                        System.out.println("Bienvenido "+usuario);
                        System.out.println("1- Dar de alta un huésped");
                        System.out.println("2- Buscar huéspedes");
                        System.out.println("3- Cerrar sesion");
                        System.out.print("\n");
                        System.out.print("Ingrese la opcion deseada: ");
                        try {
                            opcion = s.nextInt();
                        } catch (InputMismatchException e) {
                            opcion = -1;
                        }finally {
                            s.nextLine();
                        }

                        menuPrincipal:
                        switch (opcion) {
                            case 1: {
                                boolean seguirCargando = true;
                                while (seguirCargando) {
                                    String linea;
                                    HuespedDTOBuilder builderHuesped = new HuespedDTOBuilder();
                                    valido = false;

                                    System.out.println("Dar de alta un nuevo huesped");
                                    System.out.println("Si desea volver al menu ingrese CANCELAR en cualquier dato");
                                    System.out.println("Por favor ingrese los datos del huesped: ");
                                    System.out.print("Apellido: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderHuesped = builderHuesped.setApellido(linea);

                                    System.out.print("Nombre: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderHuesped = builderHuesped.setNombre(linea);

                                    //QUIZAS CHEQUEAR SI EL TIPO DE DOCUMENTO ES VALIDO
                                    System.out.print("Tipo de documento: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderHuesped = builderHuesped.setTipoDoc(linea);

                                    while (!valido) {
                                        System.out.print("Numero de documento: ");
                                        linea = s.nextLine();
                                        if(linea.equals("CANCELAR")) break menuPrincipal;
                                        try {
                                            if(Integer.parseInt(linea)<0) {
                                                System.out.println("Por favor ingrese un numero valido");
                                            }else valido = true;
                                        } catch (NumberFormatException e) {
                                            System.out.println("Por favor ingrese un numero valido");
                                        }
                                    }
                                    builderHuesped = builderHuesped.setNroDoc(linea);


                                    System.out.print("Posicion frente al IVA(Por defecto Consumidor final): ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    if(linea.isEmpty()) builderHuesped = builderHuesped.setPosicionIva("Consumidor final");
                                    else builderHuesped = builderHuesped.setPosicionIva(linea);

                                    if(!linea.equals("Responsable Inscripto")){
                                        valido = false;
                                        while (!valido) {
                                            System.out.print("Desea ingresar un CUIT(S o N)? ");
                                            linea = s.nextLine();
                                            if(linea.equals("CANCELAR")) break menuPrincipal;
                                            linea = linea.toUpperCase();
                                            switch(linea){
                                                case "S", "N": {
                                                    valido = true;
                                                    break;
                                                }
                                                default: {
                                                    System.out.println("Por favor ingrese un valor valido");
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    if(linea.equals("S") || linea.equals("Responsable Inscripto")) {
                                        valido = false;
                                        while (!valido) {
                                            System.out.print("CUIT: ");
                                            linea = s.nextLine();
                                            if(linea.equals("CANCELAR")) break menuPrincipal;
                                            try {
                                                if(Integer.parseInt(linea)<0) {
                                                    System.out.println("Por favor ingrese un numero valido");
                                                }else valido = true;
                                            } catch (NumberFormatException e) {
                                                System.out.println("Por favor ingrese un numero valido");
                                            }
                                        }
                                        //CUIL O CUIT?
                                        builderHuesped = builderHuesped.setCuil(linea);
                                    }else builderHuesped = builderHuesped.setCuil(null); //NO SE SI ES NECESARIO EL ELSE

                                    valido = false;
                                    while (!valido) {
                                        System.out.print("Fecha de nacimiento(yyyy-MM-dd): ");
                                        linea = s.nextLine();
                                        if(linea.equals("CANCELAR")) break menuPrincipal;
                                        try {
                                            LocalDate fecha = LocalDate.parse(linea);
                                            valido = true;
                                            builderHuesped = builderHuesped.setFechaNac(fecha);
                                        } catch (DateTimeParseException e) {
                                            System.out.println("Por favor ingrese una fecha valida");
                                        }
                                    }


                                    System.out.print("Telefono: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderHuesped = builderHuesped.setTelefono(linea);

                                    valido = false;
                                    while (!valido) {
                                        System.out.print("Desea ingresar un email(S o N)? ");
                                        linea = s.nextLine();
                                        if(linea.equals("CANCELAR")) break menuPrincipal;
                                        linea = linea.toUpperCase();
                                        switch(linea){
                                            case "S", "N": {
                                                valido = true;
                                                break;
                                            }
                                            default: {
                                                System.out.println("Por favor ingrese un valor valido");
                                                break;
                                            }
                                        }
                                    }
                                    if(linea.equals("S")) {
                                        System.out.print("Email: ");
                                        linea = s.nextLine();
                                        if(linea.equals("CANCELAR")) break menuPrincipal;
                                        builderHuesped = builderHuesped.setEmail(linea);
                                    }

                                    System.out.print("Ocupacion: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderHuesped = builderHuesped.setOcupacion(linea);

                                    System.out.print("Nacionalidad: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderHuesped = builderHuesped.setNacionalidad(linea);

                                    DireccionDTOBuilder builderDireccion = new DireccionDTOBuilder();
                                    System.out.println("Por favor ingrese los datos de la direccion del huesped: ");
                                    System.out.print("Pais: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderDireccion = builderDireccion.setPais(linea);

                                    System.out.print("Provincia: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderDireccion = builderDireccion.setProvincia(linea);

                                    System.out.print("Localidad: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderDireccion = builderDireccion.setLocalidad(linea);

                                    System.out.print("Codigo Postal: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderDireccion = builderDireccion.setCodigoPostal(linea);

                                    System.out.print("Domicilio: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    builderDireccion = builderDireccion.setDomicilio(linea);

                                    System.out.print("Depto: ");
                                    linea = s.nextLine();
                                    if(linea.equals("CANCELAR")) break menuPrincipal;
                                    if(linea.isEmpty()) builderDireccion = builderDireccion.setDepto(null);
                                    else builderDireccion = builderDireccion.setDepto(linea);

                                    builderHuesped = builderHuesped.setDireccion(builderDireccion.createDireccionDTO());
                                    HuespedDTO huespedDTO = builderHuesped.createHuespedDTO();
                                    valido = false;
                                    while (!valido) {
                                        try {
                                            gestorHuesped.AltaHuesped(huespedDTO);
                                            valido = true;
                                        } catch (DocumentoYaExistente e) {
                                            System.out.println(e.getMessage());
                                            while (!valido) {
                                                System.out.println("Desea ACEPTAR IGUALMENTE o CORREGIR?");
                                                linea = s.nextLine();
                                                switch (linea.toUpperCase()) {
                                                    case "ACEPTAR IGUALMENTE": {
                                                        //MODIFICAR HUESPED
                                                        System.out.println("HUESPED SOBREESCRITO(NO IMPLEMENTADO)");
                                                        valido = true;
                                                        break;
                                                    }
                                                    case "CORREGIR": {
                                                        System.out.print("Tipo de documento: ");
                                                        linea = s.nextLine();
                                                        if(linea.equals("CANCELAR")) break menuPrincipal;
                                                        builderHuesped = builderHuesped.setTipoDoc(linea);

                                                        while (!valido) {
                                                            System.out.print("Numero de documento: ");
                                                            linea = s.nextLine();
                                                            if(linea.equals("CANCELAR")) break menuPrincipal;
                                                            try {
                                                                if(Integer.parseInt(linea)<0) {
                                                                    System.out.println("Por favor ingrese un numero valido");
                                                                }else valido = true;
                                                            } catch (NumberFormatException e2) {
                                                                System.out.println("Por favor ingrese un numero valido");
                                                            }
                                                        }
                                                        builderHuesped = builderHuesped.setNroDoc(linea);
                                                        huespedDTO = builderHuesped.createHuespedDTO();
                                                        try {
                                                            gestorHuesped.AltaHuesped(huespedDTO);
                                                        } catch (DocumentoYaExistente ex) {
                                                            System.out.println(ex.getMessage());
                                                            valido = false;
                                                        } catch (IOException ex) {
                                                            throw new RuntimeException(ex);
                                                        }
                                                        break;
                                                    }
                                                    default: {
                                                        System.out.println("Por favor ingrese un valor valido");
                                                    }
                                                }
                                            }

                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    valido = false;
                                    while (!valido) {
                                        System.out.println("El huésped "+huespedDTO.getNombre()+" "+huespedDTO.getApellido()+" ha sido satisfactoriamente cargado al sistema. ¿Desea cargar otro(S o N)? ");
                                        linea = s.nextLine();
                                        linea = linea.toUpperCase();
                                        switch (linea) {
                                            case "S":{
                                                seguirCargando = true;
                                                valido = true;
                                                break;
                                            }
                                            case "N":{
                                                seguirCargando = false;
                                                valido = true;
                                                break;
                                            }
                                            default: {
                                                System.out.println("Por favor ingrese un valor valido");
                                            }
                                        }
                                    }
                                }

                                break;
                            }
                            case 2: {
                                System.out.println("BUSCAR HUESPED");
                                break;
                            }
                            case 3: {
                                break submenus;
                            }
                            default: {
                                System.out.println("OPCION INVALIDA");
                                break;
                            }
                        }
                    }

                }
                case 2: {
                    boolean valido = false;
                    boolean first = true;
                    String usuario = "", password = "";
                    while (!valido) {
                        if(!first) {
                            System.out.println("Las contraseñas no coinciden, por favor intentarlo de nuevo");
                        }
                        System.out.print("Ingrese el nombre de usuario: ");
                        usuario = s.nextLine();
                        System.out.print("Ingrese la contraseña: ");
                        password = s.nextLine();
                        System.out.print("Vuelva a ingresar la contraseña: ");
                        valido = password != null && usuario != null && password.equals(s.nextLine());
                        first = false;
                    }
                    gestorUsuario.altaUsuario(usuario, password);
                    System.out.println("Uusario registrado correctamente");
                    break;
                }
                case 3: {
                    System.exit(0);
                    break;
                }
                default: {
                    System.out.println("OPCION INVALIDA");
                    break;
                }
            }
        }
//        System.out.print("Por favor ingrese su usuario: ");
//        String usuario = s.nextLine();
//        System.out.print("Por favor ingrese su contraseña: ");
//        String password = s.nextLine();
//
//        gestorUsuario.autenticar()

    }
}