package edu.bhcc.bim.dto;

import java.sql.Timestamp;

public class UserDTO {
    private Integer userId;
    private String username;
    private UserStatusDTO userStatus;
    private Timestamp lastActive;
    private FriendshipDTO friendship;

    // Constructor, getters, and setters
    public UserDTO() {
    }

    public UserDTO(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    public UserDTO(Integer userId, String username, UserStatusDTO userStatus, Timestamp lastActive) {
        this(userId, username, userStatus, lastActive, null);
    }

    public UserDTO(Integer userId, String username, UserStatusDTO userStatus, Timestamp lastActive,
            FriendshipDTO friendship) {
        this.userId = userId;
        this.username = username;
        this.userStatus = userStatus;
        this.lastActive = lastActive;
        this.friendship = friendship;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserStatusDTO getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatusDTO userStatus) {
        this.userStatus = userStatus;
    }

    public Timestamp getLastActive() {
        return lastActive;
    }

    public void setLastActive(Timestamp lastActive) {
        this.lastActive = lastActive;
    }

    public FriendshipDTO getFriendship() {
        return friendship;
    }

    public void setFriendship(FriendshipDTO friendship) {
        this.friendship = friendship;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", userStatus=" + userStatus +
                ", lastActive=" + lastActive +
                ", friendship=" + friendship +
                '}';
    }
}
