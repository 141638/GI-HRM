package com.gi.hrm.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gi.hrm.dto.response.ApiResponse;
import com.gi.hrm.service.common.CommonDropdownService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/dropdown")
@AllArgsConstructor
public class CommonDropdownController {
	private final CommonDropdownService commonDropdownService;

	@GetMapping("department-all")
	public ResponseEntity<ApiResponse> dropdownAllDepartment() {
		return ResponseEntity.ok().body(commonDropdownService.dropdownAllDepartment());
	}

	@GetMapping("project-all")
	public ResponseEntity<ApiResponse> dropdownAllProject() {
		return ResponseEntity.ok().body(commonDropdownService.dropdownAllProject());
	}

	@GetMapping("staff-all")
	public ResponseEntity<ApiResponse> dropdownAllStaff() {
		return ResponseEntity.ok().body(commonDropdownService.dropdownAllStaff());
	}
}
