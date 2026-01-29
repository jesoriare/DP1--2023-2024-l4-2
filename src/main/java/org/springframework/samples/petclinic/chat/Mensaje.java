package org.springframework.samples.petclinic.chat;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Mensaje extends BaseEntity{
    private String escritor;
    private String textoMensaje;
}
