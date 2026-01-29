package org.springframework.samples.petclinic.carta;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.Carta.Carta;
import org.springframework.samples.petclinic.Carta.CartaRepository;
import org.springframework.samples.petclinic.Carta.CartaService;


@ExtendWith(MockitoExtension.class)
public class CartaServiceTests {
    @Mock
    private CartaRepository cartaRepository;
    
    private Carta carta;

    @BeforeEach
    public void setUp() {
        carta = new Carta();
        carta.setId(1);
        
    }

    @Test
    public void shouldFindCartaById()  {
        when(cartaRepository.findById(1)).thenReturn(Optional.of(carta));
        CartaService cs = new CartaService(cartaRepository);
        Carta cartaEncontrada = cs.getCartaById(1);
        assertNotNull(cartaEncontrada);
        assertEquals(1, cartaEncontrada.getId());
        verify(cartaRepository, times(1)).findById(1);
    }

    @Test
    public void shoulNotFindCartaById() {
        when(cartaRepository.findById(0)).thenReturn(Optional.empty());
        CartaService cs = new CartaService(cartaRepository);
        Carta cartaEncontrada = cs.getCartaById(0);
        assertNull(cartaEncontrada);
        verify(cartaRepository, times(1)).findById(0);
    }
   @Test
    public void shouldFindAllCartas(){
        List<Carta> cartas = new ArrayList<>();
        cartas.add(carta);
        when(cartaRepository.findAll()).thenReturn(cartas);
        CartaService cs = new CartaService(cartaRepository);
        List<Carta> cartasEncontradas = cs.getCartas();
        assertEquals(1, cartasEncontradas.size());
        verify(cartaRepository, times(1)).findAll();
    }

    @Test
    public void shouldDeleteCartaById() {
        CartaService cs = new CartaService(cartaRepository);
        when(cartaRepository.findById(1)).thenReturn(Optional.of(carta));
        cs.borrarCarta(carta);
        verify(cartaRepository, times(1)).delete(any(Carta.class));
        when(cartaRepository.findById(1)).thenReturn(Optional.empty());
        Carta cartaEncontrada = cs.getCartaById(1);
        assertNull(cartaEncontrada);        
    }
}
