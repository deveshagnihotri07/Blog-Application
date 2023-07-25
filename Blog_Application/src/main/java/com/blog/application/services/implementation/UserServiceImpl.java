package com.blog.application.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import com.blog.application.config.AppConstants;
import com.blog.application.entities.Role;
import com.blog.application.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.application.entities.User;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.UserDto;
import com.blog.application.repositories.UserRepo;
import com.blog.application.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo UserRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto registerUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);

		//Encode Password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		//Roles
		Role role = this.roleRepo.findById(AppConstants.ADMIN_USER_ROLE_ID).get();
		user.getRoles().add(role);

		User newUser = UserRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User savedUser = this.UserRepo.save(user);
		return userToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
		User user = this.UserRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
		user.setAbout(userDto.getAbout());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		User updatedUser = this.UserRepo.save(user);
		return this.userToUserDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.UserRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		User updatedUser = this.UserRepo.save(user);
		return this.userToUserDto(updatedUser);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users= this.UserRepo.findAll();
		List<UserDto> usersDtos = users.stream()
				.map(user -> this.userToUserDto(user)).collect(Collectors.toList());
		return usersDtos;
	}

	@Override
	public void deleteuser(Integer userId) {
		User user = this.UserRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		this.UserRepo.delete(user);
		
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setAbout(userDto.getAbout());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
		return user;
	}
	
	private UserDto userToUserDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
