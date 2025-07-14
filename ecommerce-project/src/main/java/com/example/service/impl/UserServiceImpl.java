package com.example.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.entity.User;
import com.example.model.UserRequest;
import com.example.model.UserResponse;
import com.example.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.example.service.UserService;


@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Override
	public UserResponse registerUser(UserRequest request) {
		User user = User.builder()
				.username(request.getUsername())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(User.Role.CUSTOMER)
				.build();
		user = userRepository.save(user);
		return mapToResponse(user);
	}
	
	@Override
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponse(user);
    }

	@Override
	public UserResponse updateUser(Long userId, UserRequest request) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		
		user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
        	user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        
		return mapToResponse(userRepository.save(user));
	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub
		userRepository.deleteById(userId);
	}
	
	private UserResponse mapToResponse(User user) {
		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        return response;
	}

	@Override
	public List<UserResponse> getAllUsers() {
		return userRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.collect(Collectors.toList());
	}
}
