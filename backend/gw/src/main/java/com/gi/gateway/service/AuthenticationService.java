package com.gi.gateway.service;

import com.gi.gateway.dto.request.login.LoginRequest;
import com.gi.gateway.dto.response.common.ApiResponse;
import com.gi.gateway.dto.response.common.LoginResponse;
import com.gi.gateway.entity.Employees;
import com.gi.gateway.exception.BadCredentialsException;
import com.gi.gateway.repository.EmployeeRepository;
import com.gi.gateway.security.jwt.JwtUtils;
import com.gi.gateway.security.user_details.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder bCrypt;
    private final EmployeeRepository employeeRepository;
    private final JwtUtils jwts;

    public Mono<ApiResponse> loginService(LoginRequest request) {
        final String username = request.getUsername().trim();
        final String password = request.getPassword();
        Employees employee = employeeRepository
                .findEmployeeByEmailOrUsernameAndDeleteFlagFalseAndResignDateIsNull(username, username).orElse(null);
        if (employee == null) {
            return Mono.error(new BadCredentialsException());
        }
        if (Objects.isNull(password) || !bCrypt.matches(password, employee.getPassword())) {
            return Mono.error(new BadCredentialsException());
        }
        List<String> roles = UserDetailsImpl.build(employee).getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        String jwtToken = jwts.generateJwtToken(employee.getId(), employee.getEmail(), roles);
        LoginResponse loginResponse = new LoginResponse(employee.getId(), username, jwtToken, jwts.getExpiredTimeAt(jwtToken).getTime(),
                roles);
        return Mono.just(ApiResponse.apiResponseSuccess(loginResponse));
    }
}
