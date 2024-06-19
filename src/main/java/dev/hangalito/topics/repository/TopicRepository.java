package dev.hangalito.topics.repository;

import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, Integer> {

    Iterable<Topic> findByAuthor(User author);

    Optional<Topic> findByAuthorAndName(User author, String name);

    Optional<Topic> findByAuthorAndId(User author, int id);

    void deleteByNameAndAuthor(String name, User author);

}
