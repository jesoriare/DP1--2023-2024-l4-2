package org.springframework.samples.petclinic.Jugador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class JugadorService {
    
    private JugadorRepository jugadorRepository;

    @Autowired
    public JugadorService(JugadorRepository jugadorRepository){
        this.jugadorRepository=jugadorRepository;
    }
    @Transactional
    public void guardarJugador(Jugador jugador) throws DataAccessException{
        jugadorRepository.save(jugador);
    }
    @Transactional(readOnly = true)
    public Jugador getJugadorById(int id) throws DataAccessException{
        Optional<Jugador> jugador = jugadorRepository.findById(id);
        return jugador.isPresent()?jugador.get():null;
    }
    @Transactional
    public Jugador getJugadorByUsername(String username){
       Optional<Jugador> jugOptional=jugadorRepository.getJugadorByUsername(username);
       if(jugOptional.isPresent())return jugOptional.get();
       return null;
    }
}