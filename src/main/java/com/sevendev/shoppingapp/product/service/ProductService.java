package com.sevendev.shoppingapp.product.service;

import com.sevendev.shoppingapp.product.domain.MoneyType;
import com.sevendev.shoppingapp.product.domain.Product;
import com.sevendev.shoppingapp.product.domain.ProductImage;
import com.sevendev.shoppingapp.product.domain.es.ProductEs;
import com.sevendev.shoppingapp.product.model.ProductResponse;
import com.sevendev.shoppingapp.product.model.ProductSaveRequest;
import com.sevendev.shoppingapp.product.model.ProductSellerResponse;
import com.sevendev.shoppingapp.product.repository.es.ProductEsRepository;
import com.sevendev.shoppingapp.product.repository.mongo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static com.sevendev.shoppingapp.product.domain.ProductImage.ImageType.FEATURE;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPriceService productPriceService;
    private final ProductDeliveryService productDeliveryService;
    private final ProductAmountService productAmountService;
    private final ProductImageService productImageService;
    private final ProductEsService productEsService;

    public Flux<ProductResponse> getAll() {
        return productEsService.findAll().map(this::mapToDto);
    }

    public ProductResponse save(ProductSaveRequest request) {
        Product product = Product.builder()
                .active(Boolean.TRUE)
                .code("PR0001")
                .categoryId(request.getCategoryId())
                .companyId(request.getSellerId())
                .description(request.getDescription())
                .id(request.getId())
                .features(request.getFeatures())
                .name(request.getName())
                .price(request.getPrice())
                .productImages(request.getImages().stream().map(it -> new ProductImage(FEATURE, it)).collect(toList()))
                .build();
        product = productRepository.save(product).block();

        return this.mapToDto(productEsService.saveNewProduct(product).block());
    }

    private ProductResponse mapToDto(ProductEs item) {
        if (item == null) {
            return null;
        }
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

    public Mono<Long> count() {
        return productRepository.count();
    }
}
