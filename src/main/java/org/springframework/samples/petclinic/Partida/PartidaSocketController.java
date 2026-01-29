package org.springframework.samples.petclinic.Partida;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class PartidaSocketController {
    @MessageMapping("/updateGame/{gameId}")
    @SendTo("/partidasgf/{gameId}")
    public Partida handleGameUpdate(@PathVariable("gameId") String gameId, @RequestBody Partida partida) {
        return partida;
    }
}
