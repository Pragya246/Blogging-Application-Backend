package com.blogging_app.services;

import com.blogging_app.payload.UserDto;
import java.util.List;

public interface UserService {
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto,Integer userId);
	void deleteUser(Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
    String isValidUser(UserDto userDto);
}
