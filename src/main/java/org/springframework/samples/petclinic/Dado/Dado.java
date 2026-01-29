package org.springframework.samples.petclinic.Dado;

import org.springframework.samples.petclinic.model.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Dado extends BaseEntity{
    @Max(6)
    @Min(1)
    private int numero=1;
    
    private boolean tirando=false;
}
