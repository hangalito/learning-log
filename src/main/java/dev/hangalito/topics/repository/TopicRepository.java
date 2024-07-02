package dev.hangalito.topics.repository;

import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TopicRepository extends CrudRepository<Topic, Integer> {

    Iterable<Topic> findByAuthorUsernameOrderByName(String username);

    Optional<Topic> findBySlug(String slug);

    Optional<Topic> findByAuthorAndNameOrderByName(User author, String name);

}
