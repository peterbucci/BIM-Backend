package edu.bhcc.bim.controller;

import edu.bhcc.bim.dto.ConversationDTO;
import edu.bhcc.bim.dto.UserDTO;
import edu.bhcc.bim.entity.Conversation;
import edu.bhcc.bim.entity.Conversation.ConversationType;
import edu.bhcc.bim.entity.UserConversation;
import edu.bhcc.bim.repository.ConversationRepository;
import edu.bhcc.bim.repository.UserConversationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/conversations")
public class ConversationController {
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserConversationRepository userConversationRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewConversation(@RequestParam String conversationName,
            @RequestParam ConversationType type) {
        Conversation c = new Conversation();
        c.setConversationName(conversationName);
        c.setType(type);
        conversationRepository.save(c);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Conversation> getAllConversations() {
        return conversationRepository.findAll();
    }

    @GetMapping("/{userId}")
    public List<ConversationDTO> getConversationsByUserId(@PathVariable Integer userId) {
        List<UserConversation> userConversations = userConversationRepository.findByUserId(userId);

        return userConversations.stream()
                .map(userConversation -> {
                    List<UserDTO> participants = userConversationRepository
                            .findByConversationId(userConversation.getConversationId())
                            .stream()
                            .map(uc -> new UserDTO(uc.getUser().getUserId(), uc.getUser().getUsername()))
                            .collect(Collectors.toList());
                    return new ConversationDTO(
                            userConversation.getConversation().getConversationId(),
                            userConversation.getConversation().getConversationName(),
                            userConversation.getConversation().getType().name(),
                            participants);
                })
                .collect(Collectors.toList());
    }
}
