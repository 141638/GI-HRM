package com.gi.gateway.security.user_details;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gi.gateway.dto.response.common.UserDetailsDto;
import com.gi.gateway.repository.EmployeeRepository;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		log.info("Load user by username");
		UserDetailsDto userDetail = employeeRepository.findToBuildUserDetails(username.strip())
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
		return Mono.just(UserDetailsImpl.build(userDetail));
	}

}
