package com.sevendev.shoppingapp.product.repository.mongo;

import com.sevendev.shoppingapp.product.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
