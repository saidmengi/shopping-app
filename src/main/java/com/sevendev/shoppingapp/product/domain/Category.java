package com.sevendev.shoppingapp.product.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "category")
@Data
@Builder
@EqualsAndHashCode(of = "id")
public class Category {
    @Id
    private String id;
    private String name;
    private String code;
}
