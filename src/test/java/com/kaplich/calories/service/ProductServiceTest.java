package com.kaplich.calories.service;

import com.kaplich.calories.cache.CacheEntity;
import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.mapper.ProductMapper;
import com.kaplich.calories.model.Product;
import com.kaplich.calories.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CacheEntity productCache;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<ProductDto> result = productService.findAllProducts();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
        verifyNoMoreInteractions(productRepository);
        verify(productCache, times(1)).put("all", result);
        verifyNoMoreInteractions(productCache);
    }

    @Test
    void testSaveProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("Test Product");

        when(productRepository.findByProductName(anyString())).thenReturn(null);
        when(productRepository.save(any(Product.class))).thenReturn(new Product());

        ProductDto result = productService.saveProduct(productDto);

        assertEquals(productDto, result);
    }

    @Test
    void testFindByProductName() {
        String productName = "Test Product";
        Product product = new Product();
        product.setProductName(productName);

        when(productRepository.findByProductName(productName)).thenReturn(product);

        ProductDto result = productService.findByProductName(productName);
    }

    // Дополнительные тесты для остальных методов класса ProductService

}