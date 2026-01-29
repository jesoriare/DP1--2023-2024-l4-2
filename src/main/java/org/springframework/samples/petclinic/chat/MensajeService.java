package org.springframework.samples.petclinic.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class MensajeService {
    @Autowired
    private MensajeRepository mensajeRepository;
    @Transactional
    public void guardarMensaje(Mensaje mensaje) throws DataAccessException{
        mensajeRepository.save(mensaje);
    }
}
