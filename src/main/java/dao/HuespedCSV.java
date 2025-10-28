package dao;

import domain.Huesped;
import dto.HuespedDTO;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class HuespedCSV implements HuespedDAO {

    File file = new File("huesped.csv");
    BufferedReader fr;
    FileWriter fw;

    public HuespedCSV() throws IOException {
        super();
        file.createNewFile();
    }

    public void crearHuesped(Huesped huesped) {
        try {
            fw = new FileWriter(file, true);
            fw.write(huesped.toString()+"\n");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<HuespedDTO> obtenerHuesped(HuespedDTO huespedDTO) throws IOException {
        try {
            fr = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String line = fr.readLine();
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
                        null           // direccion
                ));
            }

            valid = true;
            line = fr.readLine();
        }
        fr.close();
        return huespedDTOs;
    }

    public void modificarHuesped(String tipoDoc, String numeroDoc, Huesped huesped) {
        try {
            fr = new BufferedReader(new FileReader(file));
            StringBuilder f = new StringBuilder();
            String line = fr.readLine();
            while (line != null) {
                if(!line.contains(tipoDoc+";"+numeroDoc+";")){
                    f.append(line+"\n");
                }else {
                    f.append(huesped.toString()+"\n");
                }

                line = fr.readLine();
            }
            fr.close();
            fw = new FileWriter(file);
            fw.write(f.toString());
            fw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void eliminarHuesped(String tipoDoc, String numeroDoc){
        try {
            fr = new BufferedReader(new FileReader(file));
            StringBuilder f = new StringBuilder();
            String line = fr.readLine();
            while (line != null) {
                if(!line.contains(tipoDoc+";"+numeroDoc+";")){
                    f.append(line+"\n");
                }

                line = fr.readLine();
            }
            fr.close();
            fw = new FileWriter(file);
            fw.write(f.toString());
            fw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean comparar(String campo, String csv){
        if(campo == null) return true;
        else if (campo.equals(csv)) return true;
        else return false;
    }

}
