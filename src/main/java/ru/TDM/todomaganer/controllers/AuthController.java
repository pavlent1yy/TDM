package ru.TDM.todomaganer.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.TDM.todomaganer.entities.User;
import ru.TDM.todomaganer.services.AuthService;
import ru.TDM.todomaganer.services.CustomUserDetailsService;
import ru.TDM.todomaganer.services.UserService;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/register")
    public String registerSubmit(String name, String email, String password, Model model){
        User user = new User();
        if (!userService.isEmailInUse(email) && !userService.isUsernameInUse(name)) {
            user = authService.registerNewUser(name, email, password);
        }
        return "redirect:/ui/users/" + user.getId();
    }



}
