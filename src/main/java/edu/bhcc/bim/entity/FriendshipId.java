package edu.bhcc.bim.entity;

import java.io.Serializable;
import java.util.Objects;

public class FriendshipId implements Serializable {
    private Integer userId1;
    private Integer userId2;

    // Default constructor, equals, and hashCode methods
    public FriendshipId() {
    }

    public FriendshipId(Integer userId1, Integer userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof FriendshipId))
            return false;
        FriendshipId that = (FriendshipId) o;
        return userId1.equals(that.userId1) && userId2.equals(that.userId2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId1, userId2);
    }

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

    // toString method
    @Override
    public String toString() {
        return "FriendshipId{" +
                "userId1=" + userId1 +
                ", userId2=" + userId2 +
                '}';
    }
}
