package edu.bhcc.bim.controller;

import edu.bhcc.bim.entity.User;
import edu.bhcc.bim.entity.UserConversation;
import edu.bhcc.bim.entity.Conversation;
import edu.bhcc.bim.repository.ConversationRepository;
import edu.bhcc.bim.repository.UserConversationRepository;
import edu.bhcc.bim.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;

@RestController
@RequestMapping(path = "/user-conversations")
public class UserConversationController {
    @Autowired
    private UserConversationRepository userConversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addUserToConversation(@RequestParam Integer userId,
            @RequestParam Integer conversationId) {
        User user = userRepository.findById(userId).orElse(null);
        Conversation conversation = conversationRepository.findById(conversationId).orElse(null);

        if (user == null || conversation == null) {
            return "User or Conversation not found";
        }

        UserConversation uc = new UserConversation();
        uc.setUser(user);
        uc.setConversation(conversation);
        uc.setAddedAt(new Timestamp(System.currentTimeMillis()));
        userConversationRepository.save(uc);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UserConversation> getAllUserConversations() {
        return userConversationRepository.findAll();
    }
}
