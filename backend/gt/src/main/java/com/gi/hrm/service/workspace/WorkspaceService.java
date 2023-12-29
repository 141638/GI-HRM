package com.gi.hrm.service.workspace;

import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.gi.hrm.common.utils.DateTimeUtils;
import com.gi.hrm.common.utils.Utils;
import com.gi.hrm.dto.request.workspace.WorkspaceUpsertRequest;
import com.gi.hrm.dto.response.PreBuiltServerResponse;
import com.gi.hrm.dto.response.workspace.WorkspaceSearchResponse;
import com.gi.hrm.entity.Workspace;
import com.gi.hrm.exception.BadRequestException;
import com.gi.hrm.exception.RecordNotFoundException;
import com.gi.hrm.repository.reactive.WorkspaceRepository;
import com.gi.hrm.service.mongo.MongoUtilService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class WorkspaceService {
	private final WorkspaceRepository workspaceRepository;
	private final MongoUtilService mongoUtilService;

	public Mono<ServerResponse> upsert(WorkspaceUpsertRequest request) {
		Integer id = request.getId();
		if (Objects.nonNull(id)) {
			Mono<Workspace> workspaceMono = workspaceRepository.findByIdAndDeleteFlagFalse(id);

			return workspaceMono.switchIfEmpty(Mono.error(new RecordNotFoundException(id))).map(workspace -> {
				workspace.setName(request.getName());
				workspace.setAlias(aliasCoalesce(request.getAlias(), request.getName()));
				workspace.setCommonUpdate(0);
				return workspace;
			}).map(workspaceRepository::save).flatMap(PreBuiltServerResponse::success);
		} else {
			Mono<Integer> idMono = mongoUtilService.generateSequence(Workspace.SEQUENCE_NAME);
			Mono<Workspace> workspaceMono = createNewWorkspaceEntity(Mono.just(request));

			return Mono.zip(idMono, workspaceMono).map(t -> {
				Integer idSeq = t.getT1();
				Workspace wsp = t.getT2();
				wsp.setId(idSeq);
				return wsp;
			}).map(workspaceRepository::save).flatMap(PreBuiltServerResponse::success);
		}
	}

	private Mono<Workspace> createNewWorkspaceEntity(Mono<WorkspaceUpsertRequest> requestMono) {
		return requestMono.map(request -> {
			var wsp = new Workspace();
			BeanUtils.copyProperties(request, wsp);
			wsp.setAlias(aliasCoalesce(wsp.getAlias(), wsp.getName()));
			wsp.setCommonRegist(0);
			return wsp;
		});
	}

	private String aliasCoalesce(String alias, String name) {
		if (!StringUtils.hasText(alias)) {
			return Utils.regexFilter("[A-Z]+", name) + (DateTimeUtils.currentTimestamp().getTime() / 10000000000L);
		}
		return alias;
	}

	public Mono<ServerResponse> search(MultiValueMap<String, String> params) {
		String name = params.getFirst("name");
		String projectId = params.getFirst("projectId");
		String staffId = params.getFirst("staffId");

		Flux<WorkspaceSearchResponse> workspaceFlux = workspaceRepository.search(name, projectId, staffId);

		return PreBuiltServerResponse.success(workspaceFlux);
	}

	public Mono<ServerResponse> delete(MultiValueMap<String, String> params) {
		return Mono.just(params.getFirst("id"))
		        .switchIfEmpty(Mono.error(new BadRequestException("param {id} not found", 002))).map(Integer::parseInt)
		        .flatMap(workspaceRepository::findByIdAndDeleteFlagFalse)
		        .switchIfEmpty(Mono.error(new RecordNotFoundException("workspace"))).map(item -> {
			        item.setCommonDelete(1);
			        return item;
		        }).map(workspaceRepository::save).flatMap(PreBuiltServerResponse::success);
	}
}
