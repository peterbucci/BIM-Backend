package edu.bhcc.bim.controller;

import edu.bhcc.bim.entity.Conversation;
import edu.bhcc.bim.entity.Message;
import edu.bhcc.bim.entity.User;
import edu.bhcc.bim.repository.ConversationRepository;
import edu.bhcc.bim.repository.MessageRepository;
import edu.bhcc.bim.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping(path = "/messages")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @PostMapping(path = "/send")
    public @ResponseBody String sendMessage(@RequestParam Integer conversationId, @RequestParam Integer userId,
            @RequestParam String content) {
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (conversation == null || user == null) {
            return "Conversation or User not found";
        }

        Message m = new Message();
        m.setConversation(conversation);
        m.setUser(user);
        m.setContent(content);
        messageRepository.save(m);
        return "Sent";
    }

    @GetMapping(path = "/conversation/{id}")
    public @ResponseBody Iterable<Message> getMessagesByConversation(@PathVariable Integer id) {
        Conversation conversation = conversationRepository.findById(id).orElse(null);
        if (conversation == null) {
            return new ArrayList<>(); // Return an empty list or handle error
        }
        return messageRepository.findByConversation(conversation);
    }
}
