package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.HuespedDAO;
import org.example.TP_DESO.dao.HuespedDAOMySQL;
import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.dto.HuespedDTOBuilder;
import org.example.TP_DESO.exceptions.DocumentoYaExistente;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GestorHuesped {

    private final HuespedDAO huespedDAO;

    @Autowired
    private GestorHuesped(HuespedDAO huespedDAO) throws FracasoOperacion {
        this.huespedDAO = new HuespedDAOMySQL();
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

    public void modificarHuesped(String tipoDoc, String numeroDoc, HuespedDTO huespedDTO) throws DocumentoYaExistente, FracasoOperacion{

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
