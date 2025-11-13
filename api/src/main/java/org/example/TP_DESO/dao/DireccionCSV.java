package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.io.*;

public class DireccionCSV implements DireccionDAO {
    File fileDireccion = new File("api/bdd/direccion.csv");
    BufferedReader frDireccion;
    FileWriter fwDireccion;

    public DireccionCSV() throws FracasoOperacion {
        super();
        try{
            fileDireccion.createNewFile();
        }
        catch(IOException e){
            throw new FracasoOperacion(e.getMessage());
        }
    }

    public void crearDireccion(Direccion direccion) throws FracasoOperacion {
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
            throw new FracasoOperacion(e.getMessage());
        }
    }

    public DireccionDTO obtenerDireccion(String pais, String codigoPostal, String domicilio, String depto) throws FracasoOperacion {
        try {
            frDireccion = new BufferedReader(new FileReader(fileDireccion));

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
        } catch (IOException e) {
            throw new FracasoOperacion(e.getMessage());
        }

        return null;
    }

    private boolean comparar(String campo, String csv){
        if(campo == null) return true;
        else if (campo.equals(csv)) return true;
        else return false;
    }
}
