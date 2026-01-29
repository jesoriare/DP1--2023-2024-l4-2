package org.springframework.samples.petclinic.Dado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DadoService {
    @Autowired
    private DadoRepository dadoRepository;
    @Transactional
    public void guardarDado(Dado dado) throws DataAccessException{
        dadoRepository.save(dado);
    }
}
