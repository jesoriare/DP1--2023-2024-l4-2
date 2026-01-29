package org.springframework.samples.petclinic.Jugador;

import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface JugadorRepository extends CrudRepository<Jugador,Integer>{
    
    @Query("SELECT jugador FROM Jugador jugador WHERE jugador.user.username LIKE :username")
    Optional<Jugador> getJugadorByUsername(String username) throws DataAccessException;
}
