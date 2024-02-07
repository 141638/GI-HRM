package com.gi.gateway.dto.request.tasklog;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkspaceUpdateRequest {
    private Integer id;
    private String name;
    private String alias;
    private WorkspaceProject project;
    private WorkspaceUser curator;
    private List<WorkspaceUser> member;
}
