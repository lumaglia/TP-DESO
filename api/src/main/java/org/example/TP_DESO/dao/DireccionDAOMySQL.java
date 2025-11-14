package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DireccionDAOMySQL implements DireccionDAO {

    @Autowired
    private DireccionRepository direccionRepository;

    @Override
    public Direccion crearDireccion(Direccion direccion) throws FracasoOperacion {
        try {
            Optional<Direccion> existente = direccionRepository
                    .findByPaisAndCodigoPostalAndDomicilioAndDepto(
                            direccion.getPais(),
                            direccion.getCodigoPostal(),
                            direccion.getDomicilio(),
                            direccion.getDepto()
                    );

            if (existente.isPresent()) {
                return existente.get();
            }
            else{
                return direccionRepository.save(direccion);
            }
        } catch (Exception e) {
            throw new FracasoOperacion(e.getMessage());
        }
    }

    @Override
    public DireccionDTO obtenerDireccion(String pais, String codigoPostal, String domicilio, String depto) throws FracasoOperacion {
        try {
            Optional<Direccion> direccionOpt = direccionRepository
                    .findByPaisAndCodigoPostalAndDomicilioAndDepto(pais, codigoPostal, domicilio, depto);

            if (direccionOpt.isPresent()) {
                Direccion d = direccionOpt.get();
                return new DireccionDTO(
                        d.getDomicilio(),
                        d.getDepto(),
                        d.getCodigoPostal(),
                        d.getLocalidad(),
                        d.getProvincia(),
                        d.getPais()
                );
            }
            return null;
        } catch (Exception e) {
            throw new FracasoOperacion(e.getMessage());
        }
    }
}
