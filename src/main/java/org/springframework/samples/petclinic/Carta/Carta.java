package org.springframework.samples.petclinic.Carta;

import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;

import org.springframework.samples.petclinic.Jugador.Jugador;
import org.springframework.samples.petclinic.model.BaseEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="cartas")
public class Carta extends BaseEntity{
    
    private String imagen;

    @Enumerated(EnumType.STRING)
    private TipoCarta tipo;

    @JsonIgnore
    private int precisionJugador;

    @JsonIgnore
    private int balasJugador;

    @JsonIgnore
    private int precisionOponente;

    @JsonIgnore
    private int balasOponente;

    @JsonIgnore
    private boolean gastaBala;

    @JsonIgnore
    private boolean gastaPrecision;

    @JsonIgnore
    private boolean descartar=false;

    @JsonIgnore
    private List<String> opciones;

}
