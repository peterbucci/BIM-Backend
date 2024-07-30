package edu.bhcc.bim.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer conversationId;

    private String conversationName;

    @Enumerated(EnumType.STRING)
    private ConversationType type;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    public Conversation() {
    }

    public Conversation(String conversationName, ConversationType type) {
        this.conversationName = conversationName;
        this.type = type;
    }

    // Getters and setters
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

    public ConversationType getType() {
        return type;
    }

    public void setType(ConversationType type) {
        this.type = type;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "conversationId=" + conversationId +
                ", conversationName='" + conversationName + '\'' +
                ", type=" + type +
                ", createdAt=" + createdAt +
                '}';
    }

    public enum ConversationType {
        GROUP, ONE_ON_ONE
    }
}
