package edu.bhcc.bim.dto;

import java.sql.Timestamp;

import edu.bhcc.bim.entity.UserStatus;

public class UserStatusDTO {

    private Integer id;
    private Integer userId;
    private UserStatus.Status status;
    private Timestamp lastActive;

    public UserStatusDTO() {
    }

    public UserStatusDTO(Integer id, Integer userId, UserStatus.Status status, Timestamp lastActive) {
        this.id = id;
        this.userId = userId;
        this.status = status;
        this.lastActive = lastActive;
    }

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

    public UserStatus.Status getStatus() {
        return status;
    }

    public void setStatus(UserStatus.Status status) {
        this.status = status;
    }

    public Timestamp getLastActive() {
        return lastActive;
    }

    public void setLastActive(Timestamp lastActive) {
        this.lastActive = lastActive;
    }
}