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

    public void addTopic(Topic topic) {
        if (topic.getAuthor() == null) {
            throw new IllegalStateException();
        }
        topic.setSlug(slugService.slug(topic.getName()));
        topicRepository.save(topic);
    }

    public void deleteById(int id) {
        topicRepository.deleteById(id);
    }

}
