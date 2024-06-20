package dev.hangalito.topics.service;

import dev.hangalito.topics.model.Subject;
import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.repository.SubjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
class SubjectService {

    private final SubjectRepository subjectRepository;

    public List<Subject> getSubjects(User user) {
        List<Subject> subjects = new ArrayList<Subject>();
        subjectRepository.findByAuthor(user).forEach(subjects::add);
        subjects.sort(Comparator.comparing(Subject::getContent));
        return subjects;
    }

    public List<Subject> getSubjectByTopic(Topic topic, User user) {
        List<Subject> subjects = new ArrayList<Subject>();
        subjectRepository.findByTopicAndAuthor(topic, user).forEach(subjects::add);
        subjects.sort(Comparator.comparing(Subject::getContent));
        return subjects;
    }

    public void addSubject(Subject subject) {
        subjectRepository.save(subject);
    }

}
