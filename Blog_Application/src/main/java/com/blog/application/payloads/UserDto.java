package com.blog.application.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;

	@NotEmpty
    @Size(min = 4, max = 15, message = "Username length should be 4 to 15 !!")
	private String name;

	@Email(message = "Email is not valid !!")
	private String email;

	@NotEmpty
	@Size(min = 8, max = 15, message = "Password length should be 8 to 15 !!")
	private String password;

	@NotEmpty
	@Size(max = 200, message = "Max length is 200 char !!")
	private String about;

}
