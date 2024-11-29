package com.gi.hrm.config.internal.user_details;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
    /**
     * Get user entity from database using username as query condition
     *
     * @param username the username to look up
     * @return a mono object of user entity
     */
    @Override
    public Mono<UserDetails> findByUsername(final String username) {
        log.info("Load user by email");
//        List<UserTable> userTable = Optional.ofNullable(databaseLayout.getUserTable())
//                .orElseThrow(() -> new IllegalStateException("user table does not exist"));

//        return Mono.just(UserDetailsImpl.build(
//                userTable.stream().filter(item -> item.getEmail().equals(username.strip())).findFirst()
//                        .orElseThrow(() -> new UsernameNotFoundException(
//                                "User not found with username: " + username)))
//        );
        return Mono.empty();
    }

}
