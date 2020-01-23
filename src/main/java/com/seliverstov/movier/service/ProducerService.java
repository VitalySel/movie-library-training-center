package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Producer;
import com.seliverstov.movier.repository.ProducerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    @Autowired
    private ProducerRepository produserRepository;
}
