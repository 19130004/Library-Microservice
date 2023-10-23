package com.anle.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anle.userservice.data.User;
import com.anle.userservice.model.UserDTO;
import com.anle.userservice.service.IUserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping("/listUser")
	public List<User> getAllUser() {
		return userService.getAllUser();
	}

	@PostMapping("/addUser")
	public UserDTO addUser(@RequestBody UserDTO userDTO) {
		return userService.saveUser(userDTO);
	}
	@PostMapping("/login")
	public UserDTO login(@RequestBody UserDTO dto) {
		return userService.login(dto.getUsername(), dto.getPassword());
	}

}
