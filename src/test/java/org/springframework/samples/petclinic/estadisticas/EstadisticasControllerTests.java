package org.springframework.samples.petclinic.estadisticas;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.Jugador.Estadisticas;
import org.springframework.samples.petclinic.Jugador.EstadisticasController;
import org.springframework.samples.petclinic.Jugador.EstadisticasService;
import org.springframework.samples.petclinic.Jugador.Jugador;
import org.springframework.samples.petclinic.Jugador.JugadorService;
import org.springframework.samples.petclinic.Partida.Partida;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;

@WebMvcTest(value = EstadisticasController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class EstadisticasControllerTests {
    private static final String BASE_URL = "/api/v1/estadisticas";

    @MockBean 
    private EstadisticasService estadisticasService;
    @MockBean 
    private JugadorService jugadorService;
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
	private ObjectMapper objectMapper;

    @Test
    @WithMockUser
     public void testGetEstadisticasByUsername() throws Exception{
        Estadisticas estadisticas = new Estadisticas();
        estadisticas.setId(1);
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("jugador1");
        Jugador jugador1 = new Jugador();
        jugador1.setUser(user1);
        jugador1.setEstadisticas(estadisticas);
        jugador1.setId(1);

        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador1);
        when(estadisticasService.getEstadisticas(1)).thenReturn(estadisticas);
        mockMvc.perform(get(BASE_URL+"/{username}","jugador1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
       
    }

    @Test 
    @WithMockUser
    public void testNotGetEstadisticasByUsername() throws Exception{
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(null);
        when(estadisticasService.getEstadisticas(1)).thenReturn(null);
        mockMvc.perform(get(BASE_URL+"/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }
}
