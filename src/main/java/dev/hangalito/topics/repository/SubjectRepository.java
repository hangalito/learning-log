package dev.hangalito.topics.repository;

import dev.hangalito.topics.model.Subject;
import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import org.springframework.data.repository.CrudRepository;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {

    Iterable<Subject> findByTopic(Topic topic);

    Iterable<Subject> findByTopicAndAuthor(Topic topic, User author);

}