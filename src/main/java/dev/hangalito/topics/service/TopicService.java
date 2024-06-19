package dev.hangalito.topics.service;

import dev.hangalito.topics.model.Topic;
import dev.hangalito.topics.model.User;
import dev.hangalito.topics.repository.TopicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
class TopicService {

    private final TopicRepository topicRepository;

    public List<Topic> getTopics(User author) {
        List<Topic> topics = new ArrayList<>();
        topicRepository.findByAuthor(author).forEach(topics::add);
        topics.sort(Comparator.comparing(Topic::getName));
        return topics;
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
