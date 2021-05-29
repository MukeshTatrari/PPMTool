package com.ppm.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsernameAlreadyExistResponse {

	private String userName;

}
