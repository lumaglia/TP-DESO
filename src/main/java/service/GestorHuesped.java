package service;

import dao.HuespedCSV;
import dao.HuespedDAO;
import domain.Direccion;
import domain.Huesped;
import dto.DireccionDTO;
import dto.HuespedDTO;
import dto.HuespedDTOBuilder;
import exceptions.DocumentoYaExistente;
import exceptions.FracasoOperacion;

import java.io.IOException;
import java.util.ArrayList;

public class GestorHuesped {

    HuespedDAO huespedDAO;

    public GestorHuesped() throws FracasoOperacion {
        super();
        huespedDAO = new HuespedCSV();
    }

    public void altaHuesped(HuespedDTO huespedDTO) throws DocumentoYaExistente, FracasoOperacion {


        if(!huespedDAO.obtenerHuesped(new HuespedDTOBuilder().setTipoDoc(huespedDTO.getTipoDoc()).setNroDoc(huespedDTO.getNroDoc()).createHuespedDTO()).isEmpty()) {
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
        //huespedDAO.crearDireccion(direccion);

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

    public void bajaHuesped(HuespedDTO huespedDTO) throws FracasoOperacion {
        huespedDAO.eliminarHuesped(huespedDTO.getTipoDoc(), huespedDTO.getNroDoc());
    }

    public ArrayList<HuespedDTO> buscarHuesped(HuespedDTO huespedDTO) throws FracasoOperacion {
        return huespedDAO.obtenerHuesped(huespedDTO);
    }

    //NUNCA CHEQUEA QUE NO REPITA TIPODOC Y NUMDOC CUANDO MODIFICAS, DEBERIA LANZAR DOCUMENTO REPETIDO EXCEPTION
    public void modificarHuesped(String tipoDoc, String numeroDoc, HuespedDTO huespedDTO) throws FracasoOperacion{
        DireccionDTO direccionDTO = huespedDTO.getDireccion();
        Direccion direccion = new Direccion();
        direccion.setDomicilio(direccionDTO.getDomicilio());
        direccion.setDepto(direccionDTO.getDepto());
        direccion.setCodigoPostal(direccionDTO.getCodigoPostal());
        direccion.setLocalidad(direccionDTO.getLocalidad());
        direccion.setProvincia(direccionDTO.getProvincia());
        direccion.setPais(direccionDTO.getPais());

        //Esto esta demas porque crearHuesped() ya lo hace, lo puse porque estaba en el diagrama de secuencia
        //huespedDAO.crearDireccion(direccion);

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

        huespedDAO.modificarHuesped(tipoDoc, numeroDoc, huesped);

    }

}
