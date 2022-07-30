package com.blog.application.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.application.entities.User;
import com.blog.application.exceptions.ResourceNotFoundException;
import com.blog.application.payloads.UserDto;
import com.blog.application.repositories.UserRepo;
import com.blog.application.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepo UserRepo;

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
		User updateduser = this.UserRepo.save(user);
		return this.userToUserDto(updateduser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.UserRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		User updateduser = this.UserRepo.save(user);
		return this.userToUserDto(updateduser);
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
		User user = new User();
		user.setId(userDto.getId());
		user.setAbout(userDto.getAbout());
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		return user;
	}
	
	private UserDto userToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setAbout(user.getAbout());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPassword(user.getPassword());
		return userDto;
	}

}
