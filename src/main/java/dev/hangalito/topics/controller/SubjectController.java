package dev.hangalito.topics.controller;

import dev.hangalito.topics.model.Subject;
import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.service.SubjectService;
import dev.hangalito.topics.service.TopicService;
import dev.hangalito.topics.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@AllArgsConstructor
public class SubjectController {

    private final UserService    userService;
    private final SubjectService subjectService;
    private final TopicService   topicService;

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
        return userService.findByUsername(principal.getName());
    }

    @ModelAttribute(name = "topics")
    public List<Topic> topics(Principal principal) {
        return topicService.getTopics(principal.getName());
    }

    @GetMapping("/subjects")
    public String subjects(Model model, Principal principal) {
        if (principal == null) return "redirect:/login";
        List<Subject> subjects = subjectService.getSubjects(principal.getName());
        model.addAttribute("subjects", subjects);
        model.addAttribute("title", "All subjects");
        if (subjects.isEmpty()) {
            model.addAttribute("msg", "You have no topics saved yet");
        } else {
            model.addAttribute("msg", "All saved subjects");
        }
        return "subjects";
    }

    @GetMapping("/topics/{topic}")
    public String subjects(@PathVariable String topic, Model model) {
        List<Subject> subjects = subjectService.getAllByTopic(topic);
        model.addAttribute("topic", topicService.getBySlug(topic));
        model.addAttribute("subjects", subjects);
        if (subjects.isEmpty()) {
            model.addAttribute("msg", "There's nothing to see here");
        } else {
            model.addAttribute("msg", "Subjects on " + subjects.getFirst().getTopic().getName());
        }
        return "subjects";
    }

    @PostMapping("/subject/new")
    public String addSubject(
            @Valid Subject subject,
            @RequestParam(required = false) String topicName, Principal principal
    ) {
        var s = subjectService.addSubject(subject, principal.getName());
        if (topicName == null) {
            return "redirect:/subjects";
        } else {
            return "redirect:/topics/" + s.getTopic().getSlug();
        }
    }

    @GetMapping("/subject/delete/{id}/{next}")
    public String deleteTopic(@PathVariable int id, @PathVariable String next, Principal principal) {
        subjectService.deleteById(id, principal.getName());
        String uri;
        if (Objects.equals(next, "All subjects")) {
            uri = "redirect:/subjects";
        } else {
            uri = "redirect:/topics/" + next;
        }
        return uri;
    }

}
