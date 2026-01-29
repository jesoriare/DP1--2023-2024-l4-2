package org.springframework.samples.petclinic.Jugador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/estadisticas")
@SecurityRequirement(name = "bearerAuth")
public class EstadisticasController {
    @Autowired
    private EstadisticasService estadisticasService;

    @Autowired
    private JugadorService jugadorService;

    @GetMapping("/{username}")
    public ResponseEntity<Estadisticas> getEstadisticasJugador(@PathVariable("username") String username){
        final Jugador j = jugadorService.getJugadorByUsername(username);
        if(j==null)  {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        Estadisticas estadisticas=estadisticasService.getEstadisticas(j.getEstadisticas().getId());
        
        if ( estadisticas== null){

             return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(estadisticas,HttpStatus.OK);
    }
}
