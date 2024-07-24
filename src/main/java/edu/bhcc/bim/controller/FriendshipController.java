package edu.bhcc.bim.controller;

import edu.bhcc.bim.entity.Friendship;
import edu.bhcc.bim.entity.FriendshipId;
import edu.bhcc.bim.entity.User;
import edu.bhcc.bim.entity.UserStatus;
import edu.bhcc.bim.repository.FriendshipRepository;
import edu.bhcc.bim.repository.UserRepository;
import edu.bhcc.bim.repository.UserStatusRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/friendships")
public class FriendshipController {
    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @PostMapping(path = "/request")
    public @ResponseBody String sendFriendRequest(@RequestParam Integer userId1, @RequestParam Integer userId2) {
        Friendship f = new Friendship();
        f.setUserId1(userId1);
        f.setUserId2(userId2);
        f.setStatus(Friendship.Status.PENDING);
        friendshipRepository.save(f);
        return "Friend Request Sent";
    }

    @PostMapping(path = "/accept")
    public @ResponseBody String acceptFriendRequest(@RequestParam Integer userId1, @RequestParam Integer userId2) {
        Friendship f = friendshipRepository.findById(new FriendshipId(userId1, userId2)).orElse(null);
        if (f != null && f.getStatus() == Friendship.Status.PENDING) {
            f.setStatus(Friendship.Status.ACCEPTED);
            friendshipRepository.save(f);
            return "Friend Request Accepted";
        }
        return "Friend Request Not Found or Already Accepted";
    }

    @PostMapping(path = "/block")
    public @ResponseBody String blockUser(@RequestParam Integer userId1, @RequestParam Integer userId2) {
        Friendship f = friendshipRepository.findById(new FriendshipId(userId1, userId2)).orElse(null);
        if (f != null) {
            f.setStatus(Friendship.Status.BLOCKED);
            friendshipRepository.save(f);
            return "User Blocked";
        }
        return "Friendship Not Found";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Friendship> getAllFriendships() {
        return friendshipRepository.findAll();
    }

    @GetMapping("/{userId}")
    public List<FriendDTO> getFriendsWithStatus(@PathVariable Integer userId) {
        List<Friendship> friendships = friendshipRepository.findByUserId1OrUserId2(userId, userId);

        return friendships.stream().map(friendship -> {
            Integer friendId = friendship.getUserId1().equals(userId) ? friendship.getUserId2()
                    : friendship.getUserId1();
            User friend = userRepository.findById(friendId).orElse(null);
            UserStatus status = userStatusRepository.findById(friendId).orElse(null);

            if (friend != null && status != null) {
                return new FriendDTO(friend.getUserId(), friend.getUsername(), status.getStatus(),
                        status.getLastActive());
            }
            return null;
        }).collect(Collectors.toList());
    }

    // DTO class to transfer friend data with status
    public static class FriendDTO {
        private Integer userId;
        private String username;
        private UserStatus.Status status;
        private Timestamp lastActive;

        // Constructor, getters and setters
        public FriendDTO(Integer userId, String username, UserStatus.Status status, Timestamp lastActive) {
            this.userId = userId;
            this.username = username;
            this.status = status;
            this.lastActive = lastActive;
        }

        // Getters and setters
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

        @Override
        public String toString() {
            return "FriendDTO{" +
                    "userId=" + userId +
                    ", username='" + username + '\'' +
                    ", status=" + status +
                    ", lastActive=" + lastActive +
                    '}';
        }
    }
}
