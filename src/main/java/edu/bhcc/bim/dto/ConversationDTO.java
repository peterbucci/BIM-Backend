package edu.bhcc.bim.dto;

import java.util.List;

public class ConversationDTO {
    private Integer conversationId;
    private String conversationName;
    private String type;
    private List<UserDTO> participants;

    // Constructors, getters, and setters

    public ConversationDTO() {
    }

    public ConversationDTO(Integer conversationId, String conversationName, String type, List<UserDTO> participants) {
        this.conversationId = conversationId;
        this.conversationName = conversationName;
        this.type = type;
        this.participants = participants;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public String getConversationName() {
        return conversationName;
    }

    public void setConversationName(String conversationName) {
        this.conversationName = conversationName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<UserDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<UserDTO> participants) {
        this.participants = participants;
    }
}
