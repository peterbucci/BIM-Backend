package edu.bhcc.bim.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@IdClass(FriendshipId.class)
public class Friendship {
    @Id
    private Integer userId1;

    @Id
    private Integer userId2;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp requestedAt;

    // Getters and setters
    public Integer getUserId1() {
        return userId1;
    }

    public void setUserId1(Integer userId1) {
        this.userId1 = userId1;
    }

    public Integer getUserId2() {
        return userId2;
    }

    public void setUserId2(Integer userId2) {
        this.userId2 = userId2;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Timestamp requestedAt) {
        this.requestedAt = requestedAt;
    }

    // toString method
    @Override
    public String toString() {
        return "Friendship{" +
                "userId1=" + userId1 +
                ", userId2=" + userId2 +
                ", status=" + status +
                ", requestedAt=" + requestedAt +
                '}';
    }

    public enum Status {
        PENDING, ACCEPTED, BLOCKED
    }
}
