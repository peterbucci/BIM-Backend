package edu.bhcc.bim.entity;

import java.io.Serializable;
import java.util.Objects;

public class FriendshipId implements Serializable {
    private Integer fromUserId;
    private Integer toUserId;

    // Default constructor, equals, and hashCode methods
    public FriendshipId() {
    }

    public FriendshipId(Integer fromUserId, Integer toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof FriendshipId))
            return false;
        FriendshipId that = (FriendshipId) o;
        return fromUserId.equals(that.fromUserId) && toUserId.equals(that.toUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromUserId, toUserId);
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

    // toString method
    @Override
    public String toString() {
        return "FriendshipId{" +
                "fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                '}';
    }
}
