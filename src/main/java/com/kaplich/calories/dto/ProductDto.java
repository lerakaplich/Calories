package com.kaplich.calories.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
    @JsonProperty("product_name")
    private String productName;
    @JsonProperty("count_of_calories")
    private double countOfCalories;
    @JsonProperty("protein")
    private double protein;
    @JsonProperty("fat")
    private double fat;
    @JsonProperty("carbohydrate")
    private double carbohydrate;
}
