package org.springframework.samples.petclinic.Carta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import org.springframework.samples.petclinic.Jugador.Jugador;

public class Mazo {
private static List<String> numerosDescarte=List.of(
"3","11","13","18","21","29","32","33","34","35","36","37","40","41","46"
);
public static List<Carta> get(CartaService cartaService){
    List<Carta> cartas=new ArrayList<>();
    cartas.add(crearCarta("/ImagenesCartas/MiradaFija.jpg","MIRADA_FIJA",
    2,1,0,0,false,false));
    cartas.add(crearCarta("/ImagenesCartas/MiradaFija.jpg","MIRADA_FIJA",
    2,1,0,0,false,false));
    cartas.add(crearCarta("/ImagenesCartas/Carta1.jpg","PUNTERIA",
    1,-2,0,0,true,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta2.jpg", "PUNTERIA",
    2,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta3.jpg", "PUNTERIA",
    2,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta4.jpg", "PUNTERIA",
    3,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta5.jpg", "PUNTERIA",
    2,1,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta6.jpg", "PUNTERIA",
    3,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta7.jpg", "PUNTERIA",
    4,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta8.jpg", "PUNTERIA",
    4,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta9.jpg", "PUNTERIA",
    3,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta10.jpg", "FINTA",
    0,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta11.jpg", "FINTA",
    0,0,-2,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta12.jpg", "FINTA",
    0,3,0,0,true,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta13.jpg", "FINTA",
    0,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta14.jpg", "FINTA",
    1,-1,0,0,true,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta15.jpg", "FINTA",
    0,2,0,0,false,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta16.jpg", "FINTA",
    0,0,0,-2,false,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta17.jpg", "FINTA",
    3,0,0,0,false,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta18.jpg", "FINTA",
    0,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta19.jpg", "DISPARO",
    -2,-2,0,0,true,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta20.jpg", "DISPARO",
    -2,-1,2,0,true,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta21.jpg", "DISPARO",
    -3,-1,0,0,true,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta22.jpg", "DISPARO",
    -3,2,0,0,true,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta23.jpg", "DISPARO",
    -3,-1,-1,0,true,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta24.jpg", "DISPARO",
    -3,-1,0,0,true,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta25.jpg", "DISPARO",
    -4,-1,-3,0,true,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta26.jpg", "DISPARO",
    -4,-1,0,-2,true,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta27.jpg", "DISPARO",
    -5,0,0,0,true,true));
     cartas.add(crearCarta( "/ImagenesCartas/Carta28.jpg", "CINTURON_DE_ARMAS",
     1,1,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta29.jpg", "CINTURON_DE_ARMAS",
    0,0,-3,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta30.jpg", "CINTURON_DE_ARMAS",
    0,1,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta31.jpg", "CINTURON_DE_ARMAS",
    -1,2,0,0,false,true));
    cartas.add(crearCarta( "/ImagenesCartas/Carta32.jpg", "CINTURON_DE_ARMAS",
    3,0,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta33.jpg", "CINTURON_DE_ARMAS",
    0,0,0,-2,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta34.jpg", "CINTURON_DE_ARMAS",
    0,2,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta35.jpg", "CINTURON_DE_ARMAS",
    0,3,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta36.jpg", "CINTURON_DE_ARMAS",
    0,1,0,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta37.jpg", "INTIMIDACION",
    0,0,-4,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta38.jpg", "INTIMIDACION",
    0,-1,-3,0,true,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta39.jpg", "INTIMIDACION",
    0,1,0,-1,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta40.jpg", "INTIMIDACION",
    0,0,-1,-2,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta41.jpg", "INTIMIDACION",
    0,0,0,-1,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta42.jpg", "INTIMIDACION",
    0,0,-4,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta43.jpg", "INTIMIDACION",
    2,-1,-3,0,true,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta44.jpg", "INTIMIDACION",
    0,0,-1,0,false,false));
    cartas.add(crearCarta( "/ImagenesCartas/Carta45.jpg", "INTIMIDACION",
    0,0,-4,0,false,false));
    cartas.add(crearCarta("/ImagenesCartas/Carta46.jpg", "CINTURON_DE_ARMAS",
    0,0,0,-4,false,false));
    cartas.add(crearCarta("/ImagenesCartas/Carta47.jpg", "DISPARO",-3,-1,
    0,0,true,true));
    cartas.add(crearCarta("/ImagenesCartas/Carta48.jpg", "FINTA",
    -1,2,0,0,false,true));
    cartas.add(crearCarta("/ImagenesCartas/Carta49.jpg", "CINTURON_DE_ARMAS",
    0,0,0,0,false,false));
    cartas.add(crearCarta("/ImagenesCartas/Carta50.jpg", "DISPARO",-3,-2,
    0,0,false,true));

    //cuando gastarBala o gastarPrecision sea true, comprobar si 
    //la precision o balas es negativo, en ese caso añadir el valor, y si no restar 1 de cada uno
    for(Carta c: cartas){
        cartaService.guardarCarta(c);
    }
    return cartas;
    
}
private static Carta crearCarta(String imagen,String tipo,int precisionJugador,int balasJugador,
int precisionOponente,int balasOponente,boolean gastaBala,boolean gastaPrecision){
    Carta carta=new Carta();
    carta.setImagen(imagen);
    carta.setTipo(TipoCarta.valueOf(tipo));
    carta.setPrecisionJugador(precisionJugador);
    carta.setBalasJugador(balasJugador);
    carta.setPrecisionOponente(precisionOponente);
    carta.setBalasOponente(balasOponente);
    carta.setGastaBala(gastaBala);
    carta.setGastaPrecision(gastaPrecision);
    if(numerosDescarte.stream().anyMatch(n->imagen.contains("Carta"+n+".jpg")))carta.setDescartar(true);
    return carta;
}

public static Map<String,BiPredicate<Jugador,Jugador>> getCondiciones(int carta){
    Map<String,BiPredicate<Jugador,Jugador>> condiciones=new HashMap<>();
    if(carta==5){
        condiciones.put("recargarBalaJugador",(jugador,oponente)->jugador.getAccionOponenteAnterior()!=null && jugador.getAccionOponenteAnterior().equals(TipoCarta.DISPARO));
    }else if(carta==7){
        condiciones.put("ganarPrecisionJugador",(jugador,oponente)->oponente.getPrecision()>=4);
    }else if(carta==8){
        condiciones.put("ganarPrecisionJugador",(jugador,oponente)->oponente.getSalud()>jugador.getSalud());
    }else if(carta==9){
        condiciones.put("ganarPrecisionJugador",(jugador,oponente)->jugador.getAccionOponenteAnterior()!=null && jugador.getAccionOponenteAnterior().equals(TipoCarta.DISPARO));
    }else if(carta==10){
        condiciones.put("descartaYRoba",(jugador,oponente)->jugador.getAccionOponente()!=null &&!jugador.getAccionOponente().equals(TipoCarta.DISPARO));
    }else if(carta==12){
        condiciones.put("recargarBalaJugador",(jugador,oponente)->jugador.getAccionOponente()!=null && jugador.getAccionOponente().equals(TipoCarta.DISPARO));
    }else if(carta==13){
        condiciones.put("recuperarSaludJugador",(jugador,oponente)->jugador.getAccionOponente()!=null &&jugador.getAccionOponente().equals(TipoCarta.DISPARO));
    }else if(carta==15){
        condiciones.put("recargarBalaJugador",(jugador,oponente)->jugador.getAccionOponente()!=null &&jugador.getAccionOponente().equals(TipoCarta.DISPARO));
    }else if(carta==16){
        condiciones.put("recargarBalaOponente",(jugador,oponente)->jugador.getAccionOponente()!=null &&jugador.getAccionOponente().equals(TipoCarta.DISPARO));
    }else if(carta==17){
        condiciones.put("ganarPrecisionJugador",(jugador,oponente)->jugador.getAccionOponente()!=null && jugador.getAccionOponente().equals(TipoCarta.DISPARO));
    }else if(carta==20){
        //comprobar en un futuro
        condiciones.put("ganarPrecisionOponente",(jugador,oponente)->jugador.getAccionOponenteAnterior()!=null && jugador.getAccionOponenteAnterior().equals(TipoCarta.FINTA));
    }else if(carta==22){
        condiciones.put("recargarBalaJugador",(jugador,oponente)->oponente.getSalud()>jugador.getSalud());
    }else if(carta==25){
       //crear detector para acierto de un disparo: en este caso si acierta, el oponente pierde 3 de precision
    }else if(carta==26){
       //crear detector para acierto de un disparo: en este caso si acierta, el oponente pierde 2 balas
    }else if(carta==35){
        condiciones.put("recargarBalaJugador",(jugador,oponente)->jugador.getBalas()==0);
        condiciones.put("descartaYRoba",(jugador,oponente)->jugador.getBalas()==0);
    }else if(carta==39){
        condiciones.put("recargarBalaJugador",(jugador,oponente)->jugador.getAccionOponenteAnterior()!=null &&jugador.getAccionOponenteAnterior().equals(TipoCarta.FINTA));
        condiciones.put("recargarBalaOponente",(jugador,oponente)->jugador.getAccionOponenteAnterior()!=null &&jugador.getAccionOponenteAnterior().equals(TipoCarta.FINTA));
    }else if(carta==40){
        condiciones.put("recargarBalaJugador",(jugador,oponente)->jugador.getAccionOponente()!=null &&jugador.getAccionOponente().equals(TipoCarta.FINTA));
    }else if(carta==43){
        condiciones.put("ganarPrecisionOponente",(jugador,oponente)->oponente.getPrecision()>=4);
    }else if(carta==44){
        condiciones.put("ganarPrecisionJugador",(jugador,oponente)->oponente.getSalud()>jugador.getSalud());
    }else if(carta==50){
        //ganas 3 puntos de precision en el proximo turno si fallas el disparo
    }
    return condiciones;
}
}
