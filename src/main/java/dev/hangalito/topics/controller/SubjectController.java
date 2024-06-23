package dev.hangalito.topics.controller;

import dev.hangalito.topics.model.Subject;
import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class SubjectController {

    private static final Logger      log = LoggerFactory.getLogger(SubjectController.class);
    private final        UserService userService;

    @ModelAttribute(name = "username")
    public String username(Principal principal) {
        return principal.getName();
    }

    @ModelAttribute(name = "newSubject")
    public Subject subject() {
        return new Subject();
    }

    @ModelAttribute(name = "user")
    public User user(Principal principal) {
        return userService.getUser(principal);
    }

    @ModelAttribute(name = "topics")
    public List<Topic> topics(Principal principal) {
        return userService.getTopics(principal);
    }

    @GetMapping("/subjects")
    public String subjects(Model model, Principal principal) {
        model.addAttribute("subjects", userService.getSubjects(principal));
        model.addAttribute("title", "All subjects");
        return "subjects";
    }

    @GetMapping("/topics/{topicName}")
    public String subjects(@PathVariable String topicName, Principal principal, Model model) {
        List<Subject> subjects = userService.getSubjects(principal, topicName);
        Topic         topic    = userService.getTopic(topicName, principal);
        model.addAttribute("subjects", subjects);
        model.addAttribute("topic", topic);
        model.addAttribute("title", topicName);
        return "subjects";
    }

    @PostMapping("/subject/new")
    public String addSubject(
            @RequestParam String content, @RequestParam(name = "topic") int topicId,
            @RequestParam(required = false) String topicName, Principal principal
    ) {
        userService.createSubject(content, topicId, principal);
        if (topicName == null) {
            return "redirect:/subjects";
        } else {
            return "redirect:/topics/" + topicName;
        }
    }

}
