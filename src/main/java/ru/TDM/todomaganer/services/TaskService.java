package ru.TDM.todomaganer.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.TDM.todomaganer.LogMessages;
import ru.TDM.todomaganer.entities.Task;
import ru.TDM.todomaganer.entities.User;
import ru.TDM.todomaganer.repos.TaskRepository;
import ru.TDM.todomaganer.repos.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskService {
    public final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);


    public void addTaskToUser(Task task, User user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow();
        task.setUser(existingUser);
        task.setId(null);
        task.setCreatedAt(LocalDateTime.now());
        taskRepository.save(task);
        LOGGER.info(LogMessages.INFO.TASK_CREATED, user.getId(), task.getId());
    }

    public void deleteTaskById(Long taskId) {
        taskRepository.deleteById(taskId);
        LOGGER.info(LogMessages.INFO.TASK_DELETED, taskId);
    }

    public void changeIsCompleted(Long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow();
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
        LOGGER.info(LogMessages.INFO.TASK_IS_COMPLETE_CHANGING, taskId, task.isCompleted());
    }


    public void editTask(Task task, User user, LocalDateTime createdAt) {
        task.setUser(user);
        task.setCreatedAt(createdAt);
        taskRepository.save(task);
        LOGGER.info(LogMessages.INFO.TASK_EDITED, task.getId());
    }


    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow();
    }

    public List<Task> findTasksByTitle(String title, Long id) {
        List<Task> tasks = new ArrayList<>();
        if (userRepository.findById(id).isPresent()) {
            tasks = userRepository.findById(id).get().getTasks().stream()
                    .filter(task -> task.getTitle().equals(title)).collect(Collectors.toList());
        }
        return tasks;
    }

    public List<Task> findTasksByDescription(String description, Long id) {
        List<Task> tasks = new ArrayList<>();
        if (userRepository.findById(id).isPresent()) {
            tasks = userRepository.findById(id).get().getTasks().stream()
                    .filter(task -> task.getDescription().equals(description)).collect(Collectors.toList());
        }
        return tasks;
    }

    public List<Task> findTasksByCreationDate(String date, Long id){
        List<Task> tasks = new ArrayList<>();
        if (userRepository.findById(id).isPresent() && isInteger(date)) {
            int dateToInt = Integer.parseInt(date);
            tasks = userRepository.findById(id).get().getTasks().stream()
                    .filter(task
                            -> task.getCreatedAt().getDayOfMonth() == dateToInt
                            || task.getCreatedAt().getMonthValue() == dateToInt
                            || task.getCreatedAt().getYear() == dateToInt
                    ).collect(Collectors.toList());
        }
        return tasks;
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
