package com.ms.ws.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignUpRequest {

	private String password;
	private String name;
	private String email;
	private String team;

}
