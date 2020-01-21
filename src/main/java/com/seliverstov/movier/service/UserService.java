package com.seliverstov.movier.service;

import com.seliverstov.movier.domain.Role;
import com.seliverstov.movier.domain.User;
import com.seliverstov.movier.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User findByActivationCode(String code){
        return userRepo.findByActivationCode(code);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user with name '" + username + "'");
        }
        return user;
    }

    public void addUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        String message = String.format(
                "Hello %s! \n" +
                        "Welcome to MovieCatalog. Visit next link to activate your account : \n" +
                        "http://localhost:8080/activate/%s",
                user.getRealname(),
                user.getActivationCode()
        );
        mailService.send(user.getMail(),"Activation code", message);
    }

    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if(user == null){
            return false;
        }
        user.setActivationCode(null);
        user.setActive(true);
        userRepo.save(user);
        return true;
    }

    public void update(User user){
        userRepo.save(user);
    }
}