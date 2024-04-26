package com.gihrm.sandservice.vuejsdemo.service;

import com.gihrm.sandservice.vuejsdemo.database.entity.GvtStoreDEntity;
import com.gihrm.sandservice.vuejsdemo.database.entity.GvtStoreEntity;
import com.gihrm.sandservice.vuejsdemo.database.repository.GvtStoreDRepository;
import com.gihrm.sandservice.vuejsdemo.database.repository.GvtStoreRepository;
import com.gihrm.sandservice.vuejsdemo.database.template.GvtStoreTemplate;
import com.gihrm.sandservice.vuejsdemo.dto.request.GvtStoreInsertionDto;
import com.gihrm.sandservice.vuejsdemo.dto.request.ServiceTokenDto;
import com.gihrm.sandservice.vuejsdemo.dto.response.PreBuiltServerResponse;
import com.gihrm.sandservice.vuejsdemo.util.MongoUtilService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalTime;
import java.util.Objects;

@Service
public class StoreService {
    private final GvtStoreTemplate gvtStoreTemplate;
    private final GvtStoreRepository gvtStoreRepository;
    private final GvtStoreDRepository gvtStoreDRepository;
    private final MongoUtilService mongoUtilService;

    @Autowired
    public StoreService(GvtStoreRepository gvtStoreRepository, GvtStoreDRepository gvtStoreDRepository, MongoUtilService mongoUtilService,
        GvtStoreTemplate gvtStoreTemplate) {
        this.gvtStoreTemplate = gvtStoreTemplate;
        this.gvtStoreRepository = gvtStoreRepository;
        this.gvtStoreDRepository = gvtStoreDRepository;
        this.mongoUtilService = mongoUtilService;
    }

    public Mono<ServerResponse> insertStore(ServerRequest request) {
        Mono<ServiceTokenDto> tokenDtoMono = Mono.deferContextual(ctxView -> Mono.just(ctxView.get("tokenDto")));
        Mono<GvtStoreInsertionDto> insertDtoMono = request.bodyToMono(GvtStoreInsertionDto.class);
        Mono<Integer> storeIdMono = mongoUtilService.generateSequence(GvtStoreEntity.SEQUENCE_NAME);
        return Mono.zip(tokenDtoMono, insertDtoMono, storeIdMono).map(data -> {
            ServiceTokenDto tokenDto = data.getT1();
            GvtStoreInsertionDto insertDto = data.getT2();
            Integer storeId = data.getT3();

            var storeEntity = new GvtStoreEntity();
            storeEntity.setId(storeId);
            storeEntity.setName(insertDto.getName());
            storeEntity.setAddress(insertDto.getAddress());
            storeEntity.setContactNumber(insertDto.getContactNumber());
            storeEntity.setDescription(insertDto.getDescription());
            storeEntity.setCommonRegist(tokenDto.getUserId());

            return storeEntity;
        }).map(gvtStoreRepository::insert).flatMap(PreBuiltServerResponse::success);
    }

    public Mono<ServerResponse> updateStore(ServerRequest request) {
        // get serviceTokenDto from context
        Mono<ServiceTokenDto> tokenDtoMono = Mono.deferContextual(ctxView -> Mono.just(ctxView.get("tokenDto")));
        // get entity update from request body
        Mono<GvtStoreEntity> updateEntityMono = request.bodyToMono(GvtStoreEntity.class);
        // update entity
        return Mono.zip(tokenDtoMono, updateEntityMono).flatMap(data -> {
            // serviceTokenDto
            ServiceTokenDto tokenDto = data.getT1();
            // updateEntity
            GvtStoreEntity updateEntity = data.getT2();

            // get the to-be-updated entity
            Mono<GvtStoreEntity> oldEntity = gvtStoreRepository.findByIdAndDeleteFlagFalse(updateEntity.getId());

            // save the before-update value to the delete table
            Mono<Integer> deleteEntityIdMono = mongoUtilService.generateSequence(GvtStoreDEntity.SEQUENCE_NAME);
            Mono<GvtStoreDEntity> gvtStoreDInsert = Mono.zip(oldEntity, deleteEntityIdMono).map(toUpdateData -> {
                GvtStoreDEntity storeDeleteEntity = new GvtStoreDEntity();
                BeanUtils.copyProperties(toUpdateData.getT1(), storeDeleteEntity);
                storeDeleteEntity.setId(toUpdateData.getT2());
                return storeDeleteEntity;
            }).flatMap(gvtStoreDRepository::insert);

            // update the entity
            Mono<GvtStoreEntity> newEntity = oldEntity.map(entity -> {
                BeanUtils.copyProperties(updateEntity, entity);
                entity.setOpenTime(LocalTime.of(8, 30));
                entity.setCloseTime(LocalTime.of(22, 0));
                entity.setCommonUpdate(tokenDto.getUserId());
                return entity;
            });
            return gvtStoreDInsert.then(newEntity);
        }).map(gvtStoreRepository::save).flatMap(PreBuiltServerResponse::success);
    }

    public Mono<ServerResponse> getStoreList(ServerRequest request) {
        return Mono.just(request.queryParams()).map(params -> gvtStoreTemplate.search(params.getFirst("key"), params.getFirst("status")))
            .flatMap(PreBuiltServerResponse::success);
    }

    public Mono<ServerResponse> getStoreDetail(ServerRequest request) {
        return Mono.fromCallable(request::pathVariables).map(pathVariables -> {
            String id = pathVariables.get("id");
            if (Objects.isNull(id)) {
                throw new IllegalArgumentException("parameter [id] is null");
            }
            return gvtStoreRepository.findById(Integer.parseInt(id));
        }).switchIfEmpty(Mono.error(new EmptyResultDataAccessException("store detail not found", 1))).flatMap(PreBuiltServerResponse::success);
    }
}
