package org.springframework.samples.petclinic.Jugador;
import java.util.ArrayList;
import java.util.List;

import org.springframework.samples.petclinic.Carta.Carta;
import org.springframework.samples.petclinic.Carta.TipoCarta;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.user.User;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Jugador extends BaseEntity{

    @OneToOne
    private User user;

    @JsonIgnore
    @OneToOne
    private Estadisticas estadisticas=new Estadisticas();

    @OneToMany
    private List<Carta> cartas=new ArrayList<>();
    
    @OneToOne
    private Carta cartaUsada;

    @OneToOne
    private Carta miradaFija;

    @JsonIgnore
    private TipoCarta accionOponente;

    @JsonIgnore
    private TipoCarta accionOponenteAnterior;

    public Jugador(){
    }
    public Jugador(User user){
    this.user=user;
    }
    @Min(0)
    @Max(2)
    private int salud=2;
    @Min(0)
    @Max(6)
    private int balas=2;
    @Min(0)
    @Max(6) 
    private int precision=2;
    
}