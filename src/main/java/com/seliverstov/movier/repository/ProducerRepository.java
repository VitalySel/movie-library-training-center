package com.seliverstov.movier.repository;

import com.seliverstov.movier.domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer,Long> {

    Producer findByName(String name);

    Producer findById(int id);
}
