package com.kaplich.calories.controller;

import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.model.Product;
import com.kaplich.calories.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(productService);
    }

    @Test
    void testFindAllProducts() {
        List<ProductDto> expectedProducts = new ArrayList<>();
        when(productService.findAllProducts()).thenReturn(expectedProducts);

        List<ProductDto> result = productController.findAllProducts();

        assertEquals(expectedProducts, result);
        verify(productService, times(1)).findAllProducts();
    }

    @Test
    void testSaveProduct() {
        ProductDto productDto = new ProductDto();
        ProductDto savedProductDto = new ProductDto();
        when(productService.saveProduct(productDto)).thenReturn(savedProductDto);

        ProductDto result = productController.saveProduct(productDto);

        assertEquals(savedProductDto, result);
        verify(productService, times(1)).saveProduct(productDto);
    }

    @Test
    void testFindByProductName() {
        String productName = "Apple";
        ProductDto expectedProductDto = new ProductDto();
        when(productService.findByProductName(productName)).thenReturn(expectedProductDto);

        ProductDto result = productController.findByProductName(productName);

        assertEquals(expectedProductDto, result);
        verify(productService, times(1)).findByProductName(productName);
    }

    @Test
    void testUpdateProduct() {
        String productName = "Apple";
        String newProductName = "Orange";
        Product updatedProduct = new Product();
        when(productService.updateProduct(productName, newProductName)).thenReturn(updatedProduct);

        Product result = productController.updateProduct(productName, newProductName);

        assertEquals(updatedProduct, result);
        verify(productService, times(1)).updateProduct(productName, newProductName);
    }

    @Test
    void testDeleteProduct() {
        String productName = "Apple";

        productController.deleteProduct(productName);

        verify(productService, times(1)).deleteProduct(productName);
    }
}