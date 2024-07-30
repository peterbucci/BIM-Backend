package edu.bhcc.bim.controller;

import edu.bhcc.bim.dto.ConversationDTO;
import edu.bhcc.bim.dto.MessageDTO;
import edu.bhcc.bim.dto.UserDTO;
import edu.bhcc.bim.dto.UserStatusDTO;
import edu.bhcc.bim.entity.Conversation;
import edu.bhcc.bim.entity.Conversation.ConversationType;
import edu.bhcc.bim.entity.UserConversation;
import edu.bhcc.bim.entity.UserStatus;
import edu.bhcc.bim.repository.ConversationRepository;
import edu.bhcc.bim.repository.MessageRepository;
import edu.bhcc.bim.repository.UserConversationRepository;
import edu.bhcc.bim.repository.UserStatusRepository;

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

        @Autowired
        private UserStatusRepository userStatusRepository;

        @Autowired
        private MessageRepository messageRepository;

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
                                .filter(userConversation -> userConversation.getConversation()
                                                .getType() == Conversation.ConversationType.ONE_ON_ONE)
                                .map(userConversation -> {
                                        Integer conversationId = userConversation.getConversation().getConversationId();

                                        // Find the other participant
                                        UserDTO otherParticipant = userConversationRepository
                                                        .findByConversationId(conversationId).stream()
                                                        .filter(uc -> !uc.getUserId().equals(userId))
                                                        .map(uc -> new UserDTO(uc.getUser().getUserId(),
                                                                        uc.getUser().getUsername()))
                                                        .findFirst()
                                                        .orElse(null);

                                        UserStatus userStatus = userStatusRepository.findByUserId(
                                                        otherParticipant.getUserId()).orElse(null);

                                        otherParticipant.setLastActive(userStatus.getLastActive());
                                        otherParticipant.setUserStatus(new UserStatusDTO(userStatus.getId(),
                                                        userStatus.getUserId(),
                                                        userStatus.getStatus(), userStatus.getLastActive()));

                                        // Get messages in chronological order
                                        List<MessageDTO> messages = messageRepository
                                                        .findByConversationOrderBySentAtAsc(
                                                                        userConversation.getConversation())
                                                        .stream()
                                                        .map(message -> new MessageDTO(message.getMessageId(),
                                                                        conversationId, message.getContent(),
                                                                        message.getSentAt(),
                                                                        message.getUser().getUsername(),
                                                                        message.getUser().getUserId()))
                                                        .collect(Collectors.toList());

                                        return new ConversationDTO(
                                                        conversationId,
                                                        otherParticipant,
                                                        messages);
                                })
                                .collect(Collectors.toList());
        }
}
