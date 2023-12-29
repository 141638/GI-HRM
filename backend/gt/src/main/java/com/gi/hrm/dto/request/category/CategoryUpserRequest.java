package com.gi.hrm.dto.request.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class CategoryUpserRequest {
	private Integer id;
	private Integer workspaceId;
	private String name;
	private String color;
}
