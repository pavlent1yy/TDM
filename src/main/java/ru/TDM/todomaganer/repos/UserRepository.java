package ru.TDM.todomaganer.repos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.TDM.todomaganer.entities.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(@NonNull @NotBlank(message = "Name cannot be blank!") String name);
    boolean existsByEmail(@NonNull @Email(message = "Email should be valid!") @NotBlank(message = "Email cannot be blank!") String email);

//    String findByName(String username);

    Optional<User> findByEmail(@NonNull @NotBlank(message = "Email cannot be blank!") String email);
    Optional<User> findByName(@NonNull @NotBlank(message = "Username cannot be blank!") String username);
}
