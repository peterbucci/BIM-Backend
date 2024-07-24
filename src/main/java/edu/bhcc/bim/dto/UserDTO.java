package edu.bhcc.bim.dto;

public class UserDTO {
    private Integer userId;
    private String username;

    // Constructor, getters, and setters
    public UserDTO() {
    }

    public UserDTO(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
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
}
