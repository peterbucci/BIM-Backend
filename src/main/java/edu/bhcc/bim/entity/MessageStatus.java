package edu.bhcc.bim.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@IdClass(MessageStatusId.class)
public class MessageStatus {
    @Id
    private Integer messageId;

    @Id
    private Integer userId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedAt;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // toString method
    @Override
    public String toString() {
        return "MessageStatus{" +
                "messageId=" + messageId +
                ", userId=" + userId +
                ", status=" + status +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public enum Status {
        SENT, DELIVERED, READ
    }
}
