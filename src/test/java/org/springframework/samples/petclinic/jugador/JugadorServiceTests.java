package org.springframework.samples.petclinic.jugador;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.Jugador.Jugador;
import org.springframework.samples.petclinic.Jugador.JugadorRepository;
import org.springframework.samples.petclinic.Jugador.JugadorService;
import org.springframework.samples.petclinic.user.User;

@ExtendWith(MockitoExtension.class)
public class JugadorServiceTests {
    @Mock
    private JugadorRepository jugadorRepository;

    private Jugador jugador;

    @BeforeEach
    public void setUp() {
        jugador = new Jugador();
        jugador.setId(1);
        
    }

    @Test
    public void shouldFindJugadorById()  {
        when(jugadorRepository.findById(1)).thenReturn(Optional.of(jugador));
        JugadorService js = new JugadorService(jugadorRepository);
        Jugador jugadorEncontrado = js.getJugadorById(1);
        assertNotNull(jugadorEncontrado);
        assertEquals(1, jugadorEncontrado.getId());
        verify(jugadorRepository, times(1)).findById(1);
    }

    @Test
    public void shoulNotFindPartidaById() {
        when(jugadorRepository.findById(0)).thenReturn(Optional.empty());
        JugadorService js = new JugadorService(jugadorRepository);
        Jugador jugadorEncontrado = js.getJugadorById(0);
        assertNull(jugadorEncontrado);
        verify(jugadorRepository, times(1)).findById(0);
    }
   
    @Test
    public void shouldGetJugadorByUsername(){
        User user = new User();
        user.setId(1);
        user.setUsername("jugador1");
        Jugador jugador = new Jugador();
        jugador.setUser(user);
        jugador.setId(1);
        when(jugadorRepository.getJugadorByUsername("jugador1")).thenReturn(Optional.of(jugador));
        JugadorService js = new JugadorService(jugadorRepository);
        Jugador jugadorEncontrado = js.getJugadorByUsername("jugador1");
        assertEquals(1,jugadorEncontrado.getId());
        verify(jugadorRepository, times(1)).getJugadorByUsername("jugador1");
    }

    @Test
    public void shouldNotGetJugadorByUsername(){
        when(jugadorRepository.getJugadorByUsername("jugador1")).thenReturn(Optional.empty());
        JugadorService js = new JugadorService(jugadorRepository);
        Jugador jugadorEncontrado = js.getJugadorByUsername("jugador1");
        assertEquals(null,jugadorEncontrado);
        verify(jugadorRepository, times(1)).getJugadorByUsername("jugador1");
    }

}
