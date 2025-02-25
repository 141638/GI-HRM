package com.gi.hrm.task.application.category;

import com.gi.hrm.mongo.MongoUtilService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryDetailService {
//    private final CategoryToDeleteRepository categoryToDeleteRepository;
    private final MongoUtilService mongoService;

//    public Mono<Category> upsertCategory(CategoryUpserRequest request) {
//        Integer value = request.getId();
//        Integer workspaceId = request.getWorkspaceId();
//        String name = request.getName();
//        String color = request.getColor();
//        if (Objects.nonNull(value)) {
//            return categoryRepository.findByIdAndDeleteFlagFalse(value)
//                    .switchIfEmpty(Mono.error(new RecordNotFoundException(value))).flatMap(category -> {
//                        category.setName(name);
//                        category.setColor(color);
//                        category.setCommonUpdate(1);
//                        return categoryRepository.save(category);
//                    });
//
//        } else {
//            return mongoService.generateSequence(Category.SEQUENCE_NAME).map(idSeq -> {
//                Category category = new Category(workspaceId, name, color);
//                category.setId(idSeq);
//                category.setCommonRegist(1);
//                return category;
//            }).flatMap(categoryRepository::save);
//        }
//    }
//
//    public Mono<Integer> deleteCategory(String value) {
//        return categoryRepository.findByIdAndDeleteFlagFalse(Integer.parseInt(value))
//                .switchIfEmpty(Mono.error(new RecordNotFoundException(value))).flatMap(item -> {
//                    item.setCommonDelete(1);
//                    return categoryRepository.save(item);
//                }).map(Category::getId);
//    }
//
//    public Flux<Category> listCategory() {
//        return categoryRepository.findAllByDeleteFlagFalseOrderByIdDesc();
//    }
}
