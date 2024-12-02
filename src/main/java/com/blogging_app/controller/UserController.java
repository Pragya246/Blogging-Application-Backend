package com.blogging_app.controller;

import com.blogging_app.payload.ApiResponse;
import com.blogging_app.payload.UserDto;
import com.blogging_app.services.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/add/user")
	ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
		UserDto userDto1 = userService.createUser(userDto);
		return ResponseEntity.ok(userDto1);
	}

	@PutMapping("/updatuser/{userId}")
	ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable int userId) {
		UserDto userDto1 = userService.updateUser(userDto, userId);
		return new ResponseEntity<>(userDto1, HttpStatus.OK);
	}

	@GetMapping("/getuser/{userId}")
	ResponseEntity<UserDto> getUser(@PathVariable int userId) {
		UserDto userDto = userService.getUserById(userId);
		return ResponseEntity.ok(userDto);
	}

	@GetMapping("/getalluser")
	ResponseEntity<List<UserDto>> getAllUser() {
		List<UserDto> userDtoList = userService.getAllUsers();
		return ResponseEntity.ok(userDtoList);
	}
	@DeleteMapping("/deleteuser/{userId}")
	ResponseEntity<ApiResponse> deleteUser(@PathVariable int userId) {
		userService.deleteUser(userId);
		return ResponseEntity.ok(new ApiResponse(String.format("User with id %d deleted successfully", userId), true));
	}
}
