package org.springframework.samples.petclinic.Jugador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadisticasService {
    
    private EstadisticasRepository estadisticasRepository;
    
    @Autowired
    public EstadisticasService(EstadisticasRepository estadisticasRepository){
        this.estadisticasRepository = estadisticasRepository;
    }
    @Transactional
    public void guardarEstadisticas(Estadisticas estadisticas) throws DataAccessException{
        estadisticasRepository.save(estadisticas);
    }
     @Transactional
    public Estadisticas getEstadisticas(int id) throws DataAccessException{
        Optional<Estadisticas> estadisticas=estadisticasRepository.findById(id);
        return estadisticas.isPresent()?estadisticas.get():null;
    }
}
