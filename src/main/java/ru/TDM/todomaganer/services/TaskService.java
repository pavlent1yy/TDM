package ru.TDM.todomaganer.services;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.TDM.todomaganer.LogMessages;
import ru.TDM.todomaganer.entities.Role;
import ru.TDM.todomaganer.entities.Task;
import ru.TDM.todomaganer.entities.User;
import ru.TDM.todomaganer.repos.TaskRepository;
import ru.TDM.todomaganer.repos.UserRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Predicate;
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


    public List<Task> searchTask(String searchText, Long userId) {
        List<Task> byTitle = findTasksByTitle(searchText, userId);
        List<Task> byDescription = findTasksByDescription(searchText, userId);
        List<Task> byDate = findTasksByCreationDate(searchText, userId);

        Set<Task> set = new HashSet<>(byTitle);
        set.addAll(byDescription);
        set.addAll(byDate);
        return new ArrayList<>(set);
    }


    public List<Task> findTasksByTitle(String titlePart, Long id) {
        return filterTasks(id, task ->
                task.getTitle().toLowerCase().contains(titlePart.toLowerCase().trim()));
    }

    public List<Task> findTasksByDescription(String descriptionPart, Long id) {
        return filterTasks(id, task ->
                task.getDescription().toLowerCase().contains(descriptionPart.toLowerCase().trim()));
    }

    public List<Task> findTasksByCreationDate(String date, Long id) {
        if (!isInteger(date)) {
            return List.of();
        }
        int dateToInt = Integer.parseInt(date);
        return filterTasks(id, task ->
                task.getCreatedAt().getDayOfMonth() == dateToInt ||
                        task.getCreatedAt().getMonthValue() == dateToInt ||
                        task.getCreatedAt().getYear() == dateToInt);
    }

    private List<Task> filterTasks(Long id, Predicate<Task> condition) {
        return userRepository.findById(id)
                .map(user -> user.getTasks().stream()
                        .filter(condition)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    private List<Task> findAllTasksByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow().getTasks();
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<Task> getUserTasks(Long userId) {
        User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (current.getRole() == Role.OWNER) {
            return findAllTasksByUserId(userId);
        }
        if (!Objects.equals(current.getId(), userId)) {
            throw new AccessDeniedException("You do not have permission to access this resource");
        }
        return findAllTasksByUserId(userId);
    }


}
