/*
package org.example.TP_DESO.presentation;

import org.example.TP_DESO.dto.DireccionDTOBuilder;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.dto.HuespedDTOBuilder;
import org.example.TP_DESO.exceptions.DocumentoYaExistente;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorHuesped;
import org.example.TP_DESO.service.GestorUsuario;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainConsola {
    public static void main(String[] args) {
        GestorHuesped gestorHuesped;
        GestorUsuario gestorUsuario;

        try {
            gestorHuesped = GestorHuesped.getInstance();
            gestorUsuario = new GestorUsuario();
        } catch (FracasoOperacion e) {
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
                        try {
                            valido = gestorUsuario.autenticar(usuario, password);
                        } catch (FracasoOperacion e) {
                            throw new RuntimeException(e);
                        }
                        first = false;
                    }
                    boolean forzarAltaHuesped = false;
                    while (true) {
                        if(forzarAltaHuesped) {
                            opcion = 1;
                            forzarAltaHuesped = false;
                        }else {
                            System.out.println("Bienvenido " + usuario);
                            System.out.println("1- Dar de alta un huésped");
                            System.out.println("2- Buscar huéspedes");
                            System.out.println("3- Cerrar sesion");
                            System.out.print("\n");
                            System.out.print("Ingrese la opcion deseada: ");
                            try {
                                opcion = s.nextInt();
                            } catch (InputMismatchException e) {
                                opcion = -1;
                            } finally {
                                s.nextLine();
                            }
                        }
                        menuPrincipal:
                        switch (opcion) {
                            case 1: {
                                String linea;
                                boolean seguirCargando = true;
                                while (seguirCargando) {
                                    System.out.println("Dar de alta un nuevo huesped");
                                    System.out.println("Si desea volver al menu ingrese CANCELAR en cualquier dato");
                                    System.out.println("Por favor ingrese los datos del huesped: ");
                                    HuespedDTOBuilder builderHuesped = ingresarDatosHuesped(s, true, null);
                                    if (builderHuesped == null) break menuPrincipal;
                                    HuespedDTO huespedDTO = builderHuesped.createHuespedDTO();
                                    valido = false;
                                    while (!valido) {
                                        try {
                                            gestorHuesped.altaHuesped(huespedDTO);
                                            valido = true;
                                        } catch (DocumentoYaExistente e) {
                                            System.out.println(e.getMessage());
                                            while (!valido) {
                                                System.out.println("Desea ACEPTAR IGUALMENTE o CORREGIR?");
                                                do linea = s.nextLine();
                                                while (linea.isEmpty());
                                                switch (linea.toUpperCase()) {
                                                    case "ACEPTAR IGUALMENTE": {
                                                        try{
                                                            ArrayList<HuespedDTO> hList = gestorHuesped.buscarHuesped(new HuespedDTOBuilder().setTipoDoc(huespedDTO.getTipoDoc()).setNroDoc(huespedDTO.getNroDoc()).createHuespedDTO());
                                                            HuespedDTO sobrescrito = hList.get(0);
                                                            gestorHuesped.bajaHuesped(sobrescrito);
                                                            gestorHuesped.altaHuesped(huespedDTO);
                                                        }catch (FracasoOperacion | DocumentoYaExistente ex){
                                                            throw new RuntimeException(ex);
                                                        }
                                                        System.out.println("HUESPED SOBREESCRITO");
                                                        valido = true;
                                                        break;
                                                    }
                                                    case "CORREGIR": {
                                                        System.out.print("Tipo de documento: ");
                                                        do linea = s.nextLine();
                                                        while (linea.isEmpty());
                                                        if(linea.equals("CANCELAR")) break menuPrincipal;
                                                        builderHuesped = builderHuesped.setTipoDoc(linea);

                                                        while (!valido) {
                                                            System.out.print("Numero de documento: ");
                                                            do linea = s.nextLine();
                                                            while (linea.isEmpty());
                                                            if(linea.equals("CANCELAR")) break menuPrincipal;
                                                            try {
                                                                if(Integer.parseInt(linea)<0) {
                                                                    System.out.println("Por favor ingrese un numero valido");
                                                                }else valido = true;
                                                            } catch (NumberFormatException ex) {
                                                                System.out.println("Por favor ingrese un numero valido");
                                                            }
                                                        }
                                                        builderHuesped = builderHuesped.setNroDoc(linea);
                                                        huespedDTO = builderHuesped.createHuespedDTO();
                                                        try {
                                                            gestorHuesped.altaHuesped(huespedDTO);
                                                        } catch (DocumentoYaExistente ex) {
                                                            System.out.println(ex.getMessage());
                                                            valido = false;
                                                        } catch (FracasoOperacion ex) {
                                                            throw new RuntimeException(ex);
                                                        }
                                                        break;
                                                    }
                                                    default: {
                                                        System.out.println("Por favor ingrese un valor valido");
                                                    }
                                                }
                                            }

                                        } catch (FracasoOperacion e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                    valido = false;
                                    while (!valido) {
                                        System.out.println("El huésped "+huespedDTO.getNombre()+" "+huespedDTO.getApellido()+" ha sido satisfactoriamente cargado al sistema. ¿Desea cargar otro(S o N)? ");
                                        do linea = s.nextLine();
                                        while (linea.isEmpty());
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
                                String linea;
                                System.out.println("Buscar huespedes");
                                System.out.println("Si desea volver al menu ingrese CANCELAR en cualquier dato");
                                System.out.println("Por favor ingrese los datos por los que desea buscar: ");
                                HuespedDTOBuilder builderHuesped = ingresarDatosHuesped(s, false, null);
                                if (builderHuesped == null) break;
                                HuespedDTO huespedDTO = builderHuesped.createHuespedDTO();
                                System.out.println(huespedDTO.toString());
                                ArrayList<HuespedDTO> huespedesDTO;
                                try {
                                    huespedesDTO = gestorHuesped.buscarHuesped(huespedDTO);
                                } catch (FracasoOperacion e) {
                                    throw new RuntimeException(e);
                                }
                                int i = 0;
                                while (true) {
                                    if(i >= huespedesDTO.size()) i = huespedesDTO.size()-1;
                                    if(huespedesDTO.size()>1) {
                                        System.out.println("Se encontraron "+huespedesDTO.size()+" huespedes");
                                        System.out.println("Mostrando el huesped numero "+(i+1));
                                        System.out.println(huespedesDTO.get(i).toString());
                                        System.out.println("Por favor seleccione una de las siguientes opciones");
                                        System.out.print("A(Anterior Huesped), S(Siguiente huesped), M(Modificar huesped), V(Volver al menú): ");
                                        linea = s.nextLine();
                                        switch (linea.toUpperCase()) {
                                            case "A":{
                                                if(i > 0) i--;
                                                break;
                                            }
                                            case "S":{
                                                if(i < huespedesDTO.size()-1) i++;
                                                break;
                                            }
                                            case "M":{
                                                Boolean[] deleted = {false};
                                                HuespedDTO huespedDTONew = modificarHuesped(s, gestorHuesped, huespedesDTO.get(i), deleted);
                                                if (huespedDTONew != null) huespedesDTO.set(i, huespedDTONew);
                                                if (deleted[0]){
                                                    huespedesDTO.remove(i);
                                                }
                                                break;
                                            }
                                            case "V":{
                                                break menuPrincipal;
                                            }
                                            default: {
                                                System.out.println("Por favor ingrese un valor valido");
                                            }
                                        }

                                    } else if(!huespedesDTO.isEmpty()){
                                        System.out.println("Se encontró un unico huesped");
                                        System.out.println(huespedesDTO.get(0).toString());
                                        System.out.println("Por favor seleccione una de las siguientes opciones");
                                        System.out.print("M(Modificar huesped), V(Volver al menú): ");
                                        linea = s.nextLine();
                                        switch (linea.toUpperCase()) {
                                            case "M":{
                                                Boolean[] deleted = {false};
                                                HuespedDTO huespedDTONew = modificarHuesped(s, gestorHuesped, huespedesDTO.get(0), deleted);
                                                if (huespedDTONew != null) huespedesDTO.set(0, huespedDTONew);
                                                if (deleted[0]){
                                                    huespedesDTO.removeFirst();
                                                }
                                                break;
                                            }
                                            case "V":{
                                                break menuPrincipal;
                                            }
                                            default: {
                                                System.out.println("Por favor ingrese un valor valido");
                                            }
                                        }
                                    } else {
                                        System.out.println("No se encontraron huespedes con los datos dados, se procede a la alta de un nuevo huesped");
                                        forzarAltaHuesped = true;
                                        break menuPrincipal;
                                    }
                                }

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
                    try {
                        gestorUsuario.altaUsuario(usuario, password);
                    } catch (FracasoOperacion e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Usuario registrado correctamente");
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

    }

    public static HuespedDTO modificarHuesped(Scanner s, GestorHuesped gestorHuesped, HuespedDTO huespedDTO, Boolean[] deleted) {
        String linea;
        while(true) {
            System.out.print("Desea modificar el huesped(M), eliminarlo(E) o volver(V)? ");
            linea = s.nextLine();
            switch (linea.toUpperCase()) {
                case "M":{
                    System.out.println("Modificar Huesped");
                    System.out.println("Si desea volver al menu ingrese CANCELAR en cualquier dato");
                    System.out.println("Por favor ingrese los datos que desea modificar: ");
                    HuespedDTOBuilder huespedBuilder = ingresarDatosHuesped(s, false, huespedDTO);
                    if(huespedBuilder != null) {

                        HuespedDTO huespedDTONew = huespedBuilder.createHuespedDTO();
                        try {
                            gestorHuesped.modificarHuesped(huespedDTO.getTipoDoc(), huespedDTO.getNroDoc(), huespedDTONew);
                        } catch (DocumentoYaExistente e) {
                            System.out.println(e.getMessage());
                            boolean valido = false;
                            while (!valido) {
                                System.out.println("Desea ACEPTAR IGUALMENTE o CORREGIR?");
                                do linea = s.nextLine();
                                while (linea.isEmpty());
                                switch (linea.toUpperCase()) {
                                    case "ACEPTAR IGUALMENTE": {
                                        try {
                                            ArrayList<HuespedDTO> hList = gestorHuesped.buscarHuesped(new HuespedDTOBuilder().setTipoDoc(huespedDTONew.getTipoDoc()).setNroDoc(huespedDTONew.getNroDoc()).createHuespedDTO());
                                            HuespedDTO sobrescrito = hList.get(0);
                                            gestorHuesped.bajaHuesped(sobrescrito);
                                            gestorHuesped.modificarHuesped(huespedDTO.getTipoDoc(), huespedDTO.getNroDoc(), huespedDTONew);
                                        } catch (FracasoOperacion | DocumentoYaExistente ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        System.out.println("HUESPED SOBREESCRITO");
                                        valido = true;
                                        break;
                                    }
                                    case "CORREGIR": {
                                        System.out.print("Tipo de documento: ");
                                        do linea = s.nextLine();
                                        while (linea.isEmpty());
                                        if (linea.equals("CANCELAR")) return null;
                                        huespedBuilder = huespedBuilder.setTipoDoc(linea);

                                        while (!valido) {
                                            System.out.print("Numero de documento: ");
                                            do linea = s.nextLine();
                                            while (linea.isEmpty());
                                            if (linea.equals("CANCELAR")) return null;
                                            try {
                                                if (Integer.parseInt(linea) < 0) {
                                                    System.out.println("Por favor ingrese un numero valido");
                                                } else valido = true;
                                            } catch (NumberFormatException ex) {
                                                System.out.println("Por favor ingrese un numero valido");
                                            }
                                        }
                                        huespedBuilder = huespedBuilder.setNroDoc(linea);
                                        huespedDTO = huespedBuilder.createHuespedDTO();
                                        try {
                                            gestorHuesped.altaHuesped(huespedDTO);
                                        } catch (DocumentoYaExistente ex) {
                                            System.out.println(ex.getMessage());
                                            valido = false;
                                        } catch (FracasoOperacion ex) {
                                            throw new RuntimeException(ex);
                                        }
                                        break;
                                    }
                                    default: {
                                        System.out.println("Por favor ingrese un valor valido");
                                    }
                                }
                            }
                        } catch (FracasoOperacion e) {
                            throw new RuntimeException(e);
                        }
                        return huespedDTONew;
                    }

                    return null;
                }
                case "E":{
                    try {
                        gestorHuesped.bajaHuesped(huespedDTO);
                    } catch (FracasoOperacion e) {
                        throw new RuntimeException(e);
                    }
                    deleted[0] = true;
                    return null;
                }
                case "V":{
                    return null;
                }
                default: {
                    System.out.println("Por favor ingrese un valor valido");
                    break;
                }
            }
        }
    }

    public static HuespedDTOBuilder ingresarDatosHuesped(Scanner s, boolean datosObligatorios, HuespedDTO huespedDTO) {
        String linea;
        HuespedDTOBuilder builderHuesped = new HuespedDTOBuilder();
        boolean valido = false;
        boolean modificando = (huespedDTO != null);

        System.out.print(modificando? "Apellido("+huespedDTO.getApellido()+"): " : "Apellido: ");
        do linea = s.nextLine();
        while (linea.isEmpty() && datosObligatorios);
        if(linea.equals("CANCELAR")) return null;
        if(linea.isEmpty()){
            builderHuesped = builderHuesped.setApellido(modificando? huespedDTO.getApellido() : null);
        }else builderHuesped.setApellido(linea);


        System.out.print(modificando? "Nombre("+huespedDTO.getNombre()+"): " : "Nombre: ");
        do linea = s.nextLine();
        while (linea.isEmpty() && datosObligatorios);
        if(linea.equals("CANCELAR")) return null;
        if(linea.isEmpty()){
            builderHuesped = builderHuesped.setNombre(modificando? huespedDTO.getNombre() : null);
        }else builderHuesped.setNombre(linea);

        //QUIZAS CHEQUEAR SI EL TIPO DE DOCUMENTO ES VALIDO
        System.out.print(modificando? "Tipo de documento("+huespedDTO.getTipoDoc()+"): " : "Tipo de documento: ");
        do linea = s.nextLine();
        while (linea.isEmpty() && datosObligatorios);
        if(linea.equals("CANCELAR")) return null;
        if(linea.isEmpty()){
            builderHuesped = builderHuesped.setTipoDoc(modificando? huespedDTO.getTipoDoc() : null);
        }else builderHuesped.setTipoDoc(linea);

        while (!valido) {
            System.out.print(modificando? "Numero de documento("+huespedDTO.getNroDoc()+"): " : "Numero de documento: ");
            do linea = s.nextLine();
            while (linea.isEmpty() && datosObligatorios);
            if(linea.equals("CANCELAR")) return null;
            if (!linea.isEmpty()) {
                try {
                    if(Integer.parseInt(linea)<0) {
                        System.out.println("Por favor ingrese un numero valido");
                    }else valido = true;
                } catch (NumberFormatException e) {
                    System.out.println("Por favor ingrese un numero valido");
                }
            }else valido = true;
        }
        if(linea.isEmpty()){
            builderHuesped = builderHuesped.setNroDoc(modificando? huespedDTO.getNroDoc() : null);
        }else builderHuesped.setNroDoc(linea);

        if(datosObligatorios || modificando) {


            if (datosObligatorios) {
                System.out.print("Posicion frente al IVA(Por defecto Consumidor final): ");
                linea = s.nextLine();
                if (linea.equals("CANCELAR")) return null;
                if (linea.isEmpty()) builderHuesped = builderHuesped.setPosicionIva("Consumidor final");
                else builderHuesped = builderHuesped.setPosicionIva(linea);
            } else {
                System.out.print(modificando ? "Posicion frente al IVA(" + huespedDTO.getPosicionIva() + "): " : "Posicion frente al IVA: ");
                linea = s.nextLine();
                if (linea.equals("CANCELAR")) return null;
                else if (linea.isEmpty()) {
                    builderHuesped = builderHuesped.setPosicionIva(modificando ? huespedDTO.getPosicionIva() : null);
                } else builderHuesped.setPosicionIva(linea);
            }


            if (!linea.equals("Responsable Inscripto")) {
                valido = false;
                while (!valido && datosObligatorios) {
                    System.out.print("Desea ingresar un CUIT(S o N)? ");
                    do linea = s.nextLine();
                    while (linea.isEmpty());
                    if (linea.equals("CANCELAR")) return null;
                    linea = linea.toUpperCase();
                    switch (linea) {
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

            if (linea.equals("S") || linea.equals("Responsable Inscripto") || !datosObligatorios) {
                valido = false;
                while (!valido) {
                    System.out.print(modificando ? "CUIT(" + huespedDTO.getCuil() + "): " : "CUIT: ");
                    do linea = s.nextLine();
                    while (linea.isEmpty() && datosObligatorios);
                    if (linea.equals("CANCELAR")) return null;
                    if (!linea.isEmpty()) {
                        try {
                            if (Integer.parseInt(linea) < 0) {
                                System.out.println("Por favor ingrese un numero valido");
                            } else valido = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor ingrese un numero valido");
                        }
                    } else valido = true;
                }
                //CUIL O CUIT?
                if (linea.isEmpty()) {
                    builderHuesped = builderHuesped.setCuil(modificando ? huespedDTO.getCuil() : null);
                } else builderHuesped.setCuil(linea);
            } else builderHuesped = builderHuesped.setCuil(null); //NO SE SI ES NECESARIO EL ELSE

            valido = false;
            while (!valido) {
                System.out.print(modificando ? "Fecha de nacimiento(" + huespedDTO.getFechaNac().toString() + "): " : "Fecha de nacimiento(yyyy-MM-dd): ");
                do linea = s.nextLine();
                while (linea.isEmpty() && datosObligatorios);
                if (linea.equals("CANCELAR")) return null;
                if (!linea.isEmpty()) {
                    try {
                        LocalDate fecha = LocalDate.parse(linea);
                        valido = true;
                        builderHuesped = builderHuesped.setFechaNac(fecha);
                    } catch (DateTimeParseException e) {
                        System.out.println("Por favor ingrese una fecha valida");
                    }
                } else {
                    builderHuesped = builderHuesped.setFechaNac(modificando ? huespedDTO.getFechaNac() : null);
                    valido = true;
                }
            }


            System.out.print(modificando ? "Telefono(" + huespedDTO.getTelefono() + "): " : "Telefono: ");
            do linea = s.nextLine();
            while (linea.isEmpty() && datosObligatorios);
            if (linea.equals("CANCELAR")) return null;
            if (linea.isEmpty()) {
                builderHuesped = builderHuesped.setTelefono(modificando ? huespedDTO.getTelefono() : null);
            } else builderHuesped.setTelefono(linea);

            valido = false;
            while (!valido && datosObligatorios) {
                System.out.print("Desea ingresar un email(S o N)? ");
                do linea = s.nextLine();
                while (linea.isEmpty());
                if (linea.equals("CANCELAR")) return null;
                linea = linea.toUpperCase();
                switch (linea) {
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
            if (linea.equals("S") || !datosObligatorios) {
                System.out.print(modificando ? "Email(" + huespedDTO.getEmail() + "): " : "Email: ");
                do linea = s.nextLine();
                while (linea.isEmpty() && datosObligatorios);
                if (linea.equals("CANCELAR")) return null;
                if (linea.isEmpty()) {
                    builderHuesped = builderHuesped.setEmail(modificando ? huespedDTO.getEmail() : null);
                } else builderHuesped.setEmail(linea);
            }

            System.out.print(modificando ? "Ocupacion(" + huespedDTO.getOcupacion() + "): " : "Ocupacion: ");
            do linea = s.nextLine();
            while (linea.isEmpty() && datosObligatorios);
            if (linea.equals("CANCELAR")) return null;
            if (linea.isEmpty()) {
                builderHuesped = builderHuesped.setOcupacion(modificando ? huespedDTO.getOcupacion() : null);
            } else builderHuesped.setOcupacion(linea);

            System.out.print(modificando ? "Nacionalidad(" + huespedDTO.getNacionalidad() + "): " : "Nacionalidad: ");
            do linea = s.nextLine();
            while (linea.isEmpty() && datosObligatorios);
            if (linea.equals("CANCELAR")) return null;
            if (linea.isEmpty()) {
                builderHuesped = builderHuesped.setNacionalidad(modificando ? huespedDTO.getNacionalidad() : null);
            } else builderHuesped.setNacionalidad(linea);

            DireccionDTOBuilder builderDireccion = new DireccionDTOBuilder();
            System.out.println("Por favor ingrese los datos de la direccion del huesped: ");
            System.out.print(modificando ? "Pais(" + huespedDTO.getDireccion().getPais() + "): " : "Pais: ");
            do linea = s.nextLine();
            while (linea.isEmpty() && datosObligatorios);
            if (linea.equals("CANCELAR")) return null;
            if (linea.isEmpty()) {
                builderDireccion = builderDireccion.setPais(modificando ? huespedDTO.getDireccion().getPais() : null);
            } else builderDireccion = builderDireccion.setPais(linea);


            System.out.print(modificando ? "Provincia(" + huespedDTO.getDireccion().getProvincia() + "): " : "Provincia: ");
            do linea = s.nextLine();
            while (linea.isEmpty() && datosObligatorios);
            if (linea.equals("CANCELAR")) return null;
            if (linea.isEmpty()) {
                builderDireccion = builderDireccion.setProvincia(modificando ? huespedDTO.getDireccion().getProvincia() : null);
            } else builderDireccion = builderDireccion.setProvincia(linea);

            System.out.print(modificando ? "Localidad(" + huespedDTO.getDireccion().getLocalidad() + "): " : "Localidad: ");
            do linea = s.nextLine();
            while (linea.isEmpty() && datosObligatorios);
            if (linea.equals("CANCELAR")) return null;
            if (linea.isEmpty()) {
                builderDireccion = builderDireccion.setLocalidad(modificando ? huespedDTO.getDireccion().getLocalidad() : null);
            } else builderDireccion = builderDireccion.setLocalidad(linea);

            System.out.print(modificando ? "Codigo Postal(" + huespedDTO.getDireccion().getCodigoPostal() + "): " : "Codigo Postal: ");
            do linea = s.nextLine();
            while (linea.isEmpty() && datosObligatorios);
            if (linea.equals("CANCELAR")) return null;
            if (linea.isEmpty()) {
                builderDireccion = builderDireccion.setCodigoPostal(modificando ? huespedDTO.getDireccion().getCodigoPostal() : null);
            } else builderDireccion = builderDireccion.setCodigoPostal(linea);

            System.out.print(modificando ? "Domicilio(" + huespedDTO.getDireccion().getDomicilio() + "): " : "Domicilio: ");
            do linea = s.nextLine();
            while (linea.isEmpty() && datosObligatorios);
            if (linea.equals("CANCELAR")) return null;
            if (linea.isEmpty()) {
                builderDireccion = builderDireccion.setDomicilio(modificando ? huespedDTO.getDireccion().getDomicilio() : null);
            } else builderDireccion = builderDireccion.setDomicilio(linea);

            System.out.print(modificando ? "Depto(" + huespedDTO.getDireccion().getDepto() + "): " : "Depto: ");
            linea = s.nextLine();
            if (linea.equals("CANCELAR")) return null;
            else if (linea.isEmpty()) {
                builderDireccion = builderDireccion.setDepto(modificando ? huespedDTO.getDireccion().getDepto() : null);
            } else builderDireccion = builderDireccion.setDepto(linea);

            builderHuesped = builderHuesped.setDireccion(builderDireccion.createDireccionDTO());
        }
        return builderHuesped;
    }
}
    */