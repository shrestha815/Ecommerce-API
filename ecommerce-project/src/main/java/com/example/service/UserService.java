package com.example.service;

import java.util.List;

import com.example.model.UserRequest;
import com.example.model.UserResponse;

public interface UserService {
	UserResponse registerUser(UserRequest request);
	UserResponse updateUser(Long userId, UserRequest request);
	UserResponse getUserById(Long userId);
	List<UserResponse> getAllUsers();
	void deleteUser(Long userId);
}
