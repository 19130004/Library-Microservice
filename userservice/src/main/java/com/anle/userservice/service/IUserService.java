package com.anle.userservice.service;

import java.util.List;

import com.anle.userservice.data.Role;
import com.anle.userservice.data.User;
import com.anle.userservice.model.UserDTO;

public interface IUserService {
    List<User> getAllUser();

    List<Role> getAllRole();

    UserDTO saveUser(UserDTO userDTO);

    Role saveRole(Role role);

    void addRole(String username, String roleName);

    UserDTO login(String username, String password);

    UserDTO validateToken(String token);
}