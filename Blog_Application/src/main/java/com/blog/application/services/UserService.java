package com.blog.application.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.blog.application.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Integer id);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteuser(Integer userId);

}
