package ru.TDM.todomaganer.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.TDM.todomaganer.entities.Task;
import ru.TDM.todomaganer.entities.User;
import ru.TDM.todomaganer.services.TaskService;
import ru.TDM.todomaganer.services.UserService;

import java.io.IOException;


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

    @GetMapping("/about")
    public String aboutPage(Model model){
        return "about-page";
    }

    @GetMapping("/error")
    public String showErrorPage(){
        return "error-page";
    }



    @GetMapping("/{id}")
    public String userTasksPage(Model model, @PathVariable Long id){
        if(userService.getUserById(id).isPresent()) {
            User user = userService.getUserById(id).get();
            model.addAttribute("tasks", userService.getTasksFromUser(user));
            model.addAttribute("task", new Task());
            model.addAttribute("user", user);
        }
        return "userTasks";
    }

    @GetMapping("/{id}/avatar")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) {
        return userService.getUserAvatar(id);
    }


    @PostMapping("/add")
    public String addUser(@RequestParam("name") String name,
                          @RequestParam("email") String email,
                          @RequestParam("avatar") MultipartFile avatarFile) throws IOException {

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        if (!avatarFile.isEmpty())
            user.setAvatar(avatarFile.getBytes());

        try {
            userService.addUser(user);
        } catch (Exception e) {
            return "redirect:/ui/users/error";
        }

        return "redirect:/ui/users";
    }


    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/ui/users";
    }







}
