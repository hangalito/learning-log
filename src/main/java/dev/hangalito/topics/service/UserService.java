package dev.hangalito.topics.service;

import dev.hangalito.topics.exceptions.InvalidCredentialException;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.security.Principal;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatusCode.valueOf;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public User getUser(Principal principal) {
        return userRepository.findByUsername(principal.getName()).orElseThrow(IllegalStateException::new);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> HttpServerErrorException
                .InternalServerError
                .create(valueOf(404), "Could not determine the user details", null, null, UTF_8)
        );
    }

    public void createAccount(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateAccountDetails(Integer id, String firstName, String lastName) {
        User user = userRepository.findById(id).orElseThrow(IllegalStateException::new);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepository.save(user);
    }

    public void updatePassword(Principal principal, String currentPassword, String newPassword) throws InvalidCredentialException {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(IllegalStateException::new);
        if (!encoder.matches(currentPassword, user.getPassword())) {
            throw new InvalidCredentialException("Incorrect password");
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

}
