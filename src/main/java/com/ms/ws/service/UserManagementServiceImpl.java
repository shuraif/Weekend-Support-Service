package com.ms.ws.service;


import com.ms.ws.constants.ErrorMessages;
import com.ms.ws.exception.ApplicationException;
import com.ms.ws.mapper.UserMapper;
import com.ms.ws.model.entity.User;
import com.ms.ws.model.request.SignInRequest;
import com.ms.ws.model.request.SignUpRequest;
import com.ms.ws.model.response.AuthResponse;
import com.ms.ws.repo.UserRepo;
import com.ms.ws.repo.WeekendScheduleRepo;
import com.ms.ws.utils.ApiUtil;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserManagementServiceImpl implements UserManagementService {


	@Autowired
	UserRepo userRepo;

	@Autowired
	WeekendScheduleRepo weekendScheduleRepo;
	
	public ResponseEntity<AuthResponse> signUp(SignUpRequest signupRequest) throws ApplicationException {
		
		ResponseEntity<AuthResponse> entity = null;
		AuthResponse response=new AuthResponse();
		
		try {

			User user = userRepo.findByEmail(signupRequest.getEmail());

			if ( user == null) {

				user  = UserMapper.mapAuthRequestToUser(signupRequest);

				user = userRepo.save(user);

				response = UserMapper.mapUserToAuthResponseMapper(user);

				entity = new ResponseEntity<AuthResponse>(response, HttpStatus.OK);
				
			} else {
				throw new ApplicationException(HttpStatus.BAD_REQUEST, ErrorMessages.USER_ALREADY_EXISTS);
			}
		}catch (ApplicationException ae) {
			throw ae;
		}catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR,ErrorMessages.SOMETHING_WENT_WRONG);
		}
		
		return entity;
	}

	public ResponseEntity<AuthResponse> signIn(SignInRequest signinRequest) throws Exception {
		
		ResponseEntity<AuthResponse> entity = null;
		AuthResponse response = new AuthResponse();
		try {
			
			User user = userRepo.findByEmail(signinRequest.getEmail());
					
			if(null != user && user.getPasswordHash().equals(ApiUtil.encrypt(signinRequest.getPassword()))) {

					response = UserMapper.mapUserToAuthResponseMapper(user);
					entity=new ResponseEntity<AuthResponse>(response, HttpStatus.OK);

					user.setLastLogin(new Date());
					userRepo.save(user);

			}else {
				throw new ApplicationException(HttpStatus.BAD_REQUEST,ErrorMessages.INVALID_CREDENTIALS);
			}

		}catch (ApplicationException ae) {
			throw ae;
		}
		catch (Exception e) {
			
			throw new ApplicationException(HttpStatus.INTERNAL_SERVER_ERROR,ErrorMessages.SOMETHING_WENT_WRONG);
		}

		return entity;
	}

	public String decrypt(String encryptedString) {
		
		return ApiUtil.decrypt(encryptedString);
	}

	public String encrypt(String plainText) {
	
			return ApiUtil.encrypt(plainText);
		
	}

	@Override
	public ResponseEntity<?> getAllUsers() {

		List<User> users = userRepo.findAll();
		List<AuthResponse> responseList = users.stream()
				.map(UserMapper::mapUserToAuthResponseMapper)
				.toList();

		return new ResponseEntity<>(responseList, HttpStatus.OK);
	}


	@Override
	public List<User> getAll() {
		return userRepo.findAll();
	}



}
