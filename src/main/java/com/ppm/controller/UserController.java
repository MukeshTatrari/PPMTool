package com.ppm.controller;

import static com.ppm.security.SecurityConstants.TOKEN_PREFIX;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ppm.domain.User;
import com.ppm.login.JWTLoginSuccessResponse;
import com.ppm.login.LoginRequest;
import com.ppm.security.JwtTokenProvider;
import com.ppm.service.MapValidationErrorSevice;
import com.ppm.service.UserService;
import com.ppm.validators.UserValidator;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

	private final MapValidationErrorSevice validationService;
	private final UserService userService;
	private final UserValidator userValidator;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {

		userValidator.validate(user, result);

		ResponseEntity<?> errors = validationService.mapValidations(result);
		if (errors != null) {
			return errors;
		}

		return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest request, BindingResult result) {
		ResponseEntity<?> errors = validationService.mapValidations(result);
		if (errors != null) {
			return errors;
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);

		return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
	}

	@GetMapping("/{userName}")
	public ResponseEntity<?> getAllUsers(@PathVariable String userName) {

		return ResponseEntity.ok(userService.saveUser(userName));
	}

}
