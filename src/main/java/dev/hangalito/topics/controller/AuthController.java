package dev.hangalito.topics.controller;

import dev.hangalito.topics.exceptions.InvalidCredentialException;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Objects;

@Slf4j
@Controller
@AllArgsConstructor
public class AuthController {

    private static final String      DO_NOT_MATCH       = "Passwords don't match";
    private static final String      INCORRECT_PASSWORD = "Incorrect password";
    private static final String      TOO_SHORT_PASSWORD = "Password should be at least 8 characters long";
    private final        UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid credentials");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String register(@RequestParam(required = false) String error, Model model) {
        model.addAttribute("user", new User());
        if (error != null) {
            model.addAttribute("error", "Passwords do not match");
        }
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return "auth/register";
        }
        userService.createAccount(user);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = userService.getUser(principal);
        model.addAttribute("id", user.getId());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("firstName", user.getFirstName());
        model.addAttribute("lastName", user.getLastName());
        return "auth/profile";
    }

    @PostMapping("/profile/update")
    public String profile(
            @RequestParam int id,
            @RequestParam String firstName,
            @RequestParam String lastName,
            Model model, Principal principal
    ) {
        if (firstName.isBlank() || lastName.isBlank()) {
            model.addAttribute("username", principal.getName());
            model.addAttribute("firstNameMsg", "This field is required");
            model.addAttribute("lastNameMsg", "This field is required");
            return "auth/profile";
        }
        userService.updateAccountDetails(id, firstName, lastName);
        return "redirect:/profile";
    }

    @GetMapping("/profile/credentials")
    public String credentials(
            @RequestParam(required = false) String error,
            Principal principal,
            Model model,
            HttpSession session
    ) {
        model.addAttribute("user", userService.getUser(principal));

        if (error != null) {
            String invalid       = (String) session.getAttribute(INCORRECT_PASSWORD);
            String shortPassword = (String) session.getAttribute(TOO_SHORT_PASSWORD);
            String doNotMatch    = (String) session.getAttribute(DO_NOT_MATCH);

            if (invalid != null) {
                model.addAttribute("currentPasswordError", invalid);
            }
            if (shortPassword != null) {
                model.addAttribute("shortMessage", shortPassword);
            }
            if (doNotMatch != null) {
                model.addAttribute("error", doNotMatch);
            }
        }

        return "auth/credentials";
    }

    @PostMapping("/profile/credentials")
    public String credentials(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String newPasswordConfirm,
            Principal principal,
            HttpSession session
    ) {
        if (newPassword.length() < 8) {
            session.setAttribute(TOO_SHORT_PASSWORD, TOO_SHORT_PASSWORD);
            return "redirect:/profile/credentials?error";
        } else if (!Objects.equals(newPassword, newPasswordConfirm)) {
            session.setAttribute(DO_NOT_MATCH, DO_NOT_MATCH);
            return "redirect:/profile/credentials?error";
        } else {
            try {
                userService.updatePassword(principal, currentPassword, newPassword);
            } catch (InvalidCredentialException exception) {
                session.setAttribute(INCORRECT_PASSWORD, INCORRECT_PASSWORD);
                return "redirect:/profile/credentials?error";
            }
        }
        return "redirect:/profile";
    }

}
