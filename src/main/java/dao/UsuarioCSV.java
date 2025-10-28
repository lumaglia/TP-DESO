package dao;

import domain.Usuario;
import dto.UsuarioDTO;
import service.GestorUsuario;

import java.io.*;

public class UsuarioCSV implements UsuarioDAO {
    File file = new File("usuarios.csv");
    BufferedReader fr;
    FileWriter fw;

    public UsuarioCSV() throws IOException {
       super();
       file.createNewFile();
    }

    public void CrearUsuario(Usuario usuario){
        try {
            fw = new FileWriter(file, true);
            fw.write(usuario.toString() + "\n");
            fw.close();
        }  catch (IOException e) {
        throw new RuntimeException(e);
    }
}

    public UsuarioDTO ObtenerUsuario(UsuarioDTO usuario) throws IOException {
        try {
            fr = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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
        return null;
    }


    public void ModificarUsuario(String nombreUsuario, Usuario usuario){
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
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void EliminarUsuario(String usuario){
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
        }catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

