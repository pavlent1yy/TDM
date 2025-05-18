package ru.TDM.todomaganer.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.TDM.todomaganer.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}

