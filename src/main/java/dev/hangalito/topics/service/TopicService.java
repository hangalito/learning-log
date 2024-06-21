package dev.hangalito.topics.service;

import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class TopicService {

    private final TopicRepository topicRepository;

    public List<Topic> getTopics(User author) {
        return (List<Topic>) topicRepository.findByAuthorOrderByName(author);
    }

    public Optional<Topic> getTopic(User author, String name) {
        return topicRepository.findByAuthorAndNameOrderByName(author, name);
    }

    public void addTopic(Topic topic) {
        if (topic.getAuthor() == null) {
            throw new IllegalStateException();
        }
        topicRepository.save(topic);
    }

    public void deleteById(int id) {
        topicRepository.deleteById(id);
    }

}
