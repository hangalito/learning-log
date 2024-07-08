package dev.hangalito.topics.service;

import dev.hangalito.topics.model.Subject;
import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserService       userService;

    public List<Subject> getSubjects(String username) {
        return (List<Subject>) subjectRepository.findByAuthorUsernameOrderByContent(username);
    }

    public List<Subject> getSubjectByTopic(Topic topic, User user) {
        return (List<Subject>) subjectRepository.findByTopicAndAuthorOrderByContent(topic, user);
    }

    public List<Subject> getAllByTopic(String topic) {
        return subjectRepository.findAllByTopicSlugOrderByContent(topic);
    }

    public Subject addSubject(Subject subject, String username) {
        User user = userService.findByUsername(username);
        subject.setAuthor(user);
        if (subject.getTopic() == null) throw new IllegalStateException("Cannot add a Subject with a null topic");
        return subjectRepository.save(subject);
    }

    public void deleteById(int id, String username) {
        subjectRepository
                .findById(id)
                .ifPresent(subject -> {
                    if (subject.getAuthor().getUsername().equals(username)) {
                        subjectRepository.delete(subject);
                    }
                });

    }

}
