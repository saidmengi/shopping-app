package com.sevendev.shoppingapp.product.model.product;

import com.sevendev.shoppingapp.product.domain.MoneyType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Data

public class ProductSaveRequest {
    private String id;
    private String name;
    private String description;
    private String features;
    private BigDecimal available;
    private BigDecimal price;
    private MoneyType money;
    private List<String> images;
    private String sellerId;
    private String categoryId;
}
