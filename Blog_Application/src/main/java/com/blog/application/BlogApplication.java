package com.blog.application;

import com.blog.application.config.AppConstants;
import com.blog.application.entities.Role;
import com.blog.application.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BlogApplication implements CommandLineRunner {

	/*
		Tutorial source code
		https://github.com/LearnCodeWithDurgesh/Backend-Course-Blogging-Application
	 */

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(passwordEncoder.encode("hello"));

		try{
			Role adminRole = new Role();
			adminRole.setId(AppConstants.ADMIN_USER_ROLE_ID);
			adminRole.setName("ROLE_ADMIN");

			Role normalRole = new Role();
			normalRole.setId(AppConstants.NORMAL_USER_ROLE_ID);
			normalRole.setName("ROLE_NORMAL");

			List<Role> roles = new ArrayList<>();
			roles.add(adminRole);
			roles.add(normalRole);

			List<Role> savedRoles = roleRepo.saveAll(roles);

			savedRoles.forEach (role -> {
				System.out.println(role.getName() +" : "+role.getId());
			});
		} catch (Exception e){
			System.out.println("Unable To Create User");
			e.printStackTrace();
		}
	}
}
