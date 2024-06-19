package dev.hangalito.topics.controller;

import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class TopicController {

    private final UserService userService;

    @ModelAttribute(name = "username")
    public String username(Principal principal) {
        return principal.getName();
    }

    @GetMapping("/topic/new")
    public String newTopic(Principal principal, Model model) {
        model.addAttribute("user", userService.getUser(principal));
        model.addAttribute("topic", new Topic());
        return "new-topic";
    }

    @PostMapping("/topic/new")
    public String newTopic(@Valid Topic topic, Errors errors, Principal principal) {
        if (errors.hasErrors()) {
            return "new-topic";
        }
        userService.addTopic(topic, principal);
        return "redirect:/home";
    }


    @PostMapping("/topic/delete")
    public String deleteTopic(@RequestParam int id, Principal principal) {
        userService.deleteTopic(id, principal);
        return "redirect:/home";
    }

}
