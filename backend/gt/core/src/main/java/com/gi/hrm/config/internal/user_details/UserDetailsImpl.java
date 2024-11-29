package com.gi.hrm.config.internal.user_details;

import com.gi.hrm.presentation.UserDetailsDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    /** user ID */
    private String id;
    /** username */
    private String username;
    /** password */
    private String password;
    /** user authorities */
    private Collection<? extends GrantedAuthority> authorities;
    /** user chosen language */
    private String language;

    /**
     * Constructor
     *
     * @param idParam user ID
     * @param usernameParam username
     * @param passwordParam password
     * @param authoritiesParam user authorities
     */
    public UserDetailsImpl(final String idParam, final String usernameParam, final String passwordParam,
                           final Collection<? extends GrantedAuthority> authoritiesParam) {
        this.id = idParam;
        this.username = usernameParam;
        this.password = passwordParam;
        this.authorities = authoritiesParam;
    }

    /**
     * Build {@link UserDetailsImpl} using username
     *
     * @param username username
     * @return new {@link UserDetailsImpl}
     */
    public static UserDetailsImpl build(final String username) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UserDetailsImpl("-1", username, "*******", authorities);
    }

    /**
     * Build {@link UserDetailsImpl} using {@link UserDetailsDto} object
     *
     * @param employee {@link UserDetailsDto}  object instance
     * @return new {@link UserDetailsImpl}
     */
    public static UserDetailsImpl build(final UserDetailsDto employee) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UserDetailsImpl(employee.getId(), employee.getUsername(), employee.getPassword(), authorities);
    }

    /**
     * Returns the authorities granted to the user. Cannot return <code>null</code>.
     *
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username used to authenticate the user. Cannot return <code>null</code>.
     *
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired. An expired account cannot be authenticated.
     *
     * @return <code>true</code> if the user's account is valid (ie non-expired),
     *         <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. A locked user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is not locked, <code>false</code> otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. Expired
     * credentials prevent authentication.
     *
     * @return <code>true</code> if the user's credentials are valid (ie non-expired),
     *         <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. A disabled user cannot be
     * authenticated.
     *
     * @return <code>true</code> if the user is enabled, <code>false</code> otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
