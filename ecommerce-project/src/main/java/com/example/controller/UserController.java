package com.example.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.UserRequest;
import com.example.model.UserResponse;
import com.example.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest request) {
		UserResponse userResponse = userService.registerUser(request);
		return ResponseEntity.ok(userResponse);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserResponse> updateUser(@PathVariable Long userId, @Valid @RequestBody UserRequest request) {
		UserResponse updatedUser = userService.updateUser(userId, request);
		return ResponseEntity.ok(updatedUser);
	}
	
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers() {
	    return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/fetch-user/{userId}")
	public ResponseEntity<UserResponse> getUserById(@PathVariable Long userId) {
		UserResponse user = userService.getUserById(userId);
		return ResponseEntity.ok(user);
	}
}
