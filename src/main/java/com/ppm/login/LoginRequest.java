package com.ppm.login;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {

	@NotBlank(message = "Username can not be blank")
	private String userName;
	@NotBlank(message="Password can not be blank")
	private String password;

}
