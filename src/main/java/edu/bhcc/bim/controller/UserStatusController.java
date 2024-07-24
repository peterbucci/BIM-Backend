package edu.bhcc.bim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.bhcc.bim.entity.User;
import edu.bhcc.bim.repository.UserRepository;
import edu.bhcc.bim.entity.UserStatus;
import edu.bhcc.bim.repository.UserStatusRepository;

@RestController
@RequestMapping(path = "/user-status")
public class UserStatusController {
    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/set")
    public @ResponseBody String setUserStatus(@RequestParam Integer userId, @RequestParam String status) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return "User not found: " + userId;
            }

            UserStatus.Status enumStatus = UserStatus.Status.valueOf(status.toUpperCase());
            UserStatus us = userStatusRepository.findByUserId(userId).orElse(new UserStatus());
            us.setUserId(userId); // Ensure userId is set
            us.setUser(user); // Set the user
            us.setStatus(enumStatus);
            us.setLastActive(new java.sql.Timestamp(System.currentTimeMillis())); // Update the lastActive timestamp
            userStatusRepository.save(us);
            return "Status Updated";
        } catch (IllegalArgumentException e) {
            return "Invalid status value: " + status;
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred: " + e.getMessage();
        }
    }

    @GetMapping(path = "/{id}")
    public @ResponseBody UserStatus getUserStatus(@PathVariable Integer id) {
        return userStatusRepository.findById(id).orElse(null);
    }
}
