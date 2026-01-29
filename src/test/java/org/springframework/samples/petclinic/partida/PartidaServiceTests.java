package org.springframework.samples.petclinic.partida;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.Partida.Partida;
import org.springframework.samples.petclinic.Partida.PartidaRepository;
import org.springframework.samples.petclinic.Partida.PartidaService;
import org.springframework.samples.petclinic.user.User;

import org.springframework.samples.petclinic.Jugador.Jugador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PartidaServiceTests {

    @Mock
    private PartidaRepository partidaRepository;

    private Partida partida;

    @BeforeEach
    public void setUp() {
        partida = new Partida();
        partida.setId(1);
        
    }

    @Test
    public void shouldFindPartidaById()  {
        when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));
        PartidaService ps = new PartidaService(partidaRepository);
        Partida partidaEncontrada = ps.getPartidaById(1);
        assertNotNull(partidaEncontrada);
        assertEquals(1, partidaEncontrada.getId());
        verify(partidaRepository, times(1)).findById(1);
    }

    @Test
    public void shoulNotFindPartidaById() {
        when(partidaRepository.findById(0)).thenReturn(Optional.empty());
        PartidaService ps = new PartidaService(partidaRepository);
        Partida partidaEncontrada = ps.getPartidaById(0);
        assertNull(partidaEncontrada);
        verify(partidaRepository, times(1)).findById(0);
    }
   
    @Test
    public void shouldFindAllPartidas(){
        List<Partida> partidas = new ArrayList<>();
        partidas.add(partida);
        when(partidaRepository.findAll()).thenReturn(partidas);
        PartidaService ps = new PartidaService(partidaRepository);
        List<Partida> partidasEncontradas = ps.getPartidas();
        assertEquals(1, partidasEncontradas.size());
        verify(partidaRepository, times(1)).findAll();
    }

     @Test
    public void shouldDeletePartidaById() {
        PartidaService ps = new PartidaService(partidaRepository);
        when(partidaRepository.findById(1)).thenReturn(Optional.of(partida));
        ps.borrarPartida(1);
        verify(partidaRepository, times(1)).delete(any(Partida.class));
        when(partidaRepository.findById(1)).thenReturn(Optional.empty());
        Partida partidaEncontrada = ps.getPartidaById(1);
        assertNull(partidaEncontrada);        
    }

    @Test
    public void shouldGetPartidaByUser(){
        User user = new User();
        user.setId(1);
        Jugador jugador = new Jugador();
        jugador.setUser(user);
        partida.setJugadores(List.of(jugador));
        List<Partida> partidas = new ArrayList<>();
        partidas.add(partida);
        when(partidaRepository.findAll()).thenReturn(partidas);
        PartidaService ps = new PartidaService(partidaRepository);
        Partida partidaEncontrada = ps.getPartidaByUser(1);
        assertEquals(1, partidaEncontrada.getJugadores().stream().mapToInt(j->j.getUser().getId()).filter(i->i==1).findFirst().getAsInt());
        verify(partidaRepository, times(1)).findAll();
    }

}




