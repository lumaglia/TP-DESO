package dao;

import domain.Direccion;
import dto.DireccionDTO;

import java.io.*;

public class DireccionCSV implements DireccionDAO {
    File fileDireccion = new File("direccion.csv");
    BufferedReader frDireccion;
    FileWriter fwDireccion;

    public DireccionCSV() throws IOException {
        super();
        fileDireccion.createNewFile();
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
