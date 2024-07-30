package edu.bhcc.bim.controller;

import edu.bhcc.bim.dto.MessageDTO;
import edu.bhcc.bim.dto.UserDTO;
import edu.bhcc.bim.dto.UserStatusDTO;
import edu.bhcc.bim.entity.Message;
import edu.bhcc.bim.entity.Friendship;
import edu.bhcc.bim.dto.ConversationDTO;
import edu.bhcc.bim.dto.FriendshipDTO;
import edu.bhcc.bim.common.Status;
import edu.bhcc.bim.entity.Conversation;
import edu.bhcc.bim.entity.User;
import edu.bhcc.bim.entity.UserConversation;
import edu.bhcc.bim.entity.UserStatus;
import edu.bhcc.bim.repository.ConversationRepository;
import edu.bhcc.bim.repository.UserConversationRepository;
import edu.bhcc.bim.repository.FriendshipRepository;
import edu.bhcc.bim.repository.MessageRepository;
import edu.bhcc.bim.repository.UserRepository;
import edu.bhcc.bim.repository.UserStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserConversationRepository userConversationRepository;

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @MessageMapping("/message/{userId}")
    @SendTo("/topic/messages/{userId}")
    public MessageDTO sendMessage(@DestinationVariable Integer userId, @RequestBody MessageDTO messageDTO) {
        // Retrieve the user and conversation
        User user = userRepository.findById(messageDTO.getSenderId()).orElse(null);
        Conversation conversation = conversationRepository.findById(messageDTO.getConversationId()).orElse(null);

        if (user == null || conversation == null) {
            throw new RuntimeException("User or Conversation not found");
        }

        // Create a new message entity and save it
        Message message = new Message();
        message.setUser(user);
        message.setConversation(conversation);
        message.setContent(messageDTO.getContent());
        messageRepository.save(message);

        // Return the MessageDTO
        return new MessageDTO(message.getMessageId(), message.getConversation().getConversationId(),
                message.getContent(), message.getSentAt(), message.getUser().getUsername(),
                message.getUser().getUserId());
    }

    @MessageMapping("/user/status/{userId}")
    @SendTo("/topic/user/status/{userId}")
    public UserStatusDTO updateUserStatus(@DestinationVariable Integer userId, @RequestBody UserStatus.Status status) {
        UserStatus userStatus = userStatusRepository.findByUserId(userId).orElse(null);

        if (userStatus == null) {
            userStatus = new UserStatus();
            userStatus.setUserId(userId);
        }

        userStatus.setStatus(status);
        userStatusRepository.save(userStatus);
        logger.info("User {} is now {}", userId, userStatus.getStatus());
        return new UserStatusDTO(userStatus.getId(), userId, status, userStatus.getLastActive());
    }

    @MessageMapping("/friend/request/{username}")
    @SendTo("/topic/friend/requests/{username}")
    public UserDTO handleFriendRequest(@DestinationVariable String username,
            @RequestBody FriendshipDTO friendshipDTO) {
        User userInPath = userRepository.findByUsername(username);

        if (userInPath == null) {
            throw new RuntimeException("User not found");
        }

        if (friendshipDTO.getStatus() == Status.PENDING) {
            return handleSendFriendRequest(userInPath, friendshipDTO);
        } else if (friendshipDTO.getStatus() == Status.ACCEPTED) {
            return handleAcceptFriendRequest(userInPath, friendshipDTO);
        }

        throw new IllegalArgumentException("Invalid status");
    }

    private UserDTO handleSendFriendRequest(User toUser, FriendshipDTO friendshipDTO) {
        friendshipDTO.setToUserId(toUser.getUserId());
        User fromUser = userRepository.findById(friendshipDTO.getFromUserId()).orElse(null);

        if (fromUser == null) {
            throw new RuntimeException("From user not found");
        }

        Friendship friendship = friendshipRepository.findByFromUserIdAndToUserId(fromUser.getUserId(),
                toUser.getUserId());

        if (friendship == null) {
            friendship = new Friendship();
            friendship.setFromUserId(fromUser.getUserId());
            friendship.setToUserId(toUser.getUserId());
            friendship.setStatus(Status.PENDING);
            friendshipRepository.save(friendship);
        }

        UserStatus toUserStatus = userStatusRepository.findByUserId(toUser.getUserId()).orElse(null);
        UserStatus fromUserStatus = userStatusRepository.findByUserId(fromUser.getUserId()).orElse(null);

        if (toUserStatus == null || fromUserStatus == null) {
            throw new RuntimeException("User status not found");
        }

        UserStatusDTO toUserStatusDTO = new UserStatusDTO(toUserStatus.getId(), toUser.getUserId(),
                toUserStatus.getStatus(), toUserStatus.getLastActive());
        UserDTO toUserDTO = new UserDTO(toUser.getUserId(), toUser.getUsername(),
                toUserStatusDTO, toUserStatus.getLastActive(),
                friendshipDTO);

        UserStatusDTO fromUserStatusDTO = new UserStatusDTO(fromUserStatus.getId(), fromUser.getUserId(),
                fromUserStatus.getStatus(),
                fromUserStatus.getLastActive());
        UserDTO fromUserDTO = new UserDTO(fromUser.getUserId(), fromUser.getUsername(),
                fromUserStatusDTO, fromUserStatus.getLastActive(),
                friendshipDTO);

        messagingTemplate.convertAndSend("/topic/friend/requests/" + fromUser.getUsername(), toUserDTO);

        return fromUserDTO;
    }

    private UserDTO handleAcceptFriendRequest(User fromUser, FriendshipDTO friendshipDTO) {
        User toUser = userRepository.findById(friendshipDTO.getToUserId()).orElse(null);
        Friendship friendship = friendshipRepository.findByFromUserIdAndToUserId(fromUser.getUserId(),
                toUser.getUserId());

        if (toUser == null || friendship == null) {
            throw new RuntimeException("Friend request not found");
        }

        Conversation conversation = new Conversation("General", Conversation.ConversationType.ONE_ON_ONE);
        conversationRepository.save(conversation);

        createUserConversation(fromUser, conversation);
        createUserConversation(toUser, conversation);

        friendship.setStatus(friendshipDTO.getStatus());
        friendshipRepository.save(friendship);

        UserStatus toUserStatus = userStatusRepository.findByUserId(toUser.getUserId()).orElse(null);
        UserStatus fromUserStatus = userStatusRepository.findByUserId(fromUser.getUserId()).orElse(null);

        if (toUserStatus == null || fromUserStatus == null) {
            throw new RuntimeException("User status not found");
        }

        UserStatusDTO toUserStatusDTO = new UserStatusDTO(toUserStatus.getId(), toUser.getUserId(),
                toUserStatus.getStatus(), toUserStatus.getLastActive());
        UserDTO toUserDTO = new UserDTO(toUser.getUserId(), toUser.getUsername(),
                toUserStatusDTO, toUserStatus.getLastActive(),
                friendshipDTO);

        UserStatusDTO fromUserStatusDTO = new UserStatusDTO(fromUserStatus.getId(), fromUser.getUserId(),
                fromUserStatus.getStatus(),
                fromUserStatus.getLastActive());
        UserDTO fromUserDTO = new UserDTO(fromUser.getUserId(), fromUser.getUsername(),
                fromUserStatusDTO, fromUserStatus.getLastActive(),
                friendshipDTO);

        ConversationDTO toConversationDTO = new ConversationDTO();
        toConversationDTO.setConversationId(conversation.getConversationId());
        toConversationDTO.setParticipant(fromUserDTO);

        ConversationDTO fromConversationDTO = new ConversationDTO();
        fromConversationDTO.setConversationId(conversation.getConversationId());
        fromConversationDTO.setParticipant(toUserDTO);

        messagingTemplate.convertAndSend("/topic/conversations/" + toUser.getUserId(), toConversationDTO);
        messagingTemplate.convertAndSend("/topic/conversations/" + fromUser.getUserId(), fromConversationDTO);
        messagingTemplate.convertAndSend("/topic/friend/requests/" + toUser.getUsername(), fromUserDTO);

        return toUserDTO;
    }

    private void createUserConversation(User user, Conversation conversation) {
        UserConversation userConversation = new UserConversation();
        userConversation.setUser(user);
        userConversation.setUserId(user.getUserId());
        userConversation.setConversation(conversation);
        userConversation.setConversationId(conversation.getConversationId());
        userConversationRepository.save(userConversation);
    }
}
