package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.HuespedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Primary
@Service
public class HuespedDAOMySQL implements HuespedDAO {

    @Autowired
    private HuespedRepository huespedRepository;

    @Autowired
    private DireccionDAOMySQL direccionDAO;

    @Override
    public void crearHuesped(Huesped huesped) throws FracasoOperacion {
        try {
            Direccion direccionPersistida = direccionDAO.crearDireccion(huesped.getDireccion());
            huesped.setDireccion(direccionPersistida);
            huespedRepository.save(huesped);
        } catch (Exception e) {
            throw new FracasoOperacion("Error al crear huésped: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<HuespedDTO> obtenerHuesped(HuespedDTO filtro) throws FracasoOperacion {
        ArrayList<HuespedDTO> resultado = new ArrayList<>();
        try {

            List<Huesped> huespedes = huespedRepository.findAll();

            for (Huesped h : huespedes) {
                boolean coincide = true;

                if (filtro.getTipoDoc() != null && !filtro.getTipoDoc().equals(h.getTipoDoc())) coincide = false;
                if (filtro.getNroDoc() != null && !filtro.getNroDoc().equals(h.getNroDoc())) coincide = false;
                if (filtro.getApellido() != null && !filtro.getApellido().equals(h.getApellido())) coincide = false;
                if (filtro.getNombre() != null && !filtro.getNombre().equals(h.getNombre())) coincide = false;
                if (filtro.getCuil() != null && !filtro.getCuil().equals(h.getCuil())) coincide = false;
                if (filtro.getPosicionIva() != null && !filtro.getPosicionIva().equals(h.getPosicionIva())) coincide = false;
                if (filtro.getFechaNac() != null && !filtro.getFechaNac().equals(h.getFechaNac())) coincide = false;
                if (filtro.getTelefono() != null && !filtro.getTelefono().equals(h.getTelefono())) coincide = false;
                if (filtro.getEmail() != null && !filtro.getEmail().equals(h.getEmail())) coincide = false;
                if (filtro.getOcupacion() != null && !filtro.getOcupacion().equals(h.getOcupacion())) coincide = false;
                if (filtro.getNacionalidad() != null && !filtro.getNacionalidad().equals(h.getNacionalidad())) coincide = false;

                if (filtro.getDireccion() != null) {
                    if (filtro.getDireccion().getPais() != null && !filtro.getDireccion().getPais().equals(h.getDireccion().getPais())) coincide = false;
                    if (filtro.getDireccion().getCodigoPostal() != null && !filtro.getDireccion().getCodigoPostal().equals(h.getDireccion().getCodigoPostal())) coincide = false;
                    if (filtro.getDireccion().getDomicilio() != null && !filtro.getDireccion().getDomicilio().equals(h.getDireccion().getDomicilio())) coincide = false;
                    if (filtro.getDireccion().getDepto() != null && !filtro.getDireccion().getDepto().equals(h.getDireccion().getDepto())) coincide = false;
                }

                if (coincide) {
                    DireccionDTO direccionDTO = new DireccionDTO(
                            h.getDireccion().getDomicilio(),
                            h.getDireccion().getDepto(),
                            h.getDireccion().getCodigoPostal(),
                            h.getDireccion().getLocalidad(),
                            h.getDireccion().getProvincia(),
                            h.getDireccion().getPais()
                    );

                    HuespedDTO dto = new HuespedDTO(
                            h.getTipoDoc(),
                            h.getNroDoc(),
                            h.getApellido(),
                            h.getNombre(),
                            h.getCuil(),
                            h.getPosicionIva(),
                            h.getFechaNac(),
                            h.getTelefono(),
                            h.getEmail(),
                            h.getOcupacion(),
                            h.getNacionalidad(),
                            direccionDTO
                    );

                    resultado.add(dto);
                }
            }
        } catch (Exception e) {
            throw new FracasoOperacion("Error al obtener huéspedes: " + e.getMessage());
        }
        return resultado;
    }

    @Override
    public ArrayList<HuespedDTO> obtenerHuesped() throws FracasoOperacion {
        try{
            return huespedRepository.findAll().stream().map(h -> {
                DireccionDTO direccionDTO = new DireccionDTO(
                        h.getDireccion().getDomicilio(),
                        h.getDireccion().getDepto(),
                        h.getDireccion().getCodigoPostal(),
                        h.getDireccion().getLocalidad(),
                        h.getDireccion().getProvincia(),
                        h.getDireccion().getPais()
                );

                return new HuespedDTO(
                        h.getTipoDoc(),
                        h.getNroDoc(),
                        h.getApellido(),
                        h.getNombre(),
                        h.getCuil(),
                        h.getPosicionIva(),
                        h.getFechaNac(),
                        h.getTelefono(),
                        h.getEmail(),
                        h.getOcupacion(),
                        h.getNacionalidad(),
                        direccionDTO
                );
            }).collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            throw new FracasoOperacion("Error al obtener huesped: " + e.getMessage());
        }
    }

    @Override
    public void modificarHuesped(String tipoDoc, String numeroDoc, Huesped huesped) throws FracasoOperacion {
        try {
            Optional<Huesped> existente = huespedRepository.findByTipoDocAndNroDoc(tipoDoc, numeroDoc);
            if (existente.isPresent()) {
                Huesped h = existente.get();

                if(!(Objects.equals(h.getTipoDoc(), huesped.getTipoDoc())&&Objects.equals(h.getNroDoc(), huesped.getNroDoc()))){
                    try{
                        huespedRepository.delete(h);
                    }catch(Exception e){
                        throw new FracasoOperacion("No se puede alterar Documento:" + e.getMessage());
                    }
                    h.setTipoDoc(huesped.getTipoDoc());
                    h.setNroDoc(huesped.getNroDoc());
                }

                if (huesped.getDireccion() != null) {
                    Direccion direccionProcesada = direccionDAO.crearDireccion(huesped.getDireccion());
                    h.setDireccion(direccionProcesada);
                }

                h.setApellido(huesped.getApellido());
                h.setNombre(huesped.getNombre());
                h.setCuil(huesped.getCuil());
                h.setPosicionIva(huesped.getPosicionIva());
                h.setFechaNac(huesped.getFechaNac());
                h.setTelefono(huesped.getTelefono());
                h.setEmail(huesped.getEmail());
                h.setOcupacion(huesped.getOcupacion());
                h.setNacionalidad(huesped.getNacionalidad());


                huespedRepository.save(h);
            } else {
                throw new FracasoOperacion("Huésped no encontrado con tipoDoc: " + tipoDoc + " y nroDoc: " + numeroDoc);
            }
        } catch (Exception e) {
            throw new FracasoOperacion("Error al modificar huésped: " + e.getMessage());
        }
    }

    @Override
    public void eliminarHuesped(String tipoDoc, String numeroDoc) throws FracasoOperacion {
        try {
            Optional<Huesped> existente = huespedRepository.findByTipoDocAndNroDoc(tipoDoc, numeroDoc);
            if (existente.isPresent()) {

                Huesped h = existente.get();
                Direccion d = h.getDireccion();

                huespedRepository.delete(existente.get());
                boolean dirEnUso = huespedRepository.existsByDireccion(d);

                if(!dirEnUso){
                    direccionDAO.eliminarDireccion(d);
                }


            } else {
                throw new FracasoOperacion("Huésped no encontrado con tipoDoc: " + tipoDoc + " y nroDoc: " + numeroDoc);
            }
        } catch (Exception e) {
            throw new FracasoOperacion("Error al eliminar huésped: " + e.getMessage());
        }
    }
}
