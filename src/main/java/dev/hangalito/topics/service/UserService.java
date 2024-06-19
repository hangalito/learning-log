package dev.hangalito.topics.service;

import dev.hangalito.topics.exceptions.InvalidCredentialException;
import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TopicService   topicService;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found"));
        return org.springframework.security.core.userdetails.User.builder().username(user.getUsername()).password(
                user.getPassword()).build();
    }

    public User getUser(Principal principal) {
        return userRepository.findByUsername(principal.getName()).orElseThrow(IllegalStateException::new);
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
            log.debug("Incorrect password");
            throw new InvalidCredentialException("Incorrect password");
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    public List<Topic> getTopics(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(IllegalStateException::new);
        return topicService.getTopics(user);
    }

    public void addTopic(Topic topic, Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(IllegalStateException::new);
        topic.setAuthor(user);
        topicService.addTopic(topic);
    }

    public void deleteTopic(int id, Principal principal) {
        userRepository.findByUsername(principal.getName()).orElseThrow(IllegalStateException::new);
        topicService.deleteById(id);
    }

}
