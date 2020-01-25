package com.seliverstov.movier.repository;

import com.seliverstov.movier.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByMail(String mail);
    User findByUsername(String username);
    User findByActivationCode(String code);
}