package com.gi.gateway.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;

import com.gi.gateway.security.userDetails.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ReactiveCommonService {
	public String buildUri(String endpoint, String path, Map<String, Object> params) {
		return this.buildUri(endpoint, path, null, params);
	}

	public String buildUri(String endpoint, String path, List<String> pathVariables) {
		return this.buildUri(endpoint, path, pathVariables, null);
	}

	public String buildUri(String endpoint, String path) {
		return this.buildUri(endpoint, path, null, null);
	}

	private String buildUri(String endpoint, String path, List<String> pathVariables, Map<String, Object> params) {
		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			log.error(e.getLocalizedMessage());
			return null;
		}
		UriBuilder uriBuidler = new DefaultUriBuilderFactory().builder();
		uriBuidler.host(url.getHost()).port(url.getPort()).scheme("http").path(url.getPath()).pathSegment(path);
		if (pathVariables != null) {
			pathVariables.forEach(item -> uriBuidler.pathSegment(item));
		}
		if (params != null) {
			params.entrySet().forEach(item -> uriBuidler.queryParam(item.getKey(), item.getValue()));
		}

		return uriBuidler.build().toASCIIString();
	}

	public Mono<Integer> currentUserLoginId() {
		return userDetails().map(userDetail -> ((Integer) ((UserDetailsImpl) userDetail).getId()));
	}

	private Mono<UserDetails> userDetails() {
		return ReactiveSecurityContextHolder.getContext()
				.map(contextHolder -> (UserDetails) contextHolder.getAuthentication().getPrincipal());
	}
}
