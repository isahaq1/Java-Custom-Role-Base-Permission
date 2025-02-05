package com.dxerp.ebs.controller;

import com.dxerp.ebs.dto.UserDTO;
import com.dxerp.ebs.entity.User;
import com.dxerp.ebs.service.TokenBlacklistService;
import com.dxerp.ebs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.dxerp.ebs.repository.UserRepository;
import com.dxerp.ebs.security.JwtAuthenticationFilter;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.dxerp.ebs.util.ApiResponse;
import com.dxerp.ebs.util.AuthResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    private  UserRepository userRepository;
    private TokenBlacklistService tokenBlacklistService;

      public UserController(UserService userService, UserRepository userRepository,TokenBlacklistService tokenBlacklistService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody UserDTO userDTO) {
        // userService.registerUser(userDTO);
         ApiResponse<User> response =userService.registerUser(userDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/addUser")
    public ResponseEntity<ApiResponse<User>> adduser(@RequestBody UserDTO userDTO) {
         ApiResponse<User> response =userService.addUser(userDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) {
      
        var u = userRepository.findByUsername(user.getUsername());
        
    if(u == null){
        String token = "";
        AuthResponse authResponse = new AuthResponse(token, "", "false","Invalid Cridential");
        return ResponseEntity.ok(authResponse);
    }
       if(u.isEmailVerified()){
        String token = userService.verify(user);
      
        AuthResponse authResponse = new AuthResponse(token, u, "success","Successfully Loged In");
        return ResponseEntity.ok(authResponse);
       } else{
        String token = "";
      
        AuthResponse authResponse = new AuthResponse(token, user.getUsername(), "false","Email Not verified");
        return ResponseEntity.ok(authResponse);
       }
     
           
        

    }

    @GetMapping("/emailverify")
    public ResponseEntity<String> verifyEmail(@RequestParam String email) {
      

        Optional<User> userOptional = userRepository.findByEmail(email);

    if (userOptional.isPresent()) {
        User user = userOptional.get();
        user.setEmailVerified(true);
        userRepository.save(user);
        return ResponseEntity.ok("Email verified successfully.");
    } else {
        throw new RuntimeException("User not found");
    }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/list")
    public Page<User> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return userService.getAuthUsers(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @ModelAttribute UserDTO userDTO,
    @RequestParam(required = false) MultipartFile profileImage) {
        User updatedUser = userService.updateUser(id, userDTO,profileImage);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7); // Extract token from Bearer token
        tokenBlacklistService.blacklistToken(token); // Blacklist the token

        return ResponseEntity.ok("Logged out successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}