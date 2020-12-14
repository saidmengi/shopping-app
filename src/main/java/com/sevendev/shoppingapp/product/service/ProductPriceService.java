package com.sevendev.shoppingapp.product.service;

import com.sevendev.shoppingapp.product.domain.MoneyType;
import com.sevendev.shoppingapp.product.repository.mongo.ProductPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductPriceService {
    private final ProductPriceRepository productPriceRepository;

    public BigDecimal getByMoneyType(String id, MoneyType usd) {
        return BigDecimal.TEN;
    }
}
