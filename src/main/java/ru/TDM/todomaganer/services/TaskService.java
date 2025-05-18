package ru.TDM.todomaganer.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.TDM.todomaganer.Task;
import ru.TDM.todomaganer.User;
import ru.TDM.todomaganer.repos.TaskRepository;
import ru.TDM.todomaganer.repos.UserRepository;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TaskService {
    public final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public void addTaskToUser(Task task, User user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow();
        task.setUser(existingUser);
        task.setId(null);
        task.setCreatedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    public void deleteTaskById(Long task_id) {
        taskRepository.deleteById(task_id);
    }

    public void changeIsCompleted(Long task_id){
        Task task = taskRepository.findById(task_id).orElseThrow();
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }


}
