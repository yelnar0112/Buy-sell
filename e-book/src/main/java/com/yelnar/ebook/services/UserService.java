package com.yelnar.ebook.services;

import com.yelnar.ebook.models.User;
import com.yelnar.ebook.models.enums.Roles;
import com.yelnar.ebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean createUser(User user){
        String email = user.getEmail();
        if(userRepository.findByEmail(user.getEmail()) != null) return false;
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Roles.ROLE_USER);
        log.info("Saving new user with email: {}" , email);
        userRepository.save(user);
        return true;
    }
}
