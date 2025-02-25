package com.gi.hrm.task.application.workspace;

import com.gi.hrm.mongo.MongoUtilService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkspaceService {
//    private final WorkspaceRepository workspaceRepository;
    private final MongoUtilService mongoUtilService;

//    public Mono<ServerResponse> upsert(WorkspaceUpsertRequest request) {
//        Integer value = request.getId();
//        if (Objects.nonNull(value)) {
//            Mono<Workspace> workspaceMono = workspaceRepository.findByIdAndDeleteFlagFalse(value);
//
//            return workspaceMono.switchIfEmpty(Mono.error(new RecordNotFoundException(value))).map(workspace -> {
//                workspace.setName(request.getName());
//                workspace.setAlias(aliasCoalesce(request.getAlias(), request.getName()));
//                workspace.setCommonUpdate(0);
//                return workspace;
//            }).map(workspaceRepository::save).flatMap(PreBuiltServerResponse::success);
//        } else {
//            Mono<Integer> idMono = mongoUtilService.generateSequence(Workspace.SEQUENCE_NAME);
//            Mono<Workspace> workspaceMono = createNewWorkspaceEntity(Mono.just(request));
//
//            return Mono.zip(idMono, workspaceMono).map(t -> {
//                Integer idSeq = t.getT1();
//                Workspace wsp = t.getT2();
//                wsp.setId(idSeq);
//                return wsp;
//            }).map(workspaceRepository::save).flatMap(PreBuiltServerResponse::success);
//        }
//    }
//
//    private Mono<Workspace> createNewWorkspaceEntity(Mono<WorkspaceUpsertRequest> requestMono) {
//        return requestMono.map(request -> {
//            var wsp = Utils.copy(request, Workspace.class);
//            wsp.setAlias(aliasCoalesce(wsp.getAlias(), wsp.getName()));
//            wsp.getProject().setCurator(request.getCurator());
//            wsp.setCommonRegist(0);
//            return wsp;
//        });
//    }
//
//    private String aliasCoalesce(String alias, String name) {
//        if (!StringUtils.hasText(alias)) {
//            return Utils.regexFilter("[A-Z]+", name) + (DateTimeUtils.currentTimestamp().getTime() / 10000000000L);
//        }
//        return alias;
//    }
//
//    public Mono<ServerResponse> search(MultiValueMap<String, String> params) {
//        String name = params.getFirst("name");
//        String projectId = params.getFirst("projectId");
//        String staffId = params.getFirst("staffId");
//
//        Flux<WorkspaceSearchResponse> workspaceFlux = workspaceRepository.search(name, projectId, staffId);
//
//        return PreBuiltServerResponse.success(workspaceFlux);
//    }
//
//    public Mono<ServerResponse> delete(MultiValueMap<String, String> params) {
//        return Mono.just(Objects.requireNonNull(params.getFirst("value")))
//                .switchIfEmpty(Mono.error(new BadRequestException("param {value} not found", 2))).map(Integer::parseInt)
//                .flatMap(workspaceRepository::findByIdAndDeleteFlagFalse)
//                .switchIfEmpty(Mono.error(new RecordNotFoundException("workspace"))).map(item -> {
//                    item.setCommonDelete(1);
//                    return item;
//                }).map(workspaceRepository::save).flatMap(com.gi.hrm.presentation.PreBuiltServerResponse::success);
//    }
}
