package com.gi.hrm.service.category;

import java.util.Objects;

import org.springframework.stereotype.Service;

import com.gi.hrm.dto.request.category.CategoryUpserRequest;
import com.gi.hrm.entity.Category;
import com.gi.hrm.exception.RecordNotFoundException;
import com.gi.hrm.repository.reactive.CategoryRepository;
import com.gi.hrm.service.mongo.MongoUtilService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CategoryDetailService {
	private final CategoryRepository categoryRepository;
	private final MongoUtilService mongoService;

	public Mono<Category> upsertCategory(CategoryUpserRequest request) {
		Integer id = request.getId();
		Integer workspaceId = request.getWorkspaceId();
		String name = request.getName();
		String color = request.getColor();
		if (Objects.nonNull(id)) {
			return categoryRepository.findByIdAndDeleteFlagFalse(id)
			        .switchIfEmpty(Mono.error(new RecordNotFoundException(id))).flatMap(category -> {
				        category.setName(name);
				        category.setColor(color);
				        category.setCommonUpdate(1);
				        return categoryRepository.save(category);
			        });

		} else {
			return mongoService.generateSequence(Category.SEQUENCE_NAME).map(idSeq -> {
				Category category = new Category(workspaceId, name, color);
				category.setId(idSeq);
				category.setCommonRegist(1);
				return category;
			}).flatMap(categoryRepository::save);
		}
	}

	public Mono<Integer> deleteCategory(String id) {
		return categoryRepository.findByIdAndDeleteFlagFalse(Integer.parseInt(id))
		        .switchIfEmpty(Mono.error(new RecordNotFoundException(id))).flatMap(item -> {
			        item.setCommonDelete(1);
			        return categoryRepository.save(item);
		        }).map(Category::getId);
	}

	public Flux<Category> listCategory() {
		return categoryRepository.findAllByDeleteFlagFalseOrderByIdDesc();
	}
}
