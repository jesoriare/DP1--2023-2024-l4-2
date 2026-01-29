package org.springframework.samples.petclinic.partida;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.Carta.Carta;
import org.springframework.samples.petclinic.Carta.CartaService;
import org.springframework.samples.petclinic.Carta.TipoCarta;
import org.springframework.samples.petclinic.Dado.Dado;
import org.springframework.samples.petclinic.Dado.DadoService;
import org.springframework.samples.petclinic.Jugador.EstadisticasService;
import org.springframework.samples.petclinic.Jugador.Jugador;
import org.springframework.samples.petclinic.Jugador.JugadorService;
import org.springframework.samples.petclinic.Partida.EstadoPartida;
import org.springframework.samples.petclinic.Partida.Partida;
import org.springframework.samples.petclinic.Partida.PartidaController;
import org.springframework.samples.petclinic.Partida.PartidaService;
import org.springframework.samples.petclinic.chat.MensajeService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(value = PartidaController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class))
public class PartidaControllerTest {

    private static final String BASE_URL = "/api/v1/partidas";

    @MockBean
    private PartidaService partidaService;
    @MockBean 
    private JugadorService jugadorService;
    @MockBean
    private UserService userService;
    @MockBean
    private CartaService cartaService;
    @MockBean 
    private EstadisticasService estadisticasService;
    @MockBean
    private DadoService dadoService;
    @MockBean
    private MensajeService mensajeService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
	private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void testGetPartidas() throws Exception {
        Partida partida1 = new Partida();
        partida1.setId(1);

        Partida partida2 = new Partida();
        partida2.setId(2);

        List<Partida> partidas = new ArrayList<>();
        partidas.add(partida1);
        partidas.add(partida2);

        when(partidaService.getPartidas()).thenReturn(partidas);

        mockMvc.perform(get(BASE_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }
    @Test 
    @WithMockUser
    public void testGetPartidaById() throws Exception{
        Partida partida1 = new Partida();
        partida1.setId(1);
        when(partidaService.getPartidaById(1)).thenReturn(partida1);

        mockMvc.perform(get(BASE_URL+"/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
    }

    @Test 
    @WithMockUser
    public void testNotGetPartidaById() throws Exception{
        when(partidaService.getPartidaById(1)).thenReturn(null);

        mockMvc.perform(get(BASE_URL+"/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }
  @Test 
    @WithMockUser
    public void testGetPartidaByUserId() throws Exception{
        Partida partida1 = new Partida();
        partida1.setId(1);
        User user1 = new User();
        user1.setId(1);
        Jugador jugador1 = new Jugador();
        jugador1.setUser(user1);
        List<Jugador> jugadores = List.of(jugador1);
        partida1.setJugadores(jugadores);

        when(partidaService.getPartidaByUser(1)).thenReturn(partida1);

        mockMvc.perform(get(BASE_URL+"/jugador/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.jugadores[*].user.id").value(1));
       
    }

    @Test 
    @WithMockUser
    public void testNotGetPartidaByUser() throws Exception{
        when(partidaService.getPartidaByUser(1)).thenReturn(null);

        mockMvc.perform(get(BASE_URL+"/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test 
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testCrearPartida() throws Exception{
        Partida partida1 = new Partida();
        mockMvc.perform(post(BASE_URL)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());
    }
    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testEditarPartidaSinJugadores() throws Exception {
        Partida partida1 = new Partida();
        partida1.setId(1);
        when(partidaService.getPartidaById(1)).thenReturn(partida1);
        partida1.setEstado(EstadoPartida.DESACTIVADA);
        mockMvc.perform(put(BASE_URL + "/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"estado\": \"DESACTIVADA\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

    }
     @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testNotEditarPartida() throws Exception {
        when(partidaService.getPartidaById(0)).thenReturn(null);
        mockMvc.perform(put(BASE_URL + "/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"estado\": \"JUGANDO\"}"))
                .andExpect(status().isNotFound());
            
    }
    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    public void testEditarPartidaConJugadores() throws Exception {
        Partida partida1 = new Partida();
        partida1.setId(1);
        when(partidaService.getPartidaById(1)).thenReturn(partida1);
        User user1 = new User();
        User user2 = new User();
        user1.setId(1);
        user2.setId(2);
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();
        jugador1.setUser(user1);
        jugador2.setUser(user2);
        List<Jugador> jugadores = List.of(jugador1,jugador2);
        partida1.setEstado(EstadoPartida.JUGANDO);
        partida1.setJugadores(jugadores);
        mockMvc.perform(put(BASE_URL + "/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"jugadores\": [{\"user\": {\"id\": 1}}, {\"user\": {\"id\": 2}}], \"estado\": \"JUGANDO\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.jugadores[0].user.id").value(1))
                .andExpect(jsonPath("$.jugadores[1].user.id").value(2))
                .andExpect(jsonPath("$.estado").value("JUGANDO"));

    }
    @Test
    @WithMockUser
    public void testQuitarCartaSinJugador() throws Exception {
        Carta carta = new Carta();
        when(jugadorService.getJugadorByUsername("usuarioNoExistente")).thenReturn(null);
        mockMvc.perform(put(BASE_URL + "/quitarcarta/{username}/{mandarMazo}", "usuarioNoExistente", false)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(carta)))
        .andExpect(status().isNotFound());
    }
    @Test
    @WithMockUser
    public void testQuitarCartaConJugadorEnPartidaMandarMazoTrue() throws Exception {
        Carta carta1 = new Carta();
        carta1.setId(1);
        Carta carta2 = new Carta();
        carta2.setId(2);
        Partida partida = new Partida();
        partida.setId(1);
        User user = new User();
        user.setUsername("jugador1");
        Jugador jugador = new Jugador();
        jugador.setUser(user);
        jugador.setCartas(List.of(carta1,carta2));
        partida.setJugadores(List.of(jugador));
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador);
        when(partidaService.getPartidas()).thenReturn(List.of(partida));
        List<Carta> nuevasCartas = new ArrayList<>(jugador.getCartas());
        nuevasCartas.remove(carta1);
        jugador.setCartas(nuevasCartas);
        List<Carta> nuevoMazo= new ArrayList<>();
        partida.setMazo(nuevoMazo);
        mockMvc.perform(put(BASE_URL + "/quitarcarta/{username}/{mandarMazo}", "jugador1", true)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(carta1)))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.mazo",hasSize(1)))
        .andExpect(jsonPath("$.jugadores[0].cartas",hasSize(1)));
    
    }

    @Test
    @WithMockUser
    public void testQuitarCartaConJugadorEnPartidaMandarMazoFalse() throws Exception {
        Carta carta1 = new Carta();
        carta1.setId(1);
        Carta carta2 = new Carta();
        carta2.setId(2);
        Partida partida = new Partida();
        partida.setId(1);
        User user = new User();
        user.setUsername("jugador1");
        Jugador jugador = new Jugador();
        jugador.setUser(user);
        jugador.setCartas(List.of(carta1,carta2));
        partida.setJugadores(List.of(jugador));
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador);
        when(partidaService.getPartidas()).thenReturn(List.of(partida));
        List<Carta> nuevasCartas = new ArrayList<>(jugador.getCartas());
        nuevasCartas.remove(carta1);
        jugador.setCartas(nuevasCartas);
        List<Carta> nuevoMazo= new ArrayList<>();
        partida.setMazo(nuevoMazo);
    
        mockMvc.perform(put(BASE_URL + "/quitarcarta/{username}/{mandarMazo}", "jugador1", false)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(carta1)))
        .andExpect(status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.mazo",hasSize(0)))
        .andExpect(jsonPath("$.jugadores[0].cartaUsada.id").value(1));
    
    }
  
    @Test
    @WithMockUser
    public void testQuitarCartaSinJugadorEnPartida() throws Exception {
        Carta carta = new Carta();
        Partida partida = new Partida();
        partida.setId(1);
        User user = new User();
        user.setUsername("jugador1");
        Jugador jugador = new Jugador();
        jugador.setUser(user);
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador);
        mockMvc.perform(put(BASE_URL + "/quitarcarta/{username}/{mandarMazo}", "jugador1", false)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(carta)))
        .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testDarCartaSinJugador() throws Exception {
        Carta carta = new Carta();
        when(jugadorService.getJugadorByUsername("usuarioNoExistente")).thenReturn(null);
        mockMvc.perform(put(BASE_URL + "/darcarta/{username}", "usuarioNoExistente", false)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(carta)))
        .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testDarCarta() throws Exception {
        Carta carta1 = new Carta();
        carta1.setId(1);
        carta1.setTipo(TipoCarta.CINTURON_DE_ARMAS);
        Carta carta2 = new Carta();
        carta2.setId(2);
        Partida partida = new Partida();
        partida.setId(1);
        User user = new User();
        user.setUsername("jugador1");
        Jugador jugador = new Jugador();
        jugador.setUser(user);
        partida.setJugadores(List.of(jugador));
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador);
        when(partidaService.getPartidas()).thenReturn(List.of(partida));
        List<Carta> nuevasCartas = new ArrayList<>(jugador.getCartas());
        nuevasCartas.remove(carta1);
        jugador.setCartas(nuevasCartas);
        List<Carta> nuevoMazo= new ArrayList<>(nuevasCartas);
        partida.setMazo(nuevoMazo);
        mockMvc.perform(put(BASE_URL + "/darcarta/{username}", "jugador1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(carta1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.jugadores[0].cartas",hasSize(1)));
    }

     @Test
    @WithMockUser
    public void testDarCartaMiradaFija() throws Exception {
        Carta carta1 = new Carta();
        carta1.setId(1);
        carta1.setTipo(TipoCarta.MIRADA_FIJA);
        Carta carta2 = new Carta();
        carta2.setId(2);
        Partida partida = new Partida();
        partida.setId(1);
        User user = new User();
        user.setUsername("jugador1");
        Jugador jugador = new Jugador();
        jugador.setUser(user);
        partida.setJugadores(List.of(jugador));
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador);
        when(partidaService.getPartidas()).thenReturn(List.of(partida));
        List<Carta> nuevasCartas = new ArrayList<>(jugador.getCartas());
        nuevasCartas.remove(carta1);
        jugador.setCartas(nuevasCartas);
        List<Carta> nuevoMazo= new ArrayList<>(nuevasCartas);
        partida.setMazo(nuevoMazo);
        mockMvc.perform(put(BASE_URL + "/darcarta/{username}", "jugador1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(carta1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.jugadores[0].cartas",hasSize(1)));
    }
    @Test
    @WithMockUser
    public void testDarCartaSinJugadorEnPartida() throws Exception {
        Carta carta = new Carta();
        Partida partida = new Partida();
        partida.setId(1);
        User user = new User();
        user.setUsername("jugador1");
        Jugador jugador = new Jugador();
        jugador.setUser(user);
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador);
        mockMvc.perform(put(BASE_URL + "/darcarta/{username}", "jugador1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(carta)))
        .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testBorrarPartidaNotNull() throws Exception{
        mockMvc.perform(delete(BASE_URL+"/1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testBorrarPartida() throws Exception{
        Partida partida = new Partida();
        partida.setId(1);
        mockMvc.perform(delete(BASE_URL+"/1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }    

    @Test
    @WithMockUser
    public void testUnirsePartidaUserNull() throws Exception{
        mockMvc.perform(put(BASE_URL+"/unirsepartida/{userId}",1)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testUnirsePartida() throws Exception{
        Partida partida1 = new Partida();
        partida1.setId(1);
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("jugador1");
        Jugador jugador1 = new Jugador();
        jugador1.setUser(user1);
        when(userService.findUser(1)).thenReturn(user1);
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador1);
        when(partidaService.getPartidas()).thenReturn(List.of(partida1));
        mockMvc.perform(put(BASE_URL+"/unirsepartida/{userId}",1)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.jugadores",hasSize(1)));
    }

     @Test
    @WithMockUser
    public void testSalirPartidaUserNull() throws Exception{
        mockMvc.perform(put(BASE_URL+"/salirpartida/{userId}",1)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

     @Test
    @WithMockUser
    public void testSalirPartida() throws Exception{
        Partida partida1 = new Partida();
        partida1.setId(1);
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("jugador1");
        Jugador jugador1 = new Jugador();
        jugador1.setUser(user1);
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        partida1.setJugadores(jugadores);
        when(userService.findUser(1)).thenReturn(user1);
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador1);
        when(partidaService.getPartidas()).thenReturn(List.of(partida1));
        mockMvc.perform(put(BASE_URL+"/salirpartida/{userId}",1)
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.jugadores",hasSize(0)));
    }

      @Test
    @WithMockUser
    public void testTirarDado() throws Exception{
        Partida partida1 = new Partida();
        partida1.setId(1);
        Dado dado1 = new Dado();
        partida1.setDado(dado1);
        when(partidaService.getPartidaById(1)).thenReturn(partida1);
        mockMvc.perform(put(BASE_URL+"/tirardado")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(partida1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dado.numero", greaterThanOrEqualTo(1)))
        .andExpect(jsonPath("$.dado.numero", lessThanOrEqualTo(6)));
    }

    @Test
    @WithMockUser
    public void testPararDado() throws Exception {
        Partida partida1 = new Partida();
        Dado dado1 = new Dado();
        dado1.setTirando(true);
        partida1.setDado(dado1);
        mockMvc.perform(put(BASE_URL + "/parardado")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(partida1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.dado.tirando").value(false));
    }

    @Test
    @WithMockUser
    public void testUsarCartaUserNull() throws Exception{
        Carta carta = new Carta();
        Partida partida = new Partida();
        partida.setId(1);
        mockMvc.perform(put(BASE_URL+"/usarcarta/{cartaId}/{username}",1,"jugador1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    public void testUsarCarta() throws Exception{
        Carta carta1 = new Carta();
        carta1.setId(1);
        carta1.setImagen("frontend/public/ImagenesCartas/Carta1.jpg");
        Partida partida1 = new Partida();
        partida1.setId(1);
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("jugador1");
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("jugador2");
        Jugador jugador1 = new Jugador();
        jugador1.setUser(user1);
        Jugador jugador2 = new Jugador();
        jugador2.setUser(user2);
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        partida1.setJugadores(jugadores);
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador1);
        when(partidaService.getPartidas()).thenReturn(List.of(partida1));
        when(cartaService.getCartaById(1)).thenReturn(carta1);
        mockMvc.perform(put(BASE_URL+"/usarcarta/{cartaId}/{username}",1,"jugador1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.jugadores[0].precision").isNumber())
        .andExpect(jsonPath("$.jugadores[0].balas").isNumber())
        .andExpect(jsonPath("$.jugadores[1].precision").isNumber())
        .andExpect(jsonPath("$.jugadores[1].balas").isNumber());
        }

    @Test
    @WithMockUser
    public void testEscribirEnElChatUserNull() throws Exception{
        Partida partida = new Partida();
        partida.setId(1);
        mockMvc.perform(put(BASE_URL+"/escribirchat/{mensaje}/{username}","hola","jugador1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    public void testEscribirEnElChat() throws Exception{
        Partida partida1 = new Partida();
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("jugador1");
        User user2 = new User();
        user2.setId(2);
        user2.setUsername("jugador2");
        Jugador jugador1 = new Jugador();
        jugador1.setUser(user1);
        Jugador jugador2 = new Jugador();
        jugador2.setUser(user2);
        List<Jugador> jugadores = new ArrayList<>();
        jugadores.add(jugador1);
        jugadores.add(jugador2);
        partida1.setJugadores(jugadores);
        partida1.setId(1);
        when(partidaService.getPartidas()).thenReturn(List.of(partida1));
        when(jugadorService.getJugadorByUsername("jugador1")).thenReturn(jugador1);
        mockMvc.perform(put(BASE_URL+"/escribirchat/{mensaje}/{username}","hola","jugador1")
        .with(csrf())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.chat",hasSize(1)))
        .andExpect(jsonPath("$.chat.[0].textoMensaje").value("hola"));
    }
}
