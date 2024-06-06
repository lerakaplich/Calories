package com.kaplich.calories.controller;

import com.kaplich.calories.dto.DishDto;
import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.model.Product;
import com.kaplich.calories.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    private static final String HOME_VIEW = "home";

    private static final String PRODUCTS_VIEW = "products";

    @GetMapping
    public String findAllProducts(Model model) {
        List<ProductDto> products = service.findAllProducts();
        model.addAttribute(PRODUCTS_VIEW, products);
        model.addAttribute("message", model.getAttribute("message")); // Получаем сообщение об успехе
        return PRODUCTS_VIEW;
    }

    @PostMapping("/save")
    public String saveProduct(@Valid @ModelAttribute("productDto") final ProductDto productDto,
                           final BindingResult bindingResult,
                           final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            // Если есть ошибки валидации, возвращаемся на ту же страницу с ошибками
            return "createProduct";
        }
        service.saveProduct(productDto); // Сохранение клиента
        redirectAttributes.addFlashAttribute("message", "Продукт успешно сохранен");
        return "redirect:/products"; // Перенаправление на страницу списка клиентов
    }

    @GetMapping("/save")
    public String createProduct(final @ModelAttribute("products") ProductDto productDto) {
        return "createProduct";
    }

    @GetMapping("/findByName")
    public ProductDto findByProductName(@RequestParam
                                        final String nameOfProduct) {
        return service.findByProductName(nameOfProduct);
    }

    @PutMapping("/update")
    public Product updateProduct(final String productName,
                                 final String newProductName) {
        return service.updateProduct(productName, newProductName);
    }

    @PostMapping(value = "/{nameOfProduct}", params = "_method=DELETE")
    public String deleteClient(@PathVariable final String nameOfProduct, final Model model) {
        service.deleteProduct(nameOfProduct);
        return "redirect:/products";
    }


    @GetMapping("/delete/{nameOfProduct}")
    public String showDeleteForm(@PathVariable String nameOfProduct, Model model) {
        final ProductDto productDto = service.findByProductName(nameOfProduct);
        if (productDto!= null) {
            model.addAttribute("product", productDto);
            return "deleteProduct";
        } else {
            // Обработайте случай, когда клиент не найден
            return "error"; // Замените на вашу страницу ошибки
        }
    }
}
