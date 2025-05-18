package ru.TDM.todomaganer.controllers;

import lombok.AllArgsConstructor;
import org.hibernate.property.access.internal.PropertyAccessStrategyResolverInitiator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.TDM.todomaganer.Task;
import ru.TDM.todomaganer.User;
import ru.TDM.todomaganer.services.TaskService;
import ru.TDM.todomaganer.services.UserService;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/ui/users")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    @GetMapping
    public String usersPage(Model model){
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        return "users";
    }

    @GetMapping("/{id}")
    public String userTasksPage(Model model, @PathVariable Long id){
        User user = userService.getUserById(id).get();
        model.addAttribute("tasks", userService.getTasksFromUser(user));
        model.addAttribute("task", new Task());
        model.addAttribute("user", user);
        return "userTasks";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/ui/users";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/ui/users";
    }

    






}
