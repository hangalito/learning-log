package dev.hangalito.topics.controller;

import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.service.TopicService;
import dev.hangalito.topics.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final UserService  userService;
    private final TopicService topicService;

    @ModelAttribute(name = "topics")
    public List<Topic> topics(Principal principal) {
        return topicService.getTopics(principal.getName());
    }

    @ModelAttribute(name = "username")
    public String username(Principal principal) {
        return principal.getName();
    }

    @ModelAttribute(name = "newTopic")
    public Topic newTopic() {
        return new Topic();
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

}
