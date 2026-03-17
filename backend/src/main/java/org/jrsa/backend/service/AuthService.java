//package org.jrsa.backend.service;
//
//
//import java.util.Optional;
//
//import org.jrsa.backend.dto.LoginRequest;
//import org.jrsa.backend.dto.RegisterRequest;
//import org.jrsa.backend.entity.User;
//import org.jrsa.backend.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public String register(RegisterRequest req) {
//
//        // check if user already exists
//        Optional<User> existingUser = userRepository.findByEmail(req.getEmail());
//
//        if (existingUser.isPresent()) {
//            return "User already exists";
//        }
//
//        // create new user
//        User user = new User();
//        user.setName(req.getName());
//        user.setEmail(req.getEmail());
//        user.setPassword(req.getPassword());
//
//        userRepository.save(user);
//
//        return "User registered successfully";
//    }
//
//    public String login(LoginRequest req) {
//
//        Optional<User> userOptional = userRepository.findByEmail(req.getEmail());
//
//        if (userOptional.isEmpty()) {
//            return "User not found";
//        }
//
//        User user = userOptional.get();
//
//        if (!user.getPassword().equals(req.getPassword())) {
//            return "Invalid password";
//        }
//
//        return "Login successful";
//    }
//}