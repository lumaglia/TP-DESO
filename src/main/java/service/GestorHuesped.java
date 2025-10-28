package service;

import dao.HuespedCSV;
import dao.HuespedDAO;
import domain.Direccion;
import domain.Huesped;
import dto.DireccionDTO;
import dto.HuespedDTO;
import exceptions.DocumentoYaExistente;

import java.io.IOException;
import java.util.ArrayList;

public class GestorHuesped {

    HuespedDAO huespedDAO;

    public GestorHuesped() throws IOException {
        super();
        huespedDAO = new HuespedCSV();
    }

    public void AltaHuesped(HuespedDTO huespedDTO) throws DocumentoYaExistente, IOException {

        if(!huespedDAO.obtenerHuesped(new HuespedDTO(huespedDTO.getTipoDoc(), huespedDTO.getNroDoc())).isEmpty()) {
            throw new DocumentoYaExistente("El tipo y numero de documento ya existen en el sistema");
        }

        DireccionDTO direccionDTO = huespedDTO.getDireccion();
        Direccion direccion = new Direccion();
        direccion.setDomicilio(direccionDTO.getDomicilio());
        direccion.setDepto(direccionDTO.getDepto());
        direccion.setCodigoPostal(direccionDTO.getCodigoPostal());
        direccion.setLocalidad(direccionDTO.getLocalidad());
        direccion.setProvincia(direccionDTO.getProvincia());
        direccion.setPais(direccionDTO.getPais());

        //Esto esta demas porque crearHuesped() ya lo hace, lo puse porque estaba en el diagrama de secuencia
        huespedDAO.crearDireccion(direccion);

        Huesped huesped = new Huesped();
        huesped.setNombre(huespedDTO.getNombre());
        huesped.setApellido(huespedDTO.getApellido());
        huesped.setTipoDoc(huespedDTO.getTipoDoc());
        huesped.setNroDoc(huespedDTO.getNroDoc());
        huesped.setCuil(huespedDTO.getCuil());
        huesped.setPosicionIva(huespedDTO.getPosicionIva());
        huesped.setFechaNac(huespedDTO.getFechaNac());
        huesped.setTelefono(huespedDTO.getTelefono());
        huesped.setEmail(huespedDTO.getEmail());
        huesped.setOcupacion(huespedDTO.getOcupacion());
        huesped.setNacionalidad(huespedDTO.getNacionalidad());
        huesped.setDireccion(direccion);

        huespedDAO.crearHuesped(huesped);

    }

}
