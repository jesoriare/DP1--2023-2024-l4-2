package org.springframework.samples.petclinic.estadisticas;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.Jugador.Estadisticas;
import org.springframework.samples.petclinic.Jugador.EstadisticasRepository;
import org.springframework.samples.petclinic.Jugador.EstadisticasService;

@ExtendWith(MockitoExtension.class)
public class EstadisticasServiceTests {
    
    @Mock
    private EstadisticasRepository estadisticasRepository;

    private Estadisticas estadisticas;

    @BeforeEach
    public void setUp() {
        estadisticas = new Estadisticas();
        estadisticas.setId(1);
    }

    @Test
    public void shouldFindEstadisticasById()  {
        when(estadisticasRepository.findById(1)).thenReturn(Optional.of(estadisticas));
        EstadisticasService es = new EstadisticasService(estadisticasRepository);
        Estadisticas estadisticasEncontradas = es.getEstadisticas(1);
        assertNotNull(estadisticasEncontradas);
        assertEquals(1, estadisticasEncontradas.getId());
        verify(estadisticasRepository, times(1)).findById(1);
    }

    @Test
    public void shoulNotFindEstadisticasById() {
        when(estadisticasRepository.findById(0)).thenReturn(Optional.empty());
        EstadisticasService es = new EstadisticasService(estadisticasRepository);
        Estadisticas estadisticasEncontradas = es.getEstadisticas(0);
        assertNull(estadisticasEncontradas);
        verify(estadisticasRepository, times(1)).findById(0);
    }
}
