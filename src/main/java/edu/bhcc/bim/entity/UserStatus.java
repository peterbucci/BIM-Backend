package edu.bhcc.bim.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId", insertable = false, updatable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Timestamp lastActive;

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getLastActive() {
        return lastActive;
    }

    public void setLastActive(Timestamp lastActive) {
        this.lastActive = lastActive;
    }

    // toString method
    @Override
    public String toString() {
        return "UserStatus{" +
                "id=" + id +
                ", userId=" + userId +
                ", user=" + user +
                ", status=" + status +
                ", lastActive=" + lastActive +
                '}';
    }

    public enum Status {
        ONLINE, OFFLINE, AWAY, BUSY
    }
}
