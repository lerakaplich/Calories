package com.kaplich.calories.repository;

import com.kaplich.calories.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    Dish findByDishName(String nameOfDish);

    Dish findDishById(Long id);

    @Override
    void delete(Dish entity);

    List<Dish> findAll();

    Dish saveAndFlush(Dish dish);

    @Query("SELECT d FROM Dish d INNER JOIN d.client"
            + " c WHERE c.clientName = :clientName "
            + "AND d.countOfCalories = :countOfCalories")
    List<Dish> findByClientNameAndCountOfCalories(
            @Param("clientName") String clientName,
            @Param("countOfCalories") double countOfCalories);
}
