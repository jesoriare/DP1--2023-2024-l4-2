package org.springframework.samples.petclinic.Partida;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.Carta.Carta;
import org.springframework.samples.petclinic.Carta.CartaService;
import org.springframework.samples.petclinic.Carta.Mazo;
import org.springframework.samples.petclinic.Carta.TipoCarta;
import org.springframework.samples.petclinic.Dado.DadoService;
import org.springframework.samples.petclinic.Jugador.EstadisticasService;
import org.springframework.samples.petclinic.Jugador.Jugador;
import org.springframework.samples.petclinic.Jugador.JugadorService;
import org.springframework.samples.petclinic.auth.payload.response.MessageResponse;
import org.springframework.samples.petclinic.chat.MensajeService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.samples.petclinic.util.RestPreconditions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/partidas")
@SecurityRequirement(name = "bearerAuth")
public class PartidaController {
    
    private final PartidaService partidaService;
    private final JugadorService jugadorService;
    private final UserService userService;
    private final CartaService cartaService;
    private final DadoService dadoService;
    private final EstadisticasService estadisticasService;
    private final MensajeService mensajeService;

    @Autowired
    public PartidaController(PartidaService partidaService,JugadorService jugadorService,UserService userService,
    CartaService cartaService,EstadisticasService estadisticasService,DadoService dadoService, MensajeService mensajeService){
        this.partidaService=partidaService;
        this.jugadorService=jugadorService;
        this.userService=userService;
        this.cartaService=cartaService;
        this.estadisticasService=estadisticasService;
        this.dadoService=dadoService;
        this.mensajeService=mensajeService;
        
    }

    @GetMapping
	public ResponseEntity<List<Partida>> getPartidas() {
		return new ResponseEntity<>(partidaService.getPartidas(), HttpStatus.OK);
	}
    @GetMapping("/{partidaId}")
    public ResponseEntity<Partida> getPartidaById(@PathVariable("partidaId") int partidaId){
        Partida partida=partidaService.getPartidaById(partidaId);
        if ( partida== null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        return new ResponseEntity<>(partida,HttpStatus.OK);
    }
    @GetMapping("/jugador/{userId}")
    public ResponseEntity<Partida> getPartidaByUser(@PathVariable("userId") int userId){
        Partida partida=partidaService.getPartidaByUser(userId);
        if ( partida== null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(partida,HttpStatus.OK);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Partida> crearPartida(){
        Partida partida=new Partida();
        partidaService.guardarPartida(partida);
        return new ResponseEntity<>(partida,HttpStatus.CREATED);
    }
    @PutMapping("/{partidaId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Partida> editarPartida(@PathVariable("partidaId") int partidaId, @RequestBody @Valid Partida partidaActualizada) {
            Partida partidaExistente = partidaService.getPartidaById(partidaId);

            if (partidaExistente == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if(!partidaActualizada.getJugadores().isEmpty()){
                partidaExistente.setJugadores(partidaActualizada.getJugadores());
            }
            partidaExistente.setEstado(partidaActualizada.getEstado());
            partidaService.guardarPartida(partidaExistente);

            return new ResponseEntity<>(partidaExistente, HttpStatus.OK);

    }
    @PutMapping("/quitarcarta/{username}/{mandarMazo}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Partida> quitarCarta(@PathVariable("username") String username, 
    @PathVariable("mandarMazo") boolean mandarMazo,@RequestBody @Valid Carta carta) {
        Jugador jugador=jugadorService.getJugadorByUsername(username);
        if(jugador==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        for(Partida partida:partidaService.getPartidas()){
            if(partida.getJugadores().contains(jugador)){
                for(int i=0;i<jugador.getCartas().size();i++){
                    if(jugador.getCartas().get(i).getId().equals(carta.getId())){
                        jugador.getCartas().remove(i);
                        break;
                    }
                }
                jugadorService.guardarJugador(jugador);
                if(mandarMazo){
                    partida.getMazo().add(carta);
                }else{
                    jugador.setCartaUsada(carta);
                    if(partida.getJugadores().stream().allMatch(j->j.getCartaUsada()!=null)){
                        partida.crearTurno();
                    }
                    jugadorService.guardarJugador(jugador);
                }
                partidaService.guardarPartida(partida);
                return new ResponseEntity<>(partida, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/darcarta/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Partida> darCarta(@PathVariable("username") String username
    ,@RequestBody @Valid Carta carta) {
        Jugador jugador=jugadorService.getJugadorByUsername(username);
        if(jugador==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        for(Partida partida:partidaService.getPartidas()){
            if(partida.getJugadores().contains(jugador)){
                if(jugador.getCartaUsada()!=null&&jugador.getCartaUsada().equals(carta)){
                    jugador.setCartaUsada(null);
                    jugadorService.guardarJugador(jugador);
                }else if(!carta.getTipo().equals(TipoCarta.MIRADA_FIJA)){
                    for(int i=0;i<partida.getMazo().size();i++){
                    if(partida.getMazo().get(i).getId().equals(carta.getId())){
                        partida.getMazo().remove(i);
                        partidaService.guardarPartida(partida);
                        break;
                    }
                    }
                }else{
                    jugador.setMiradaFija(null);
                    jugadorService.guardarJugador(jugador);
                }
                jugador.getCartas().add(carta);            
                partidaService.guardarPartida(partida);
                return new ResponseEntity<>(partida, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping({"/{partidaId}"})
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<MessageResponse> delete(@PathVariable("partidaId") int partidaId) {
		Partida partida = RestPreconditions.checkNotNull(partidaService.getPartidaById(partidaId), "Partida", "ID", partidaId);
        partidaService.borrarPartida(partidaId);
		return new ResponseEntity<>(new MessageResponse("Partida deleted!"), HttpStatus.OK);

	}
    @PutMapping("unirsepartida/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Partida> unirsePartida(@PathVariable("userId") int userId){
        User user= userService.findUser(userId);
        if(user==null){
             return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        
        Jugador jugador= jugadorService.getJugadorByUsername(user.getUsername());
        if(jugador==null){
            jugador=new Jugador(user);
            estadisticasService.guardarEstadisticas(jugador.getEstadisticas());
            jugadorService.guardarJugador(jugador);
        }
        List<Partida> partidas=partidaService.getPartidas();
        final Jugador j = jugador;
        Optional<Partida> optional = partidas.stream().filter(p->p.getJugadores().contains(j)).findFirst();
        if (optional.isPresent()) {
            return new ResponseEntity<>(optional.get(),HttpStatus.OK);
        }
        Partida partida=null;
        for(Partida p:partidas){
            if(p.estaActivada()&&!p.estaIniciada()&&!p.estaLlena()){
                partida=p;break;
        }}
         if(partida!=null){
            jugador.setSalud(2);
            jugador.setBalas(2);
            jugador.setPrecision(2);
            jugador.setAccionOponente(null);
            jugador.setAccionOponenteAnterior(null);
            jugador.setCartaUsada(null);
            partida.getJugadores().add(jugador);
            if(partida.getJugadores().size()>=2){
                List<Carta> cartas=Mazo.get(cartaService);
                partida.iniciar(cartas);
            }
            partidaService.guardarPartida(partida);
            return new ResponseEntity<>(partida,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("salirpartida/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Partida> salirPartida(@PathVariable("userId") int userId){
        User user= userService.findUser(userId);
        if(user==null){
            // crear excepcion para usuario no encontrado
             return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        
        Jugador jugador= jugadorService.getJugadorByUsername(user.getUsername());
        if(jugador==null){
            // crear excepcion para jugador no encontrado
             return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        List<Partida> partidas=partidaService.getPartidas();
        final Jugador j = jugador;
        Optional<Partida> optional = partidas.stream().filter(p->p.getJugadores().contains(j)).findFirst();
        if (optional.isPresent()) {
            jugador.setCartas(new ArrayList<>());
            Partida partida=optional.get();
            //aumentar partidas jugadas
            if(!partida.getEstado().equals(EstadoPartida.ESPERANDO)){
                jugador.getEstadisticas().setPartidasJugadas(jugador.getEstadisticas().getPartidasJugadas()+1);
                Jugador ganador=partida.ganador();
                //aumentar partidas ganadas
                if(ganador!=null&&ganador.getUser().getUsername().equals(jugador.getUser().getUsername())){
                    jugador.getEstadisticas().setPartidasGanadas(jugador.getEstadisticas().getPartidasGanadas()+1);
                }
            }
            partida.getJugadores().remove(jugador);
            if(partida.getJugadores().size()==0)partida.setEstado(EstadoPartida.ESPERANDO);
            estadisticasService.guardarEstadisticas(jugador.getEstadisticas());
            jugadorService.guardarJugador(jugador);
            partidaService.guardarPartida(partida);
            return new ResponseEntity<>(partida,HttpStatus.OK);
        }else{
             return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/tirardado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Partida> tirarDado(@PathVariable("id") int partidaId) {
        Partida partida=partidaService.getPartidaById(partidaId);
        if(partida==null)return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        Random r=new Random();
        int numDado= r.nextInt(1,7);
        partida.getDado().setNumero(numDado);
        partida.getDado().setTirando(true);
        dadoService.guardarDado(partida.getDado());
        partidaService.guardarPartida(partida);
        return new ResponseEntity<>(partida,HttpStatus.OK);
    }
    @PutMapping("/parardado/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Partida> pararDado(@PathVariable("id") int partidaId) {
        Partida partida=partidaService.getPartidaById(partidaId);
        partida.getDado().setTirando(false);
        dadoService.guardarDado(partida.getDado());
        partidaService.guardarPartida(partida);
        return new ResponseEntity<>(partida,HttpStatus.OK);
    }

    @PutMapping("/usarcarta/{cartaId}/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Partida> usarCarta(@PathVariable("cartaId") int cartaId,
    @PathVariable("username") String username) {
        Carta carta=cartaService.getCartaById(cartaId);
        if(carta==null)return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        List<Partida> partidas=partidaService.getPartidas();
        final Jugador j = jugadorService.getJugadorByUsername(username);
        if(j==null) return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        Optional<Partida> optional = partidas.stream().filter(p->p.getJugadores().contains(j)).findFirst();
        if (optional.isPresent()) {
            Partida partida=optional.get();
            partida.usarCarta(carta,j,jugadorService);
            partidaService.guardarPartida(partida);
            return new ResponseEntity<>(partida,HttpStatus.OK);
        }else{
         return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/escribirchat/{mensaje}/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Partida> escribirEnElChat(@PathVariable("mensaje") String mensaje,
    @PathVariable("username") String username) {
        List<Partida> partidas=partidaService.getPartidas();
        final Jugador j = jugadorService.getJugadorByUsername(username);
        if(j==null)  return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        Optional<Partida> optional = partidas.stream().filter(p->p.getJugadores().contains(j)).findFirst();
        if (optional.isPresent()) {
            Partida partida=optional.get();
            partida.escribirEnElChat(mensaje,username,mensajeService);
            partidaService.guardarPartida(partida);
            return new ResponseEntity<>(partida,HttpStatus.OK);
        }else return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
}
