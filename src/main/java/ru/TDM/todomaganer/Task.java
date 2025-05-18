package ru.TDM.todomaganer;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String title;

    private String description;

    private LocalDateTime createdAt;

    private boolean isCompleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
