package com.seliverstov.movier.repository;

import com.seliverstov.movier.domain.Movie;
import com.seliverstov.movier.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findByMail(String mail);
    User findByUsername(String username);
    User findByActivationCode(String code);




}