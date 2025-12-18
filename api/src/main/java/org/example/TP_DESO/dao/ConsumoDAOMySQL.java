package org.example.TP_DESO.dao;

import org.springframework.transaction.annotation.Transactional;
import org.example.TP_DESO.domain.Consumo;
import org.example.TP_DESO.repository.ConsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@Transactional
public class ConsumoDAOMySQL implements ConsumoDAO {
    @Autowired
    private ConsumoRepository consumoRepository;

    @Override
    public ArrayList<Consumo> consumosEstadia(Long idEstadia) {
        return new ArrayList<>(consumoRepository.findByEstadia_IdEstadiaAndFacturaIsNull(idEstadia));
    }
}
