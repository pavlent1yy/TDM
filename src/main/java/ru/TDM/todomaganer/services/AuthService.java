package ru.TDM.todomaganer.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.TDM.todomaganer.entities.User;
import ru.TDM.todomaganer.repos.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public User registerNewUser(String username, String email, String password) {
        if (userRepository.findByName(username).isPresent() || userRepository.findByEmail(email).isPresent()) {

            if (username.equals(userRepository.findByName(username).get().getName())
                    || email.equals(userRepository.findByEmail((email)).get().getEmail())) {
                log.warn("Username already exists");
                throw new RuntimeException("Username already exists");
            }
        }
        String encodedPassword = passwordEncoder.encode(password);
        log.info("User created");
        return userService.createNewUser(username, email, encodedPassword);
    }

    

}
