package ru.TDM.todomaganer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.TDM.todomaganer.Task;
import ru.TDM.todomaganer.User;
import ru.TDM.todomaganer.repos.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> addUser(User user) {
        user.setRegisteredAt(LocalDateTime.now());
        return Optional.of(userRepository.save(user));
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public List<Task> getTasksFromUser(User user){
        return user.getTasks();
    }

}
