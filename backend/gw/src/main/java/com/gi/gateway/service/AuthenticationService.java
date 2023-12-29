package com.gi.gateway.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gi.gateway.dto.request.login.LoginRequest;
import com.gi.gateway.dto.response.common.ApiResponse;
import com.gi.gateway.dto.response.common.LoginResponse;
import com.gi.gateway.entity.Employees;
import com.gi.gateway.exception.BadCredentialsException;
import com.gi.gateway.repository.EmployeeRepository;
import com.gi.gateway.security.jwt.JwtUtils;
import com.gi.gateway.security.userDetails.UserDetailsImpl;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AuthenticationService {
	private final PasswordEncoder bCrypt;
	private final EmployeeRepository employeeRepository;
	private final JwtUtils jwts;

	public Mono<ApiResponse> loginService(LoginRequest request) {
		String username = request.getUsername().trim();
		String password = request.getPassword();
		Employees employee = employeeRepository
				.findEmployeeByEmailOrUsernameAndDeleteFlagFalseAndResignDateIsNull(username, username).orElse(null);
		if (employee == null) {
			return Mono.error(new BadCredentialsException());
		}
		if (Objects.isNull(password) || !bCrypt.matches(password, employee.getPassword())) {
			return Mono.error(new BadCredentialsException());
		}
		List<String> roles = UserDetailsImpl.build(employee).getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		String jwtToken = jwts.generateJwtToken(employee.getEmail(), roles);
		LoginResponse loginResponse = new LoginResponse(username, jwtToken, jwts.getExpiredTimeAt(jwtToken).getTime(),
				roles);
		return Mono.just(ApiResponse.ApiResponseSuccess(loginResponse));
	}
}
