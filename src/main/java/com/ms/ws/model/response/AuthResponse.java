package com.ms.ws.model.response;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthResponse {

	private String userId;
	private Date lastLogin;
	private Date createdOn;
	private String name;
	private String email;
	private String team;

}
