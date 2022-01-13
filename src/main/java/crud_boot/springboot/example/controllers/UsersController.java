package crud_boot.springboot.example.controllers;

import crud_boot.springboot.example.models.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UsersController {

    @GetMapping()
    public String getUser(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("roles", user.getRoles());
        model.addAttribute("user", user);
        return "user";
    }
}