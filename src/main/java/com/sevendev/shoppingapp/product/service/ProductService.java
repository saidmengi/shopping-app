package com.sevendev.shoppingapp.product.service;

import com.sevendev.shoppingapp.product.domain.MoneyType;
import com.sevendev.shoppingapp.product.domain.es.ProductEs;
import com.sevendev.shoppingapp.product.model.ProductResponse;
import com.sevendev.shoppingapp.product.model.ProductSaveRequest;
import com.sevendev.shoppingapp.product.model.ProductSellerResponse;
import com.sevendev.shoppingapp.product.repository.es.ProductEsRepository;
import com.sevendev.shoppingapp.product.repository.mongo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductEsRepository productEsRepository;
    private final ProductRepository productRepository;
    private final ProductPriceService productPriceService;
    private final ProductDeliveryService productDeliveryService;
    private final ProductAmountService productAmountService;
    private final ProductImageService productImageService;

    public Flux<ProductResponse> getAll() {
        return productEsRepository.findAll().map(this::mapToDto);
    }

    ProductResponse save(ProductSaveRequest productSaveRequest) {
        return null;
    }

    private ProductResponse mapToDto(ProductEs item) {
        BigDecimal productPrice = productPriceService.getByMoneyType(item.getId(), MoneyType.USD);
        return ProductResponse.builder()
                .price(productPrice)
                .name(item.getName())
                .features(item.getFeatures())
                .id(item.getId())
                .description(item.getDescription())
                .deliveryIn(productDeliveryService.getDeliveryInfo(item.getId()))
                .categoryId(item.getCategory().getId())
                .available(productAmountService.getByProductId(item.getId()))
                .freeDelivery(productDeliveryService.freeDeliveryCheck(item.getId(), productPrice))
                .moneyType(MoneyType.USD)
                .image(productImageService.getProductMainImage(item.getId()))
                .seller(ProductSellerResponse.builder().id(item.getSeller().getId()).name(item.getName()).build())
                .build();
    }
}
