package edu.bhcc.bim.dto;

import edu.bhcc.bim.common.Status;

public class FriendshipDTO {
    private Integer fromUserId;
    private Integer toUserId;
    private Status status;

    public FriendshipDTO() {
    }

    public FriendshipDTO(Integer fromUserId, Integer toUserId, Status status) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.status = status;
    }

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

    @Override
    public String toString() {
        return "FriendshipDTO{" +
                "fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", status=" + status +
                '}';
    }
}
