package com.kaplich.calories.controller;

import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.dto.DishDto;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.model.Dish;
import com.kaplich.calories.service.DishService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/dishes")
public class DishController {
    private final DishService service;
    private static final String DISHES_VIEW = "dishes";

    @GetMapping
    public String findAllDishes(Model model) {
        List<DishDto> dishes = service.findAllDishes();
        model.addAttribute("dishes", dishes);
        model.addAttribute("message", model.getAttribute("message")); // Получаем сообщение об успехе
        return DISHES_VIEW;
    }

    @PostMapping("/save")
    public String saveDish(@Valid @ModelAttribute("dishDto") final DishDto dishDto,
                             final BindingResult bindingResult,
                             final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Если есть ошибки валидации, возвращаемся на ту же страницу с ошибками
            return "createDish";
        }
        service.saveDish(dishDto); // Сохранение клиента
        redirectAttributes.addFlashAttribute("message", "Блюдо успешно сохранено");
        return "redirect:/dishes"; // Перенаправление на страницу списка клиентов
    }

    @GetMapping("/save")
    public String createDish(final @ModelAttribute("dishes") DishDto dishDto) {
        return "createDish";
    }



    @GetMapping("/findByName")
    public DishDto findByDishName(@RequestParam final String nameOfDish) {

        return service.findByDishName(nameOfDish);
    }

    @PutMapping("/update")
    public Dish updateDish(final String dishName, final String newDishName) {
        return service.updateDish(dishName, newDishName);
    }

    @PostMapping(value = "/{nameOfDish}", params = "_method=DELETE")
    public String deleteClient(@PathVariable final String nameOfDish, final Model model) {
        service.deleteDish(nameOfDish);
        return "redirect:/dishes";
    }


    @GetMapping("/delete/{nameOfDish}")
    public String showDeleteForm(@PathVariable String nameOfDish, Model model) {
        final DishDto dishDto = service.findByDishName(nameOfDish);
        if (dishDto!= null) {
            model.addAttribute("dish", dishDto);
            return "deleteDish";
        } else {
            // Обработайте случай, когда клиент не найден
            return "error"; // Замените на вашу страницу ошибки
        }
    }

    @GetMapping("/findByNameAndCountOfCalories")
    public List<DishDto> findByClientNameAndCountOfCalories(
            @Param("clientName") final String clientName,
            @Param("countOfCalories") final double countOfCalories) {
        return service.
                findByClientNameAndCountOfCalories(clientName, countOfCalories);
    }
}
