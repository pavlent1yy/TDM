package ru.TDM.todomaganer.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.TDM.todomaganer.LogMessages;
import ru.TDM.todomaganer.entities.Task;
import ru.TDM.todomaganer.entities.User;
import ru.TDM.todomaganer.repos.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void addUser(User user) {
        user.setRegisteredAt(LocalDateTime.now());
        try {
            userRepository.save(user);
            LOGGER.info(LogMessages.INFO.USER_CREATED, user.getId(), user.getName(), user.getEmail());
        } catch (Exception e) {
            LOGGER.error(LogMessages.ERROR.USER_DUBLICATE_KEY_VALUE_ERROR, e.getMessage());
        }
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
        LOGGER.info(LogMessages.INFO.USER_DELETED, id);
    }

    public List<Task> getTasksFromUser(User user){
        return user.getTasks();
    }

}
