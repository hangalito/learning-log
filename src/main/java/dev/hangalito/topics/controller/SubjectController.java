package dev.hangalito.topics.controller;

import dev.hangalito.topics.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class SubjectController {

    private final UserService userService;

    @ModelAttribute(name = "username")
    public String username(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/subjects")
    public String subjects(Model model, Principal principal) {
        model.addAttribute("subjects", userService.getSubjects(principal));
        return "subjects";
    }

    @PostMapping("/subjects")
    public String subjects(@RequestParam String topic, Model model, Principal principal) {
        model.addAttribute("subjects", userService.getSubjects(principal, topic));
        return "subjects";
    }

}
