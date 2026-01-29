package org.springframework.samples.petclinic.Jugador;

import java.time.LocalTime;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class Estadisticas extends BaseEntity{
    
    private int partidasJugadas=0;

    private int partidasGanadas=0;

    private String cartaMasUsada = null;

    private LocalTime tiempoMedioDePartida = null;

    private double numerodeTurnosMedioPorPartida = 0.;
}
