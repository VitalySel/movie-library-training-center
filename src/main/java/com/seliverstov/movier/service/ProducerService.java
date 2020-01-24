package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.domain.Producer;
import com.seliverstov.movier.repository.ProducerRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProducerService {

    @Autowired
    private ProducerRepository produserRepository;

    public Producer getProducerId(int id) throws NotFoundException {

        Optional<Producer> optionalProducer = Optional.ofNullable(produserRepository.findById(id));

        if (!optionalProducer.isPresent()) {
            throw new NotFoundException("Producer not found with id - " + id);
        }
        return optionalProducer.get();
    }

    public void update(Producer producer) {
        produserRepository.save(producer);
    }
}
