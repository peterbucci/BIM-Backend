package edu.bhcc.bim.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import edu.bhcc.bim.common.Status;

@Entity
@IdClass(FriendshipId.class)
public class Friendship {
    @Id
    @Column(name = "from_user_id")
    private Integer fromUserId;

    @Id
    @Column(name = "to_user_id")
    private Integer toUserId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp requestedAt;

    // Getters and setters
    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
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

    @Override
    public String toString() {
        return "Friendship{" +
                "fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", status=" + status +
                ", requestedAt=" + requestedAt +
                '}';
    }
}
