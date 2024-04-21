package com.kaplich.calories.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "dish")
@Entity

public class Dish {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "dish_product",
            joinColumns = @JoinColumn(name = "dish_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    List<Product> productList;
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String dishName;
    @Column
    private double countOfCalories;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "client_id")
    private Client client;

}
