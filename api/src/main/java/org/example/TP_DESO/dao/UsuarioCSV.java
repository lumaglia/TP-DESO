package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Usuario;
import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.io.*;

public class UsuarioCSV implements UsuarioDAO {
    File file = new File("api/bdd/usuarios.csv");
    BufferedReader fr;
    FileWriter fw;

    public UsuarioCSV() throws FracasoOperacion {
       super();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new FracasoOperacion(e.getMessage());
        }
    }

    public void CrearUsuario(Usuario usuario) throws FracasoOperacion {
        try {
            fw = new FileWriter(file, true);
            fw.write(usuario.toString() + "\n");
            fw.close();
        }  catch (IOException e) {
        throw new FracasoOperacion(e.getMessage());
    }
}

    public UsuarioDTO ObtenerUsuario(UsuarioDTO usuario) throws FracasoOperacion {
        try {
            fr = new BufferedReader(new FileReader(file));

            String line = fr.readLine();
            boolean valid = true;
            while (line != null) {
                String[] lineSplit = line.split(";");

                valid = usuario.getUsuario().equals(lineSplit[0]);
                valid = valid && usuario.getContrasenna().equals(lineSplit[1]);
                if(valid){
                    line = null;
                    return usuario;
                } else {
                    line = fr.readLine();
                    valid = true;
                }
            }
            fr.close();
        } catch (IOException e) {
            throw new FracasoOperacion(e.getMessage());
        }
        return null;
    }


    public void ModificarUsuario(String nombreUsuario, Usuario usuario) throws  FracasoOperacion {
        try {
            fr = new BufferedReader(new FileReader(file));
            StringBuilder f = new StringBuilder();
            String line = fr.readLine();
            while (line != null){
                if(!line.startsWith(nombreUsuario + ";")){
                    f.append(line+"\n");
                }else {
                    f.append(usuario.toString()+"\n");
                }
                line = fr.readLine();
            }
            fr.close();
            fw = new FileWriter(file);
            fw.write(f.toString());
            fw.close();
        } catch (IOException e) {
            throw new FracasoOperacion(e.getMessage());
        }
    }

    public void EliminarUsuario(String usuario) throws FracasoOperacion {
        try {
            fr = new BufferedReader(new FileReader(file));
            StringBuilder f = new StringBuilder();
            String line = fr.readLine();
            while (line != null){
                if(!line.startsWith(usuario + ";")){
                    f.append(line+"\n");
                }
                line = fr.readLine();
            }
            fr.close();
            fw = new FileWriter(file);
            fw.write(f.toString());
            fw.close();
        } catch (IOException e) {
            throw new FracasoOperacion(e.getMessage());
        }
    }

}

