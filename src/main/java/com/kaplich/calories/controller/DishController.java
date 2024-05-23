package com.kaplich.calories.controller;

import com.kaplich.calories.dto.DishDto;
import com.kaplich.calories.model.Dish;
import com.kaplich.calories.service.DishService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/dishes")
public class DishController {
    private final DishService service;
    private static final String HOME_VIEW = "home";

    private static final String DISHES_VIEW = "dishes";

    @GetMapping
    public String findAllDishes(Model model) {
        List<DishDto> dishes = service.findAllDishes();
        model.addAttribute("dishes", dishes);
        return DISHES_VIEW;

    }

    @GetMapping("/home")
    public String findClients() {
        return HOME_VIEW;
    }

    @GetMapping("/save")
    public String createDay(final @ModelAttribute("dishes") DishDto dishDto) {
        return "createDish";
    }

    @PostMapping("/save")
    public DishDto saveDish(@RequestBody final DishDto dishDto) {
        return service.saveDish(dishDto);
    }

    @GetMapping("/findByName")
    public DishDto findByDishName(@RequestParam final String nameOfDish) {

        return service.findByDishName(nameOfDish);
    }

    @PutMapping("/update")
    public Dish updateDish(final String dishName, final String newDishName) {
        return service.updateDish(dishName, newDishName);
    }

    @DeleteMapping("/delete")
    public void deleteDish(@RequestParam final String nameOfDish) {

        service.deleteDish(nameOfDish);
    }

    @GetMapping("/findByNameAndCountOfCalories")
    public List<DishDto> findByClientNameAndCountOfCalories(
            @Param("clientName") final String clientName,
            @Param("countOfCalories") final double countOfCalories) {
        return service.
                findByClientNameAndCountOfCalories(clientName, countOfCalories);
    }
}
