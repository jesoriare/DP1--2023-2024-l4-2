package org.springframework.samples.petclinic.Partida;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.Jugador.Jugador;
import org.springframework.samples.petclinic.exceptions.ResourceNotFoundException;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PartidaService {
    
    private PartidaRepository partidaRepository;

    @Autowired
    public PartidaService(PartidaRepository partidaRepository){
        this.partidaRepository=partidaRepository;
    }
    @Transactional
    public void guardarPartida(Partida partida) throws DataAccessException{
        partidaRepository.save(partida);
    }
    @Transactional(readOnly = true)
    public Partida getPartidaById(int id) throws DataAccessException{
        Optional<Partida> partida = partidaRepository.findById(id);
        return partida.isPresent()?partida.get():null;
    }
     @Transactional
     public List<Partida> getPartidas(){
        return (List<Partida>)(partidaRepository.findAll());
     }

     @Transactional
     public void borrarPartida(int id) throws DataAccessException {
         Partida toDelete = getPartidaById(id);
         if (toDelete != null) {
             partidaRepository.delete(toDelete);
         } else {
             throw new ResourceNotFoundException("No se ha encontrado la partida");
         }
     }
    @Transactional
    public Partida getPartidaByUser(int userId) {
        List<Partida> partidas=(List<Partida>)(partidaRepository.findAll());
        for(Partida p:partidas){
            for(Jugador j:p.getJugadores()){
                if(j.getUser().getId()==userId)return p;
            }
        }
        return null;
    }
     
}

