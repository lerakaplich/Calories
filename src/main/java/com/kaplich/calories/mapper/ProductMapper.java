package com.kaplich.calories.mapper;

import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.model.Product;

public class ProductMapper {

    private ProductMapper() {
    }

    public static ProductDto toDto(final Product product) {
        if (product == null) {
            return null;
        }
        return ProductDto.builder()
                .productName(product.getProductName())
                .countOfCalories(product.getCountOfCalories())
                .protein(product.getProtein())
                .fat(product.getFat())
                .build();
    }

    public static Product toEntity(final ProductDto productDto) {
        if (productDto == null) {
            return null;
        }
        Product product = new Product();
        product.setProductName(productDto.getProductName());
        product.setCountOfCalories(productDto.getCountOfCalories());
        product.setProtein(productDto.getProtein());
        product.setFat(productDto.getFat());
        product.setCarbohydrate(productDto.getCarbohydrate());
        return product;
    }
}
