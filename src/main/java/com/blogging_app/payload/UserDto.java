package com.blogging_app.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {
	private int uid;

	@NotEmpty
	@Size(min = 2, max = 50, message = "Name should be of atleast 2 characters.")
	private String name;

	@NotEmpty
	private String about;

	@Email
	private String email;

	@NotEmpty
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password should be atleast 8 chars, Contains at least one digit, one lower alpha char and one upper alpha char, Does not contain space.")
	private String password;
}
