package be.lens.syntra.rest.demo.repository;

import be.lens.syntra.rest.demo.domain.Message;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MessageRepositoryMemory implements MessageRepository {
    private Map<Integer, Message> messages = new HashMap<>();

    @PostConstruct
    public void init() {
        Message message = new Message(1, "Homer", "Hello");
        messages.put(message.getId(), message);
    }

    @Override
    public Message getMessageById(int id) {
        return messages.get(id);
    }

    @Override
    public List<Message> getAllMessages() {
        return new ArrayList<>(messages.values());
    }

    @Override
    public List<Message> getAllMessagesByAuthor(String author) {
        return messages.values().stream()
                .filter(m -> m.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    @Override
    public Message createMessage(Message message) {
        if (message.getId() == 0) {
            message.setId(createId());
        }
        messages.put(message.getId(), message);
        return message;
    }

    @Override
    public Message updateMessage(Message message) {
        messages.put(message.getId(), message);
        return message;
    }

    @Override
    public void deleteMessage(int id) {
        messages.remove(id);
    }

    private int createId() {
        OptionalInt max = messages.keySet().stream().mapToInt(Integer::intValue).max();
        return max.isEmpty() ? 1 : max.getAsInt() + 1;
    }
}
