package edu.bhcc.bim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.bhcc.bim.entity.User;
import edu.bhcc.bim.repository.UserRepository;
import edu.bhcc.bim.repository.UserStatusRepository;
import edu.bhcc.bim.dto.UserDTO;
import edu.bhcc.bim.dto.UserStatusDTO;
import edu.bhcc.bim.entity.UserStatus;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @PostMapping(path = "/add")
    public @ResponseBody UserDTO addNewUser(@RequestParam String username, @RequestParam String passwordHash,
            @RequestParam String email) {
        User n = new User();
        n.setUsername(username);
        n.setPasswordHash(passwordHash);
        n.setEmail(email);
        userRepository.save(n);

        UserStatus s = new UserStatus();
        s.setUserId(n.getUserId());
        s.setStatus(UserStatus.Status.ONLINE);
        userStatusRepository.save(s);

        UserStatusDTO userStatusDTO = new UserStatusDTO(s.getId(), s.getUserId(), s.getStatus(), s.getLastActive());

        return new UserDTO(n.getUserId(), n.getUsername(), userStatusDTO, s.getLastActive());
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            return new UserDTO(user.getUserId(), user.getUsername());
        }
        return null;
    }

    @GetMapping("/authenticate")
    public UserDTO authenticateUser(@RequestParam String username, @RequestParam String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPasswordHash().equals(password)) { // Compare hashed passwords
            return new UserDTO(user.getUserId(), user.getUsername());
        }
        throw new RuntimeException("Invalid username or password");
    }
}
