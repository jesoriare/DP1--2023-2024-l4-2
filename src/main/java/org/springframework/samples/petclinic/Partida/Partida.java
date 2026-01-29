package org.springframework.samples.petclinic.Partida;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import java.util.Map;
import org.springframework.samples.petclinic.Carta.Carta;
import org.springframework.samples.petclinic.Carta.Mazo;
import org.springframework.samples.petclinic.Carta.TipoCarta;
import org.springframework.samples.petclinic.Dado.Dado;
import org.springframework.samples.petclinic.Jugador.Jugador;
import org.springframework.samples.petclinic.Jugador.JugadorService;
import org.springframework.samples.petclinic.chat.Mensaje;
import org.springframework.samples.petclinic.chat.MensajeService;
import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Table(name="partidas")
@Entity
public class Partida extends BaseEntity{
    
    @OneToMany
    private List<Jugador> jugadores=new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private EstadoPartida estado=EstadoPartida.ESPERANDO;

   @OneToMany(cascade = CascadeType.ALL)
    private List<Carta> mazo;

   @OneToOne(cascade = CascadeType.ALL)
   private Dado dado=new Dado();

    @OneToMany(cascade = CascadeType.ALL)
   private List<Mensaje> chat=new ArrayList<>();

    private String jugadorTurno="";

    private boolean turnoActivo=false;

    public boolean estaActivada(){
        return !this.getEstado().equals(EstadoPartida.DESACTIVADA);
    }
    public boolean estaIniciada(){
        return !this.getEstado().equals(EstadoPartida.DESACTIVADA)&&!this.getEstado().equals(EstadoPartida.ESPERANDO);
    }
    public boolean estaLlena(){
        return this.getJugadores().size()>=2;
    }
    public Jugador ganador(){
        if(jugadores.size()<2)return null;
        Jugador j1=jugadores.get(0);
        Jugador j2=jugadores.get(1);
        if(j1.getSalud()==0)return j2;
        else if(j1.getSalud()==0)return j1;
        return null;
    }
    public void iniciar(List<Carta>cartas) {
        estado=EstadoPartida.JUGANDO;
        chat.clear();
        mazo=cartas;
        repartirCartas();
    }
    private void repartirCartas() {
        Random r=new Random();
        List<Carta> miradaFijas=new ArrayList<>();
        mazo.stream().filter(c->c.getTipo().equals(TipoCarta.MIRADA_FIJA)).forEach(c->miradaFijas.add(c));
        List<Carta> newMazo=mazo.stream().filter(c->!c.getTipo().equals(TipoCarta.MIRADA_FIJA)).toList();
        mazo.clear();
        for(Carta c: newMazo)mazo.add(c);
        for(Jugador j: jugadores){
            j.setCartas(new ArrayList<>());
            Carta miradaFija=miradaFijas.get(r.nextInt(miradaFijas.size()));
            j.setMiradaFija(miradaFija);
            miradaFijas.remove(miradaFija);
            for(int i=0;i<8;i++){
            Carta carta=null;
            carta=mazo.get(r.nextInt(mazo.size()));
            //tener una de finta y disparo obligatoriamente
            if(j.getCartas().size()>=6&&!carta.getTipo().equals(TipoCarta.DISPARO)&&!carta.getTipo().equals(TipoCarta.FINTA)){
                if(!j.getCartas().stream().anyMatch(c->c.getTipo().equals(TipoCarta.DISPARO)))carta=cartaOptimaDisparoFinta(TipoCarta.DISPARO);
                else if(!j.getCartas().stream().anyMatch(c->c.getTipo().equals(TipoCarta.FINTA)))carta=cartaOptimaDisparoFinta(TipoCarta.DISPARO);
            }
            j.getCartas().add(carta);
            mazo.remove(carta);
            }
            
        }
    }
    private Carta cartaOptimaDisparoFinta(TipoCarta tipo){
        Carta carta=null;
        Random r=new Random();
        while(carta==null||!carta.getTipo().equals(tipo))carta=mazo.get(r.nextInt(mazo.size()));
        return carta;
    }
    public void usarCarta(Carta carta, Jugador jugador,JugadorService jugadorService) {
        Jugador oponente=jugadores.stream().filter(j->!j.equals(jugador)).findFirst().get();
        if(oponente==null)return;
        jugador.setCartaUsada(null);
        jugadorService.guardarJugador(jugador);
        String numero=carta.getImagen().replace("/ImagenesCartas/Carta", "");
        numero=numero.replace(".jpg", "");
        int numCarta;
        try{
           numCarta=Integer.parseInt(numero);
        }catch(Exception e){numCarta=0;}
        Map<String,BiPredicate<Jugador,Jugador>> condiciones=Mazo.getCondiciones(numCarta);
        boolean ganarPrecisionJugador=true;
        boolean recargarBalaJugador=true;
        boolean ganarPrecisionOponente=true;
        boolean recargarBalaOponente=true;
        boolean descartaYRoba=true;
        if(condiciones.containsKey("ganarPrecisionJugador")){
            if(!condiciones.get("ganarPrecisionJugador").test(jugador, oponente))ganarPrecisionJugador=false;
        }
        if(condiciones.containsKey("recargarBalaJugador")){
            if(!condiciones.get("recargarBalaJugador").test(jugador, oponente))recargarBalaJugador=false;
        }if(condiciones.containsKey("ganarPrecisionOponente")){
             if(!condiciones.get("ganarPrecisionOponente").test(jugador, oponente))ganarPrecisionOponente=false;
        }if(condiciones.containsKey("recargarBalaOponente")){
            if(!condiciones.get("recargarBalaOponente").test(jugador, oponente))recargarBalaOponente=false; 
        }if(condiciones.containsKey("descartaYRoba")){
             if(!condiciones.get("descartaYRoba").test(jugador, oponente))descartaYRoba=false; 
        }
        if(ganarPrecisionJugador){
             if(carta.isGastaPrecision()){
                if(carta.getPrecisionJugador()>=0) jugador.setPrecision(
                    getNumInRange06(jugador.getPrecision()-1));
             }
             int jugadorPrecision=getNumInRange06(jugador.getPrecision()+carta.getPrecisionJugador());
             jugador.setPrecision(jugadorPrecision);
        }
        if(recargarBalaJugador){
            if(carta.isGastaBala()){
                if(carta.getBalasJugador()>=0) jugador.setBalas(
                    getNumInRange06(jugador.getBalas()-1));
             }
             int jugadorBalas=getNumInRange06(jugador.getBalas()+carta.getBalasJugador());
             jugador.setBalas(jugadorBalas);
        }if(ganarPrecisionOponente){
            int oponentePrecision=getNumInRange06(oponente.getBalas()+carta.getBalasJugador());
             oponente.setPrecision(oponentePrecision);
        }if(recargarBalaOponente){
            int oponenteBalas=getNumInRange06(oponente.getBalas()+carta.getBalasJugador());
            oponente.setBalas(oponenteBalas);
        }
        if(carta.isGastaBala()){
            //usar el valor del dado para tomar decisiones de tiro
            int numDado=dado.getNumero();
            if(numDado<=jugador.getPrecision())oponente.setSalud(oponente.getSalud()-1);
            if(oponente.getSalud()==0)estado=EstadoPartida.FINALIZANDO;
        }
        if(descartaYRoba) {
            //dar carta random a jugador: 
            if(carta.isDescartar()) {
                Random r=new Random();
                Carta cartaDescarte=mazo.get(r.nextInt(mazo.size()));
                mazo.remove(cartaDescarte);
                mazo.add(carta);
                jugador.getCartas().add(cartaDescarte);
            }
            else jugador.getCartas().add(carta);
        }
        else jugador.getCartas().add(carta);
        //cambiar turno
        if(this.jugadores.stream().allMatch(j->j.getCartaUsada()==null)){
            jugadorTurno="";turnoActivo=false;}
        else jugadorTurno=oponente.getUser().getUsername();
        jugadorService.guardarJugador(jugador);
        jugadorService.guardarJugador(oponente);
    }
    
    private int getNumInRange06(int num){
        if(num<0)return 0;
        if(num>6)return 6;
        return num;
    }
    public void escribirEnElChat(String texto, String user,MensajeService mensajeService) {
        Mensaje mensaje=new Mensaje();
        mensaje.setEscritor(user);
        mensaje.setTextoMensaje(texto);
        chat.add(mensaje);
        mensajeService.guardarMensaje(mensaje);
    }
    public void crearTurno() {
        turnoActivo=true;
        Jugador jug1=this.getJugadores().get(0);
        Jugador jug2=this.getJugadores().get(1);
        Carta cartaJug1=jug1.getCartaUsada();
        Carta cartaJug2=jug2.getCartaUsada();
        //usar las cartas para seleccionar el turno de los jugadores
        if(cartaJug1.getTipo().equals(TipoCarta.FINTA)&&!cartaJug2.getTipo().equals(TipoCarta.FINTA)){
            jugadorTurno=jug1.getUser().getUsername();return;
        }if(cartaJug2.getTipo().equals(TipoCarta.FINTA)&&!cartaJug1.getTipo().equals(TipoCarta.FINTA)){
            jugadorTurno=jug2.getUser().getUsername();return;
        }
        if(jug1.getBalas()>jug2.getBalas()){
            jugadorTurno=jug1.getUser().getUsername();return;
        }if(jug2.getBalas()>jug1.getBalas()){
            jugadorTurno=jug2.getUser().getUsername();return;
        }
        jugadorTurno=jug1.getUser().getUsername();
    }
}