package dev.hangalito.topics.service;

import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.repository.TopicRepository;
import dev.hangalito.topics.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatusCode.valueOf;

@Service
@AllArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final SlugService     slugService;
    private final UserRepository  userRepository;

    @SneakyThrows
    public Topic getById(int id) {
        return topicRepository.findById(id)
                              .orElseThrow(() -> HttpClientErrorException.NotFound
                                      .create("Could not find the specified topic",
                                              HttpStatusCode.valueOf(404),
                                              "Not Found",
                                              null, null,
                                              StandardCharsets.UTF_8
                                      )
                              );
    }

    public List<Topic> getTopics(User author) {
        return (List<Topic>) topicRepository.findByAuthorOrderByName(author);
    }

    public Topic getBySlug(String slug) {
        return topicRepository.findBySlug(slug)
                              .orElseThrow(() -> HttpClientErrorException.NotFound
                                      .create("Could not find the specified topic",
                                              HttpStatusCode.valueOf(404),
                                              "Not Found",
                                              null, null,
                                              StandardCharsets.UTF_8
                                      )
                              );
    }

    public Optional<Topic> getTopic(User author, String name) {
        return topicRepository.findByAuthorAndNameOrderByName(author, name);
    }

    public void addTopic(Topic topic, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> HttpServerErrorException
                .InternalServerError
                .create(valueOf(404), "Could not determine the user details", null, null, UTF_8)
        );
        topic.setAuthor(user);
        topic.setSlug(slugService.slug(topic.getName()));
        topicRepository.save(topic);
    }

    public void deleteById(Integer id, String username) {
        topicRepository.findById(id)
                       .ifPresent(topic -> {
                           if (topic.getAuthor().getUsername().equals(username)) {
                               topicRepository.delete(topic);
                           }

                       });
    }

}
