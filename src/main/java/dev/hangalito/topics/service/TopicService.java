package dev.hangalito.topics.service;

import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.repository.TopicRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final SlugService     slugService;
    private final UserService     userService;

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

    public List<Topic> getTopics(String username) {
        return (List<Topic>) topicRepository.findByAuthorUsernameOrderByName(username);
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
        topic.setAuthor(userService.findByUsername(username));
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
