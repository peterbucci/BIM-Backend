package edu.bhcc.bim.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserConversationId implements Serializable {
    private Integer userId;
    private Integer conversationId;

    // Default constructor, equals, and hashCode methods
    public UserConversationId() {
    }

    public UserConversationId(Integer userId, Integer conversationId) {
        this.userId = userId;
        this.conversationId = conversationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserConversationId))
            return false;
        UserConversationId that = (UserConversationId) o;
        return userId.equals(that.userId) && conversationId.equals(that.conversationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, conversationId);
    }

    // Getters and setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    // toString method
    @Override
    public String toString() {
        return "UserConversationId{" +
                "userId=" + userId +
                ", conversationId=" + conversationId +
                '}';
    }
}
