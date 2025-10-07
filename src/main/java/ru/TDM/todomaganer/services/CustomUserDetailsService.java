package ru.TDM.todomaganer.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.TDM.todomaganer.repos.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        String trimmed = login.trim();
        return userRepository.findByName(trimmed)
                .or( () -> userRepository.findByEmail(trimmed))
                .orElseThrow( () -> new UsernameNotFoundException("User not found"));
    }
}
