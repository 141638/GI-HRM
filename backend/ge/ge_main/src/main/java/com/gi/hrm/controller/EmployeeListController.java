package com.gi.hrm.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gi.hrm.dto.request.system.EmployeeAddRequest;
import com.gi.hrm.dto.request.system.EmployeeListSearchRequest;
import com.gi.hrm.dto.response.ApiResponse;
import com.gi.hrm.service.system.EmployeeListService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/staff")
@AllArgsConstructor
public class EmployeeListController {
	private final EmployeeListService employeeListService;
	private static final String HEADER_KEY = "Location";
	private static final String HEADER_VALUE = "resource-employee";

	@PostMapping(value = "/search")
	public ResponseEntity<ApiResponse> search(@RequestBody EmployeeListSearchRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).header(HEADER_KEY, HEADER_VALUE)
		        .body(employeeListService.search(request));
	}

	@PostMapping(value = "/add")
	public ResponseEntity<ApiResponse> add(@RequestBody EmployeeAddRequest request,
	        @RequestHeader("session-user-id") Integer sessionUserId) {
		return ResponseEntity.status(HttpStatus.CREATED).header(HEADER_KEY, HEADER_VALUE)
		        .body(employeeListService.add(request, sessionUserId));
	}

	@GetMapping(value = "/details")
	public ResponseEntity<ApiResponse> details(@RequestParam Integer id) {
		return ResponseEntity.status(HttpStatus.CREATED).header(HEADER_KEY, HEADER_VALUE)
		        .body(employeeListService.details(id));
	}

	@DeleteMapping(value = "/delete")
	public ResponseEntity<ApiResponse> delete(@RequestParam Integer id) {
		return ResponseEntity.status(HttpStatus.CREATED).header(HEADER_KEY, HEADER_VALUE)
		        .body(employeeListService.delete(id));
	}
}
