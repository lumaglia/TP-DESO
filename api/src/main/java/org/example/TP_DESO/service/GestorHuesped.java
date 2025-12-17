package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.EstadiaDAO;
import org.example.TP_DESO.dao.EstadiaDAOMySQL;
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
import java.util.Objects;

@Service
public class GestorHuesped {
    @Autowired
    private HuespedDAO huespedDAO;
    @Autowired
    private EstadiaDAO estadiaDAO;


    private GestorHuesped(HuespedDAO huespedDAO) throws FracasoOperacion {

    }

    public void altaHuesped(HuespedDTO huespedDTO) throws DocumentoYaExistente, FracasoOperacion {


        if (!huespedDAO.obtenerHuesped(new HuespedDTOBuilder().setTipoDoc(huespedDTO.getTipoDoc()).setNroDoc(huespedDTO.getNroDoc()).createHuespedDTO()).isEmpty()) {
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

    public void bajaHuesped(HuespedDTO dto) throws FracasoOperacion {
        boolean seAlojo = estadiaDAO.existeEstadiaDeHuesped(dto.getTipoDoc(), dto.getNroDoc());
        if (seAlojo) {
            throw new FracasoOperacion("El huésped no puede ser eliminado pues se ha alojado en el hotel en alguna oportunidad.");
        }
        huespedDAO.eliminarHuesped(dto.getTipoDoc(), dto.getNroDoc());
    }

    public ArrayList<HuespedDTO> buscarHuesped(HuespedDTO huespedDTO) throws FracasoOperacion {
        if (huespedDTO.getNombre() == null &&
                huespedDTO.getApellido() == null &&
                huespedDTO.getTipoDoc() == null &&
                huespedDTO.getNroDoc() == null &&
                huespedDTO.getCuil() == null &&
                huespedDTO.getPosicionIva() == null &&
                huespedDTO.getFechaNac() == null &&
                huespedDTO.getTelefono() == null &&
                huespedDTO.getEmail() == null &&
                huespedDTO.getOcupacion() == null &&
                huespedDTO.getNacionalidad() == null &&
                huespedDTO.getDireccion() == null) {
            return huespedDAO.obtenerHuesped();
        } else {
            return huespedDAO.obtenerHuesped(huespedDTO);
        }
    }

    public void modificarHuesped(String tipoDoc, String numeroDoc, HuespedDTO huespedDTO, boolean override) throws DocumentoYaExistente, FracasoOperacion {
        if (!Objects.equals(tipoDoc, huespedDTO.getTipoDoc()) || !Objects.equals(numeroDoc, huespedDTO.getNroDoc())) {
            if (!huespedDAO.obtenerHuesped(new HuespedDTOBuilder().setTipoDoc(huespedDTO.getTipoDoc()).setNroDoc(huespedDTO.getNroDoc()).createHuespedDTO()).isEmpty()){
                if(override){
                    if (estadiaDAO.existeEstadiaDeHuesped(huespedDTO.getTipoDoc(), huespedDTO.getNroDoc())) {
                        throw new FracasoOperacion("El huésped no puede ser sobreescrito pues se ha alojado en el hotel en alguna oportunidad.");
                    }
                }else{
                    throw new DocumentoYaExistente("El tipo y numero de documento ya existen en el sistema");
                }
            }
            if (estadiaDAO.existeEstadiaDeHuesped(tipoDoc, numeroDoc)) {
                throw new FracasoOperacion("El huésped no puede ser modificado pues se ha alojado en el hotel en alguna oportunidad.");
            }
        }

        if (!huespedDAO.obtenerHuesped(new HuespedDTOBuilder().setTipoDoc(huespedDTO.getTipoDoc()).setNroDoc(huespedDTO.getNroDoc()).createHuespedDTO()).isEmpty()) {
            if((Objects.equals(tipoDoc, huespedDTO.getTipoDoc()) && Objects.equals(numeroDoc, huespedDTO.getNroDoc()))){


            }else{

            }
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

    public void modificarHuesped(HuespedDTO huespedDTO) throws FracasoOperacion {
        Huesped huesped = new Huesped();

        huesped.setTipoDoc(huespedDTO.getTipoDoc());
        huesped.setNroDoc(huespedDTO.getNroDoc());

        huesped.setNombre(huespedDTO.getNombre());
        huesped.setApellido(huespedDTO.getApellido());
        huesped.setTelefono(huespedDTO.getTelefono());
        huesped.setEmail(huespedDTO.getEmail());
        huesped.setOcupacion(huespedDTO.getOcupacion());
        huesped.setNacionalidad(huespedDTO.getNacionalidad());
        huesped.setFechaNac(huespedDTO.getFechaNac());
        huesped.setCuil(huespedDTO.getCuil());
        huesped.setPosicionIva(huespedDTO.getPosicionIva());

        if (huespedDTO.getDireccion() != null) {
            Direccion dirEntity = new Direccion();

            dirEntity.setDomicilio(huespedDTO.getDireccion().getDomicilio());
            dirEntity.setDepto(huespedDTO.getDireccion().getDepto());
            dirEntity.setCodigoPostal(huespedDTO.getDireccion().getCodigoPostal());
            dirEntity.setLocalidad(huespedDTO.getDireccion().getLocalidad());
            dirEntity.setProvincia(huespedDTO.getDireccion().getProvincia());
            dirEntity.setPais(huespedDTO.getDireccion().getPais());

            huesped.setDireccion(dirEntity);
        }

        huespedDAO.modificarHuesped(
                huespedDTO.getTipoDoc(),
                huespedDTO.getNroDoc(),
                huesped
        );
    }
}