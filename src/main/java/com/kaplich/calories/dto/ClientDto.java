package com.kaplich.calories.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ClientDto {
    @JsonProperty("client_name")
    private String clientName;
    @JsonProperty("weight")
    private double weight;
    @JsonProperty("height")
    private double height;
    @JsonProperty("dish_list")
    private List<String> dishDtoList;

}
