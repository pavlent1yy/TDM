package ru.TDM.todomaganer.repos;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.TDM.todomaganer.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByName(@NonNull @NotBlank(message = "Name cannot be blank!") String name);
    boolean existsByEmail(@NonNull @Email(message = "Email should be valid!") @NotBlank(message = "Email cannot be blank!") String email);

}
