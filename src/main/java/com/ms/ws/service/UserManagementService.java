package com.ms.ws.service;

import com.ms.ws.exception.ApplicationException;
import com.ms.ws.model.entity.User;
import com.ms.ws.model.request.SignInRequest;
import com.ms.ws.model.request.SignUpRequest;
import com.ms.ws.model.response.AuthResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;


public interface UserManagementService {

	public ResponseEntity<AuthResponse> signUp(SignUpRequest signupRequest) throws ApplicationException;

	public ResponseEntity<AuthResponse> signIn(SignInRequest signinRequest) throws Exception;

	public String decrypt(String encryptedString);

	public String encrypt(String plainText);

	ResponseEntity<?> getAllUsers();

	List<User> getAll();

}
