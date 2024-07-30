package edu.bhcc.bim.controller;

import edu.bhcc.bim.entity.Friendship;
import edu.bhcc.bim.entity.FriendshipId;
import edu.bhcc.bim.dto.FriendshipDTO;
import edu.bhcc.bim.dto.UserDTO;
import edu.bhcc.bim.dto.UserStatusDTO;
import edu.bhcc.bim.entity.User;
import edu.bhcc.bim.entity.UserStatus;
import edu.bhcc.bim.repository.FriendshipRepository;
import edu.bhcc.bim.repository.UserRepository;
import edu.bhcc.bim.repository.UserStatusRepository;
import edu.bhcc.bim.common.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public @ResponseBody String sendFriendRequest(@RequestParam Integer fromUserId, @RequestParam Integer toUserId) {
        Friendship f = new Friendship();
        f.setFromUserId(fromUserId);
        f.setToUserId(toUserId);
        f.setStatus(Status.PENDING);
        friendshipRepository.save(f);
        return "Friend Request Sent";
    }

    @PostMapping(path = "/accept")
    public @ResponseBody String acceptFriendRequest(@RequestParam Integer fromUserId, @RequestParam Integer toUserId) {
        Friendship f = friendshipRepository.findById(new FriendshipId(fromUserId, toUserId)).orElse(null);
        if (f != null && f.getStatus() == Status.PENDING) {
            f.setStatus(Status.ACCEPTED);
            friendshipRepository.save(f);
            return "Friend Request Accepted";
        }
        return "Friend Request Not Found or Already Accepted";
    }

    @PostMapping(path = "/block")
    public @ResponseBody String blockUser(@RequestParam Integer fromUserId, @RequestParam Integer toUserId) {
        Friendship f = friendshipRepository.findById(new FriendshipId(fromUserId, toUserId)).orElse(null);
        if (f != null) {
            f.setStatus(Status.BLOCKED);
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
    public List<UserDTO> getFriendsWithStatus(@PathVariable Integer userId) {
        List<Friendship> friendships = friendshipRepository.findByFromUserIdOrToUserId(userId, userId);

        return friendships.stream().map(friendship -> {
            Integer friendId = friendship.getFromUserId().equals(userId) ? friendship.getToUserId()
                    : friendship.getFromUserId();
            User friend = userRepository.findById(friendId).orElse(null);
            UserStatus userStatus = userStatusRepository.findByUserId(friendId).orElse(null);

            if (friend != null && userStatus != null) {
                UserStatusDTO userStatusDTO = new UserStatusDTO(userStatus.getId(), userStatus.getUserId(),
                        userStatus.getStatus(), userStatus.getLastActive());
                UserDTO userDTO = new UserDTO(friend.getUserId(), friend.getUsername(), userStatusDTO,
                        userStatus.getLastActive(),
                        new FriendshipDTO(friendship.getFromUserId(), friendship.getToUserId(),
                                friendship.getStatus()));

                System.out.println("UserDTO99 " + userDTO);
                return userDTO;
            }

            return null;
        }).collect(Collectors.toList());
    }
}
