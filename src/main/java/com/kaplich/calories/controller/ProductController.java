package com.kaplich.calories.controller;

import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.model.Product;
import com.kaplich.calories.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return PRODUCTS_VIEW;
    }

    @GetMapping("/home")
    public String findClients() {
        return HOME_VIEW;
    }

    @PostMapping("/save")
    public ProductDto saveProduct(@RequestBody
                                  final ProductDto productDto) {
        return service.saveProduct(productDto);
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

    @DeleteMapping("/delete")
    public void deleteProduct(@RequestParam final String nameOfProduct) {

        service.deleteProduct(nameOfProduct);
    }
}
