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
class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<Subject> getSubjects(User user) {
        return (List<Subject>) subjectRepository.findByAuthorOrderByContent(user);
    }

    public List<Subject> getSubjectByTopic(Topic topic, User user) {
        return (List<Subject>) subjectRepository.findByTopicAndAuthorOrderByContent(topic, user);
    }

    public void addSubject(Subject subject) {
        if (subject.getAuthor() == null) throw new IllegalStateException("Cannot add a Subject with a null author");
        if (subject.getTopic() == null) throw new IllegalStateException("Cannot add a Subject with a null topic");
        subjectRepository.save(subject);
    }

}
