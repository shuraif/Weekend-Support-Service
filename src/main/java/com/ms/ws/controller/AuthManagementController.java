package com.ms.ws.controller;

import com.ms.ws.exception.ApplicationException;
import com.ms.ws.model.entity.User;
import com.ms.ws.model.request.SignInRequest;
import com.ms.ws.model.request.SignUpRequest;
import com.ms.ws.model.response.AuthResponse;
import com.ms.ws.service.UserManagementService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weekend-support")
public class AuthManagementController {


	@Autowired
	UserManagementService userService;


	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signUp(HttpServletRequest httpRequest, @RequestBody SignUpRequest signupRequest)
			throws ApplicationException {

		return userService.signUp(signupRequest);
	}

	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signIn(HttpServletRequest request, @RequestBody SignInRequest signinRequest)
			throws Exception {

		return userService.signIn(signinRequest);
	}

	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers() {

		return userService.getAllUsers();

	}

	@GetMapping("/team")
	public ResponseEntity<?> getTeamDetails() {
		List<User> teamList = userService.getAll();
		return new ResponseEntity<>(teamList, HttpStatus.OK);
	}

}
