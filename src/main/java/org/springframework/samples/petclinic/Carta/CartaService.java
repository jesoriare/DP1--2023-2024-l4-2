package org.springframework.samples.petclinic.Carta;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.Partida.PartidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartaService {
    
    
    private CartaRepository cartaRepository;

    @Autowired
    public CartaService(CartaRepository cartaRepository){
        this.cartaRepository=cartaRepository;
    }

    @Transactional
    public void guardarCarta(Carta carta) throws DataAccessException{
        cartaRepository.save(carta);
    }

    @Transactional
    public List<Carta> getCartas() throws DataAccessException{
        return (List<Carta>) cartaRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Carta getCartaById(int id) throws DataAccessException{
        Optional<Carta> carta = cartaRepository.findById(id);
        return carta.isPresent()?carta.get():null;
    }
    @Transactional
    public void borrarCarta(Carta carta) {
        cartaRepository.delete(carta);
    }
}
