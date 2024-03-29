package com.example.authservice.controller;

import com.example.authservice.dto.UserRequest;
import com.example.authservice.dto.UserResponse;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.model.Role;
import com.example.authservice.dto.UserResponseDTO;
import com.example.authservice.exception.UserCanNotFoundException;
import com.example.authservice.service.UserDetailsServiceImpl;
import com.example.authservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest){
        return ResponseEntity.ok().body(userService.create(userRequest));
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest userRequest, @PathVariable Long id){
        return ResponseEntity.ok().body(userService.update(userRequest, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.delete(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping("/role/{userId}")
    public ResponseEntity<Set<Role>> getRoleUser(@PathVariable Long userId){
        return ResponseEntity.ok().body(userService.getRoleUser(userId));
    }

    @PostMapping("/role/assign/{userId}")
    public ResponseEntity<String> assignRole(@PathVariable Long userId, @RequestBody Role role){
        userService.assignRole(userId, role.getRoleId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/role/revoke/{userId}")
    public ResponseEntity<String> revoke(@PathVariable Long userId, @RequestBody Role role){
        userService.revokeRole(userId, role.getRoleId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username){
        UserResponse userResponse = userMapper.toUserResponse(userDetailsService.loadUserByUsername(username));
        return ResponseEntity.ok().body(userResponse);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        final String jwt = ((Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getTokenValue();
        System.out.println(jwt);
        Map<String, Object> result = new HashMap<>();
        result.put("data", "Authorization server: " + userId + " token: " + jwt);
        return ResponseEntity.ok().body(result);
    }

//    @GetMapping("user/{userId}")
//    public ResponseEntity<?> getUserById(@PathVariable Long userId) throws UserCanNotFoundException {
//        UserResponseDTO user = userDetailsService.getUserById(userId);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

    @GetMapping("/exist-user")
    public boolean existUser(@RequestParam("userId") Long userId) {
        return userService.existUserById(userId);
    }
}
