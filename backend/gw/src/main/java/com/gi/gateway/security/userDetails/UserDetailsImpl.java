package com.gi.gateway.security.userDetails;

import com.gi.gateway.common.Erole;
import com.gi.gateway.dto.response.common.UserDetailsDto;
import com.gi.gateway.entity.Employees;
import com.gi.gateway.entity.RoleGroup;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private String language;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public UserDetailsImpl(Integer id, String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(Employees employee) {
		return build(new UserDetailsDto(employee.getId(), employee.getEmail(), employee.getPassword(),
				employee.getRoleGroups()));
	}

	public static UserDetailsImpl build(UserDetailsDto employee) {
		Set<String> roles = new HashSet<>();
		List<GrantedAuthority> authorities = new ArrayList<>();
		RoleGroup employeeRoles = employee.getRoles();

		Boolean hrFlag = employeeRoles.getHrFlag();
		Boolean leaderFlag = employeeRoles.getLeaderFlag();
		Boolean projectManagerFlag = employeeRoles.getProjectManagerFlag();
		Boolean employeeFlag = employeeRoles.getEmployeeFlag();
		Boolean accountantFlag = employeeRoles.getAccountantFlag();
		Boolean sourcerFlag = employeeRoles.getSourcerFlag();
		Boolean recruiterFlag = employeeRoles.getRecruiterFlag();
		Boolean guestFlag = employeeRoles.getGuestFlag();
		Boolean staffFlag = employeeRoles.getStaffFlag();

		if (hrFlag) {
			if (leaderFlag) {
				roles.add(Erole.HR.toString());
			}
			if (accountantFlag) {
				roles.add(Erole.ACCOUNTANT.toString());
			}
			if (sourcerFlag) {
				roles.add(Erole.SOURCER.toString());
			}
			if (recruiterFlag) {
				roles.add(Erole.RECRUITER.toString());
			}
			if (staffFlag) {
				roles.add(Erole.STAFF.toString());
			}
		} else {
			if (guestFlag) {
				roles.add(Erole.GUEST.toString());
			}
			if (employeeFlag) {
				roles.add(Erole.EMPLOYEE.toString());
			}
			if (leaderFlag) {
				roles.add(Erole.LEADER.toString());
			}
			if (projectManagerFlag) {
				roles.add(Erole.PROJECT_MANAGER.toString());
			}
		}
		authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		return new UserDetailsImpl(employee.getId(), employee.getUsername(), employee.getPassword(), authorities);
	}

}
