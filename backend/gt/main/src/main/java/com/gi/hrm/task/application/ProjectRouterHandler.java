package com.gi.hrm.task.application;

import com.gi.hrm.mongo.MongoUtilService;
import com.gi.hrm.presentation.PreBuiltServerResponse;
import com.gi.hrm.task.command.usecase.ChangeProjectParam;
import com.gi.hrm.task.command.usecase.ChangeProjectUsecase;
import com.gi.hrm.task.command.usecase.CreateProjectParam;
import com.gi.hrm.task.command.usecase.CreateProjectUsecase;
import com.gi.hrm.task.command.usecase.DeleteProjectParam;
import com.gi.hrm.task.command.usecase.DeleteProjectUsecase;
import com.gi.hrm.task.query.usecase.FindProjectParam;
import com.gi.hrm.task.query.usecase.FindProjectUsecase;
import com.gi.hrm.task.query.usecase.FindProjectsParam;
import com.gi.hrm.task.query.usecase.FindProjectsUsecase;
import com.gi.hrm.utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectRouterHandler {
    private final FindProjectsUsecase findProjectsUsecase;
    private final FindProjectUsecase findProjectUsecase;
    private final CreateProjectUsecase createProjectUsecase;
    private final ChangeProjectUsecase changeProjectUsecase;
    private final DeleteProjectUsecase deleteProjectUsecase;
    private final MongoUtilService mongoService;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        final Integer pageNoParam = CommonUtils.retrieveSingleInt(request.queryParams(), "pageNo");
        final Integer pageSizeParam = CommonUtils.retrieveSingleInt(request.queryParams(), "pageSize");

        val result = findProjectsUsecase.execute(new FindProjectsParam(pageNoParam, pageSizeParam));

        return PreBuiltServerResponse.success(result);
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        val projectIdPathVar = CommonUtils.execNS(request.pathVariable("projectId"), Integer::parseInt);

        val result = findProjectUsecase.execute(new FindProjectParam(projectIdPathVar));

        return PreBuiltServerResponse.success(result);
    }

    public record CreateProjectRequest(
            String name,
            List<CreateProjectParam.Category> categories,
            String owner
    ) { }

    public Mono<ServerResponse> create(ServerRequest request) {
        val createProjectParamMono = request.bodyToMono(CreateProjectRequest.class)
                .map(req -> new CreateProjectParam(req.name(), req.categories(), req.owner()));

        val result = createProjectParamMono.flatMap(createProjectParam ->
                createProjectUsecase.execute(createProjectParam, mongoService));

        return PreBuiltServerResponse.success(result);
    }

    public record ChangeProjectRequest(
            Integer id,
            String name,
            List<ChangeProjectParam.Category> categories,
            String owner
    ) { }

    public Mono<ServerResponse> change(ServerRequest request) {
        val changeProjectParamMono = request.bodyToMono(ChangeProjectRequest.class)
                .map(req -> new ChangeProjectParam(req.id(), req.name(), req.categories(), req.owner()));

        val result = changeProjectParamMono.flatMap(changeProjectParam ->
                changeProjectUsecase.execute(changeProjectParam, mongoService));

        return PreBuiltServerResponse.success(result);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        val projectIdPathVar = CommonUtils.execNS(request.pathVariable("projectId"), Integer::parseInt);

        val result = deleteProjectUsecase.execute(new DeleteProjectParam(projectIdPathVar));

        return PreBuiltServerResponse.success(result);
    }
}
