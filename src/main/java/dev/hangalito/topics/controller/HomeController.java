package dev.hangalito.topics.controller;

import dev.hangalito.topics.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserService userService;

    @ModelAttribute(name = "username")
    public String username(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

}
