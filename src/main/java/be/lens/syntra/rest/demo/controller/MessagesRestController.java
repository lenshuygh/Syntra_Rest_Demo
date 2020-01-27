package be.lens.syntra.rest.demo.controller;

import be.lens.syntra.rest.demo.domain.Message;
import be.lens.syntra.rest.demo.domain.MessageList;
import be.lens.syntra.rest.demo.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@Controller
@RequestMapping(value = "/messages")
public class MessagesRestController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping(value = "{id}")
    public ResponseEntity<Message> getMessage(@PathVariable("id") int id){
        Message message = messageRepository.getMessageById(id);
        if(message != null){
            return new ResponseEntity<>(message, HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<MessageList> getMessages(){
        List<Message> messages = messageRepository.getAllMessages();
        MessageList messageList = new MessageList(messages);
        return new ResponseEntity<>(messageList,HttpStatus.OK);
    }

    @GetMapping(params = {"author"})
    public ResponseEntity<MessageList> getMessagesForAuthor(@RequestParam(value = "author",required = false,defaultValue = "Unknown") String author){
        List<Message> messages = messageRepository.getAllMessagesByAuthor(author);
        MessageList messageList = new MessageList(messages);
        return new ResponseEntity<>(messageList,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Message> addMessage(@RequestBody Message message, HttpServletRequest request){
        if(message.getId() != 0){
            return ResponseEntity.badRequest().build();
        }
        message = messageRepository.createMessage(message);
        URI uri = URI.create(request.getRequestURL()+"/"+message.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "{id:^\\d+$}")
    public ResponseEntity updateMessage(@PathVariable("id") int id,@RequestBody Message message){
        if(message.getId() != id){
            return ResponseEntity.badRequest().build();
        }
        message = messageRepository.updateMessage(message);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "{id:^\\d+$}")
    public ResponseEntity<Message> patchMessage(@PathVariable("id") int id,@RequestBody Message patchMessage){
        Message message = messageRepository.getMessageById(id);
        if(message == null){
            return ResponseEntity.notFound().build();
        }
        if((patchMessage.getId() != 0) && (patchMessage.getId() != id)){
            return ResponseEntity.badRequest().build();
        }
        if(patchMessage.getAuthor() != null){
            message.setAuthor((patchMessage.getAuthor()));
        }
        if( patchMessage.getText() != null){
            message.setText(patchMessage.getText());
        }
        message = messageRepository.updateMessage(message);
        return ResponseEntity.ok(message);
    }

    @DeleteMapping(value = "{id:^\\d+$}")
    public ResponseEntity<?> deleteMessage(@PathVariable("id") int id){
        messageRepository.deleteMessage(id);
        return ResponseEntity.noContent().build();
    }
}
