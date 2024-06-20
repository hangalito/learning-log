package dev.hangalito.topics.controller;

import dev.hangalito.topics.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String subjects(Principal principal, Model model, HttpSession session) {
        String attr = ((String) session.getAttribute("topic"));
        if (attr == null) {
            model.addAttribute("subjects", userService.getSubjects(principal));
        } else {
            model.addAttribute("subjects", userService.getSubjects(principal, attr));
        }
        return "subjects";
    }

    @GetMapping("/subjects/{topic}")
    public String subjects(@PathVariable String topic, Principal principal, Model model, HttpSession session) {
        session.setAttribute("topic", topic);
        return "redirect:/subjects";
    }

}
