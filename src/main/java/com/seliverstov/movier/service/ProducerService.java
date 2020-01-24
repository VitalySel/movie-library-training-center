package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.domain.Producer;
import com.seliverstov.movier.repository.ProducerRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
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

    public Integer getIdProducerName(String name) throws NotFoundException {
        Producer producer = produserRepository.findByName(name);
        if (producer == null){
            throw new NotFoundException("Producer not found with name - "+ name);
        }
        return producer.getId();
    }

    public void update(Producer producer) {
        produserRepository.save(producer);
    }
}
