package dev.hangalito.topics.service;

import dev.hangalito.topics.exceptions.InvalidCredentialException;
import dev.hangalito.topics.model.Subject;
import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TopicService   topicService;
    private final SubjectService subjectService;

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
            throw new InvalidCredentialException("Incorrect password");
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    public List<Topic> getTopics(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(IllegalStateException::new);
        return topicService.getTopics(user);
    }

    public Topic getTopic(String name, Principal principal) {
        return topicService.getTopic(getUser(principal), name)
                           .orElseThrow(() -> HttpClientErrorException.NotFound
                                   .create("Could not find the specified topic",
                                           HttpStatusCode.valueOf(404),
                                           "Not Found",
                                           null, null,
                                           StandardCharsets.UTF_8
                                   )
                           );
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

    public List<Subject> getSubjects(Principal principal) {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow(IllegalStateException::new);
        return subjectService.getSubjects(user);
    }

    public List<Subject> getSubjects(Principal principal, String topicName) {
        User  user  = userRepository.findByUsername(principal.getName()).orElseThrow(IllegalStateException::new);
        Topic topic = topicService.getTopic(user, topicName).orElseThrow(IllegalStateException::new);
        return subjectService.getSubjectByTopic(topic, user);
    }

    public void createSubject(String content, int topicId, Principal principal) {
        var user    = getUser(principal);
        var subject = new Subject();
        var topic   = topicService.getById(topicId);
        subject.setAuthor(user);
        subject.setContent(content);
        subject.setTopic(topic);
        subjectService.addSubject(subject);
    }

    public void deleteSubject(int id) {
        subjectService.deleteById(id);
    }

}
