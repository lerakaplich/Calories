package com.kaplich.calories.mapper;

import com.kaplich.calories.dto.DishDto;
import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.model.Dish;
import com.kaplich.calories.model.Product;

import java.util.ArrayList;
import java.util.List;

public final class DishMapper {

    private DishMapper() {
    }

    public static DishDto toDto(final Dish dish) {

        if (dish == null) {
            return null;
        }
        List<ProductDto> productDtoList = new ArrayList<>();
        if (dish.getProductList() != null) {
            for (Product product : dish.getProductList()) {
                ProductDto productDto = ProductMapper.toDto(product);
                productDtoList.add(productDto);
            }
        }
        if (dish.getProductList() != null) {
            if (dish.getClient() != null) {
                return DishDto.builder()
                        .dishName(dish.getDishName())
                        .productList(productDtoList)
                        .countOfCalories(dish.getCountOfCalories())
                        .clientDto(ClientMapper.toDto(dish.getClient()))
                        .build();
            } else {
                return DishDto.builder()
                        .dishName(dish.getDishName())
                        .productList(productDtoList)
                        .countOfCalories(dish.getCountOfCalories())
                        .build();
            }
        } else {
            if (dish.getClient() != null) {
                return DishDto.builder()
                        .dishName(dish.getDishName())
                        .countOfCalories(dish.getCountOfCalories())
                        .clientDto(ClientMapper.toDto(dish.getClient()))
                        .build();
            } else {
                return DishDto.builder()
                        .dishName(dish.getDishName())
                        .countOfCalories(dish.getCountOfCalories())
                        .build();
            }
        }

    }

    public static Dish toEntity(final DishDto dishDto) {
        if (dishDto == null) {
            return null;
        }
        List<Product> productList = new ArrayList<>();
        Dish dish = new Dish();
        dish.setDishName(dishDto.getDishName());
        dish.setCountOfCalories(dishDto.getCountOfCalories());
        if (dishDto.getProductList() != null) {
            for (ProductDto productDto : dishDto.getProductList()) {
                Product product = ProductMapper.toEntity(productDto);
                productList.add(product);
            }
            dish.setProductList(productList);
        }
        if (dishDto.getClientDto() != null) {
            dish.setClient(ClientMapper.toEntity(dishDto.getClientDto()));
        }
        return dish;
    }
}
