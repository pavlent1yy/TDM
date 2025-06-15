package ru.TDM.todomaganer.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.TDM.todomaganer.LogMessages;
import ru.TDM.todomaganer.entities.Task;
import ru.TDM.todomaganer.entities.User;
import ru.TDM.todomaganer.repos.UserRepository;

import java.io.IOException;
import java.io.InputStream;
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

    public User getUserByIdOrNull(Long id){
        if (userRepository.findById(id).isPresent())
            return userRepository.findById(id).get();
        return null;
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

    public ResponseEntity<byte[]> getUserAvatar(Long id){
        User user = getUserById(id).orElseThrow();
        byte[] avatar = user.getAvatar();

        if (avatar == null || avatar.length == 0) {
            try {
                InputStream defaultAvatar = getClass().getResourceAsStream("/static/images/default_avatar.jpg");
                assert defaultAvatar != null;
                avatar = defaultAvatar.readAllBytes();
            } catch (IOException e) {
                return ResponseEntity.notFound().build();
            }
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(avatar);
    }

}
