package ru.TDM.todomaganer.repos;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.TDM.todomaganer.entities.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByTitle(@NonNull @NotBlank(message = "Title cannot be blank") String title);
    List<Task> findByDescription(@NonNull @NotBlank(message = "Description cannot be blank") String title);
}

