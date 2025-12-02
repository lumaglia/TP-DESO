package org.example.TP_DESO.dao.CSV;

import org.example.TP_DESO.dao.HuespedDAO;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.dto.HuespedDTOBuilder;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class HuespedCSV implements HuespedDAO {
    File fileHuesped = new File("api/bdd/huesped.csv");
    BufferedReader frHuesped;
    FileWriter fwHuesped;
    private DireccionCSV direccionCSV;

    public HuespedCSV() throws FracasoOperacion {
        super();
        try{
            fileHuesped.createNewFile();
            this.direccionCSV = new DireccionCSV();
        }
        catch(IOException e){
            throw new FracasoOperacion(e.getMessage());
        }
    }

    public void crearHuesped(Huesped huesped) throws FracasoOperacion{
        try {
            fwHuesped = new FileWriter(fileHuesped, true);
            fwHuesped.write(huesped.toString()+"\n");
            fwHuesped.close();
            direccionCSV.crearDireccion(huesped.getDireccion());
        } catch (IOException e) {
            throw new FracasoOperacion(e.getMessage());
        }
    }

    public ArrayList<HuespedDTO> obtenerHuesped(HuespedDTO huespedDTO) throws FracasoOperacion {

        ArrayList<HuespedDTO> huespedDTOs = new ArrayList<>();

        try {
            frHuesped = new BufferedReader(new FileReader(fileHuesped));

            String line = frHuesped.readLine();
            boolean valid = true;

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
                    huespedDTOs.add(new HuespedDTOBuilder().setTipoDoc(lineSplit[0]).setNroDoc(lineSplit[1]).setApellido(lineSplit[2]).setNombre(lineSplit[3]).setCuil(lineSplit[4]).setPosicionIva(lineSplit[5]).setFechaNac(LocalDate.parse(lineSplit[6])).setTelefono(lineSplit[7]).setEmail(lineSplit[8]).setOcupacion(lineSplit[9]).setNacionalidad(lineSplit[10]).setDireccion(direccionDTO).createHuespedDTO());
                }

                valid = true;
                line = frHuesped.readLine();
            }
            frHuesped.close();
        } catch (IOException e) {
            throw new FracasoOperacion(e.getMessage());
        }
        return huespedDTOs;
    }

    public void modificarHuesped(String tipoDoc, String numeroDoc, Huesped huesped) throws FracasoOperacion {
        try {
            direccionCSV.crearDireccion(huesped.getDireccion());
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
        } catch (IOException e) {
            throw new FracasoOperacion(e.getMessage());
        }

    }

    public void eliminarHuesped(String tipoDoc, String numeroDoc) throws FracasoOperacion {
        try {
            frHuesped = new BufferedReader(new FileReader(fileHuesped));
            StringBuilder f = new StringBuilder();
            String line = frHuesped.readLine();
            while (line != null) {
                if (!line.startsWith(tipoDoc + ";" + numeroDoc + ";")) {
                    f.append(line + "\n");
                }

                line = frHuesped.readLine();
            }
            frHuesped.close();
            fwHuesped = new FileWriter(fileHuesped);
            fwHuesped.write(f.toString());
            fwHuesped.close();
        } catch (IOException e) {
            throw new FracasoOperacion(e.getMessage());
        }
    }

    public DireccionDTO obtenerDireccion(String pais, String codigoPostal, String domicilio, String depto) throws FracasoOperacion {
        return direccionCSV.obtenerDireccion(pais, codigoPostal, domicilio, depto);
    }

    private boolean comparar(String campo, String csv){
        if(campo == null) return true;
        else if (campo.isEmpty()) return true;
        else return campo.equals(csv);
    }
}
