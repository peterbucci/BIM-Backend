package edu.bhcc.bim.dto;

import java.util.ArrayList;
import java.util.List;

public class ConversationDTO {
    private Integer conversationId;
    private UserDTO participant;
    private List<MessageDTO> messages;

    public ConversationDTO() {
        this.messages = new ArrayList<>(); // Initialize to an empty list
    }

    public ConversationDTO(Integer conversationId, UserDTO participant, List<MessageDTO> messages) {
        this.conversationId = conversationId;
        this.participant = participant;
        this.messages = messages;
    }

    // Getters and setters
    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public UserDTO getParticipant() {
        return participant;
    }

    public void setParticipant(UserDTO participant) {
        this.participant = participant;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
