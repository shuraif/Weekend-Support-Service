package com.ms.ws.mapper;


import com.ms.ws.model.entity.User;
import com.ms.ws.model.request.SignUpRequest;
import com.ms.ws.model.response.AuthResponse;
import com.ms.ws.utils.ApiUtil;
import java.util.Date;

public class UserMapper {

  public static AuthResponse mapUserToAuthResponseMapper(User user) {

    AuthResponse response = new AuthResponse();
    response.setUserId(user.getId());
    response.setLastLogin(user.getLastLogin());
    response.setCreatedOn(user.getCreatedOn());
    response.setName(user.getName());
    response.setEmail(user.getEmail());
    response.setTeam(user.getTeam());

    return response;
  }

  public static User mapAuthRequestToUser(SignUpRequest authRequest) {

    User user = new User();
    user.setEmail(authRequest.getEmail());
    user.setPasswordHash(ApiUtil.encrypt(authRequest.getPassword()));
    user.setCreatedOn(new Date());
    user.setLastLogin(new Date());
    user.setName(authRequest.getName());
    user.setActive(true);
    user.setTeam(authRequest.getTeam());
    return user;
  }

}
