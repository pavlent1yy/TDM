package ru.TDM.todomaganer.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.TDM.todomaganer.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}

