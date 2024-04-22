package com.gi.hrm.dto.request.workspace;

import com.gi.hrm.entity.Project;
import com.gi.hrm.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkspaceUpsertRequest {
    private Integer id;
    private String name;
    private String alias;
    private User curator;
    private Project project;
    private List<User> member;

}
