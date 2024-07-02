package dev.hangalito.topics.controller;

import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.service.TopicService;
import dev.hangalito.topics.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class TopicController {

    private final UserService  userService;
    private final TopicService topicService;

    @ModelAttribute(name = "username")
    public String username(Principal principal) {
        return principal.getName();
    }

    @PostMapping("/topic/new")
    public String newTopic(@Valid Topic topic, Principal principal) {
        topicService.addTopic(topic, principal.getName());
        return "redirect:/home";
    }


    @GetMapping("/topic/delete/{id}")
    public String deleteTopic(@PathVariable int id, Principal principal) {
        topicService.deleteById(id, principal.getName());
        return "redirect:/home";
    }

}
