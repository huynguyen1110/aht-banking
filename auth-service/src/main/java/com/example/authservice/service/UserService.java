package com.example.authservice.service;

import com.example.authservice.dto.UserRequest;
import com.example.authservice.dto.UserResponse;
import com.example.authservice.exception.DuplicatedException;
import com.example.authservice.exception.NotFoundException;
import com.example.authservice.mapper.UserMapper;
import com.example.authservice.model.Role;
import com.example.authservice.model.User;
import com.example.authservice.repository.RoleRepository;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest userRequest){

        if(userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicatedException(Constants.ERROR_CODE.USERNAME_ALREADY_EXISTED, userRequest.getUsername());
        }

        if(userRepository.existsByEmail(userRequest.getEmail())){
            throw new DuplicatedException(Constants.ERROR_CODE.EMAIL_ALREADY_EXISTED, userRequest.getEmail());
        }

        User user = User.builder()
                .email(userRequest.getEmail())
                .username(userRequest.getUsername())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .fullName(userRequest.getFullName())
                .createdAt(new Date())
                .build();
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Constants.ROLE_USER));
        user.setRoles(roles);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> getUsers(){
        List<User> users = userRepository.findAll();
        List<UserResponse> results = new ArrayList<>();
        users.forEach(user -> results.add(userMapper.toUserResponse(user)));
        return results;
    }

    public UserResponse update(UserRequest userRequest, Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, id)
        );
        user.setEmail(userRequest.getEmail());
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setFullName(userRequest.getFullName());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public void delete(Long id){
        User user = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, id)
        );
        userRepository.delete(user);
    }

    public Set<Role> getRoleUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, userId));
        return user.getRoles();
    }

    public void assignRole(Long userId, Long roleId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, userId));
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new NotFoundException(Constants.ERROR_CODE.ROLE_NOT_FOUND, roleId));
        try {
            Set<Role> roles = user.getRoles();
            roles.add(role);
        } catch (Exception e){
            e.printStackTrace();
        }
        userRepository.save(user);
    }

    public void revokeRole(Long userId, Long roleId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(Constants.ERROR_CODE.USER_NOT_FOUND, userId));
        Role role = roleRepository.findById(roleId).orElseThrow(
                () -> new NotFoundException(Constants.ERROR_CODE.ROLE_NOT_FOUND, roleId));
        Set<Role> roles = user.getRoles();
        if (!roles.removeIf(r -> r.equals(role))) {
            throw new NotFoundException(Constants.ERROR_CODE.ROLE_USER_NOT_FOUND, role.getRoleId());
        }
        userRepository.save(user);
    }

    public boolean existUserById(Long userId) {
        return userRepository.existsById(userId);
    }
}
