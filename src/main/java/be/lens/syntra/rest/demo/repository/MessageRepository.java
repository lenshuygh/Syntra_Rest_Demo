package be.lens.syntra.rest.demo.repository;

import be.lens.syntra.rest.demo.domain.Message;

import java.util.List;

public interface MessageRepository {
    Message getMessageById(int id);

    List<Message> getAllMessages();

    List<Message> getAllMessagesByAuthor(String author);

    Message createMessage(Message message);

    Message updateMessage(Message message);

    void deleteMessage(int id);
}
