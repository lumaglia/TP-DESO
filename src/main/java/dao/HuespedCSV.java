package dao;

import domain.Direccion;
import domain.Huesped;
import dto.DireccionDTO;
import dto.HuespedDTO;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class HuespedCSV implements HuespedDAO {

    File fileHuesped = new File("huesped.csv");
    File fileDireccion = new File("direccion.csv");
    BufferedReader frHuesped;
    FileWriter fwHuesped;
    BufferedReader frDireccion;
    FileWriter fwDireccion;

    public HuespedCSV() throws IOException {
        super();
        fileHuesped.createNewFile();
        fileDireccion.createNewFile();
    }

    public void crearHuesped(Huesped huesped) {
        try {
            fwHuesped = new FileWriter(fileHuesped, true);
            fwHuesped.write(huesped.toString()+"\n");
            fwHuesped.close();
            crearDireccion(huesped.getDireccion());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<HuespedDTO> obtenerHuesped(HuespedDTO huespedDTO) throws IOException {
        try {
            frHuesped = new BufferedReader(new FileReader(fileHuesped));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String line = frHuesped.readLine();
        boolean valid = true;
        ArrayList<HuespedDTO> huespedDTOs = new ArrayList<>();
        while (line != null) {
            String[] lineSplit = line.split(";");

            valid = comparar(huespedDTO.getTipoDoc(), lineSplit[0]);
            valid = valid && comparar(huespedDTO.getNroDoc(), lineSplit[1]);
            valid = valid && comparar(huespedDTO.getApellido(), lineSplit[2]);
            valid = valid && comparar(huespedDTO.getNombre(), lineSplit[3]);
            valid = valid && comparar(huespedDTO.getCuil(), lineSplit[4]);
            valid = valid && comparar(huespedDTO.getPosicionIva(), lineSplit[5]);

            if(huespedDTO.getFechaNac() != null){
                valid = valid && comparar(huespedDTO.getFechaNac().toString(), lineSplit[6]);
            }
            valid = valid && comparar(huespedDTO.getTelefono(), lineSplit[7]);
            valid = valid && comparar(huespedDTO.getEmail(), lineSplit[8]);
            valid = valid && comparar(huespedDTO.getOcupacion(), lineSplit[9]);
            valid = valid && comparar(huespedDTO.getNacionalidad(), lineSplit[10]);
            if(huespedDTO.getDireccion() != null){
                valid = valid && comparar(huespedDTO.getDireccion().getPais(), lineSplit[11]);
                valid = valid && comparar(huespedDTO.getDireccion().getCodigoPostal(), lineSplit[12]);
                valid = valid && comparar(huespedDTO.getDireccion().getDomicilio(), lineSplit[13]);
                valid = valid && comparar(huespedDTO.getDireccion().getDepto(), lineSplit[14]);
            }

            if(valid){
                DireccionDTO direccionDTO = obtenerDireccion(lineSplit[11], lineSplit[12], lineSplit[13], lineSplit[14]);
                huespedDTOs.add(new HuespedDTO(
                        lineSplit[0],  // tipoDoc
                        lineSplit[1],  // nroDoc
                        lineSplit[2],  // apellido
                        lineSplit[3],  // nombre
                        lineSplit[4],  // cuil
                        lineSplit[5],  // posicionIva
                        LocalDate.parse(lineSplit[6]),  // fechaNac
                        lineSplit[7],  // telefono
                        lineSplit[8],  // email
                        lineSplit[9],  // ocupacion
                        lineSplit[10], // nacionalidad
                        direccionDTO   // direccion
                ));
            }

            valid = true;
            line = frHuesped.readLine();
        }
        frHuesped.close();
        return huespedDTOs;
    }

    public void modificarHuesped(String tipoDoc, String numeroDoc, Huesped huesped) {
        try {
            frHuesped = new BufferedReader(new FileReader(fileHuesped));
            StringBuilder f = new StringBuilder();
            String line = frHuesped.readLine();
            while (line != null) {
                if(!line.startsWith(tipoDoc+";"+numeroDoc+";")){
                    f.append(line+"\n");
                }else {
                    f.append(huesped.toString()+"\n");
                }

                line = frHuesped.readLine();
            }
            frHuesped.close();
            fwHuesped = new FileWriter(fileHuesped);
            fwHuesped.write(f.toString());
            fwHuesped.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void eliminarHuesped(String tipoDoc, String numeroDoc){
        try {
            frHuesped = new BufferedReader(new FileReader(fileHuesped));
            StringBuilder f = new StringBuilder();
            String line = frHuesped.readLine();
            while (line != null) {
                if(!line.startsWith(tipoDoc+";"+numeroDoc+";")){
                    f.append(line+"\n");
                }

                line = frHuesped.readLine();
            }
            frHuesped.close();
            fwHuesped = new FileWriter(fileHuesped);
            fwHuesped.write(f.toString());
            fwHuesped.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void crearDireccion(Direccion direccion) {
        try {
            if(obtenerDireccion(
                    direccion.getPais(),
                    direccion.getCodigoPostal(),
                    direccion.getDomicilio(),
                    direccion.getDepto()
            ) == null){
                fwDireccion = new FileWriter(fileDireccion, true);
                fwDireccion.write(direccion.toString()+"\n");
                fwDireccion.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DireccionDTO obtenerDireccion(String pais, String codigoPostal, String domicilio, String depto) throws IOException {
        try {
            frDireccion = new BufferedReader(new FileReader(fileDireccion));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String line = frDireccion.readLine();
        while (line != null) {
            String[] lineSplit = line.split(";");
            if(line.startsWith(pais+";"+codigoPostal+";"+domicilio+";"+depto+";")) {
                frDireccion.close();

                return new DireccionDTO(
                        lineSplit[2],
                        lineSplit[3],
                        lineSplit[1],
                        lineSplit[4],
                        lineSplit[5],
                        lineSplit[0]
                );
            }

            line = frDireccion.readLine();
        }
        frDireccion.close();
        return null;
    }

    private boolean comparar(String campo, String csv){
        if(campo == null) return true;
        else if (campo.equals(csv)) return true;
        else return false;
    }

}
