package com.icetea.monstu_back.repository;

import com.icetea.monstu_back.model.PostCategory;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PostCategoryRepository extends ReactiveCrudRepository<PostCategory, Long> {
}
