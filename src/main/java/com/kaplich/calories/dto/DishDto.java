package com.kaplich.calories.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DishDto {
    @JsonProperty("dish_name")
    private String dishName;
    @JsonProperty("count_of_calories")
    private double countOfCalories;
    @JsonProperty("product_list")
    private List<ProductDto> productList;
    @JsonIgnore
    @JsonProperty("client_id")
    private ClientDto clientDto;

}
