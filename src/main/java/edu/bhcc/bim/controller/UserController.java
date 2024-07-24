package edu.bhcc.bim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.bhcc.bim.entity.User;
import edu.bhcc.bim.repository.UserRepository;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewUser(@RequestParam String username, @RequestParam String passwordHash,
            @RequestParam String email) {
        User n = new User();
        n.setUsername(username);
        n.setPasswordHash(passwordHash);
        n.setEmail(email);
        userRepository.save(n);
        return "Saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
