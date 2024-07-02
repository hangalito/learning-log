package dev.hangalito.topics.repository;

import dev.hangalito.topics.model.Subject;
import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {

    Iterable<Subject> findByAuthorUsernameOrderByContent(String username);

    List<Subject> findAllByTopicSlugOrderByContent(String topicSlug);

    Iterable<Subject> findByTopicAndAuthorOrderByContent(Topic topic, User author);

}
