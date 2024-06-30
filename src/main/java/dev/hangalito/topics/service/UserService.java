package dev.hangalito.topics.service;

import dev.hangalito.topics.exceptions.InvalidCredentialException;
import dev.hangalito.topics.model.Subject;
import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TopicService   topicService;
    private final SubjectService subjectService;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

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
