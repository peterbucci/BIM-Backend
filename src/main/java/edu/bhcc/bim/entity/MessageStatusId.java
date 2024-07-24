package edu.bhcc.bim.entity;

import java.io.Serializable;
import java.util.Objects;

public class MessageStatusId implements Serializable {
    private Integer messageId;
    private Integer userId;

    // Default constructor, equals, and hashCode methods
    public MessageStatusId() {
    }

    public MessageStatusId(Integer messageId, Integer userId) {
        this.messageId = messageId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MessageStatusId))
            return false;
        MessageStatusId that = (MessageStatusId) o;
        return messageId.equals(that.messageId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, userId);
    }

    // Getters and setters
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    // toString method
    @Override
    public String toString() {
        return "MessageStatusId{" +
                "messageId=" + messageId +
                ", userId=" + userId +
                '}';
    }
}
