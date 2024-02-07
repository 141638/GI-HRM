package com.gi.gateway.dto.request.tasklog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceProject {
    private Integer id;
    private String name;
    private WorkspaceUser curator;
}
