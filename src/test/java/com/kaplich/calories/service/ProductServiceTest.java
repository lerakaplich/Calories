package com.kaplich.calories.service;

import com.kaplich.calories.cache.CacheEntity;
import com.kaplich.calories.controller.ProductController;
import com.kaplich.calories.dto.ClientDto;
import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.mapper.ProductMapper;
import com.kaplich.calories.model.Client;
import com.kaplich.calories.model.Product;
import com.kaplich.calories.repository.ClientRepository;
import com.kaplich.calories.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductController productController;


    @Mock
    private ClientRepository clientRepository;

    @Mock
    private CacheEntity clientCache;

    private ClientService clientService;
    @Mock
    private CacheEntity productCache;


    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        productController = new ProductController(productService);
    }

    @Test
    void testUpdateProduct() {
        String productName = "Apple";
        String newProductName = "Orange";
        Product existingProduct = new Product();
        existingProduct.setProductName(productName);

        when(productRepository.findByProductName(productName)).thenReturn(existingProduct);
        when(productService.updateProduct(productName, newProductName)).thenCallRealMethod();

        Product result = productController.updateProduct(productName, newProductName);

        assertNotNull(result);
        assertEquals(newProductName, result.getProductName());
    }

    @Test
    void testSaveProduct_CacheHit() {
        // Test setup
        ProductDto productDto = new ProductDto();
        productDto.setProductName("TestProduct");
        List<ProductDto> cachedList = new ArrayList<>();
        cachedList.add(productDto);
        when(productCache.get("all")).thenReturn(cachedList);

        // Perform the action
        Product savedProduct = productRepository.save(ProductMapper.toEntity(productDto));

        // Assertion
        assertNotNull(savedProduct);
        assertEquals(productDto.getProductName(), savedProduct.getProductName());
    }

    @Test
    void testSaveProduct_CacheHitWithValidList() {
        // Test setup
        ProductDto productDto = new ProductDto();
        productDto.setProductName("TestProduct");
        List<ProductDto> cachedList = new ArrayList<>();
        cachedList.add(productDto);
        when(productCache.get("all")).thenReturn(cachedList);

        // Perform the action
        Product savedProduct = productRepository.save(ProductMapper.toEntity(productDto));

        // Assertion
        assertNotNull(savedProduct);
        assertEquals(productDto.getProductName(), savedProduct.getProductName());
    }

    @Test
    void testFindAllClients_CacheHitWithValidList() {
        // Test setup
        List<ClientDto> cachedList = new ArrayList<>();
        cachedList.add(new ClientDto());
        when(clientCache.get("all")).thenReturn(cachedList);

        // Perform the action
        List<ClientDto> result = productController.findAllClients();

        // Assertion
        assertNotNull(result);
        assertEquals(cachedList, result);
    }

    @Test
    void testFindAllClients_CacheMiss() {
        // Test setup
        when(clientCache.get("all")).thenReturn(null);
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        when(clientRepository.findAll()).thenReturn(clientList);

        // Perform the action
        List<ClientDto> result = productController.findAllClients();

        // Assertion
        assertNotNull(result);
        assertEquals(clientList.size(), result.size());
    }

    @Test
    void testFindAllClients_CacheHitWithInvalidList() {
        // Test setup
        List<String> cachedList = new ArrayList<>();
        cachedList.add("invalid");
        when(clientCache.get("all")).thenReturn(cachedList);
        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        when(clientRepository.findAll()).thenReturn(clientList);

        // Perform the action
        List<ClientDto> result = productController.findAllClients();

        // Assertion
        assertNotNull(result);
        assertEquals(clientList.size(), result.size());
    }
    @Test
    void testFindByProductName() {
        String productName = "Test Product";
        Product product = new Product();
        product.setProductName(productName);

        when(productRepository.findByProductName(productName)).thenReturn(product);

        ProductDto result = productService.findByProductName(productName);
    }

    @Test
    void testSaveProduct_NewProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("TestProduct");

        when(productRepository.findByProductName("TestProduct")).thenReturn(null);

        Product savedProductEntity = new Product();
        savedProductEntity.setProductName("TestProduct");
        when(productRepository.save(any(Product.class))).thenReturn(savedProductEntity);

        ProductDto savedProductDto = productService.saveProduct(productDto);

        assertNotNull(savedProductDto);
        assertEquals("TestProduct", savedProductDto.getProductName());
    }

    @Test
    void testSaveProduct_ExistingProduct() {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("TestProduct");

        Product existingProductEntity = new Product();
        existingProductEntity.setProductName("TestProduct");
        when(productRepository.findByProductName("TestProduct")).thenReturn(existingProductEntity);

        ProductDto savedProductDto = productService.saveProduct(productDto);

        assertNotNull(savedProductDto);
        assertEquals("TestProduct", savedProductDto.getProductName());

        verify(productRepository, times(1)).findByProductName("TestProduct");
        verify(productRepository, never()).save(any(Product.class));
        verify(productCache, never()).put(anyString(), any(ProductDto.class));
    }

    @Test
    void testSaveProduct_CacheHit() {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("TestProduct");

        List<ProductDto> cachedList = new ArrayList<>();
        cachedList.add(productDto);
        when(productCache.get("all")).thenReturn(cachedList);

        Product savedProduct = productRepository.save(ProductMapper.toEntity(productDto));


    }

    @Test
    void testSaveProduct_CacheHitWithValidList() {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("TestProduct");

        List<ProductDto> cachedList = new ArrayList<>();
        cachedList.add(productDto);
        when(productCache.get("all")).thenReturn(cachedList);

        Product savedProduct = productRepository.save(ProductMapper.toEntity(productDto));

    }

    @Test
    void testFindAllClients_CacheHitWithValidList() {
        List<ClientDto> cachedList = new ArrayList<>();
        cachedList.add(new ClientDto());
        when(clientCache.get("all")).thenReturn(cachedList);


    }


    @Test
    void testFindAllClients_CacheMiss() {
        when(clientCache.get("all")).thenReturn(null);

        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        when(clientRepository.findAll()).thenReturn(clientList);

    }

    @Test
    void testFindAllClients_CacheHitWithInvalidList() {
        List<String> cachedList = new ArrayList<>();
        cachedList.add("invalid");
        when(clientCache.get("all")).thenReturn(cachedList);

        List<Client> clientList = new ArrayList<>();
        clientList.add(new Client());
        when(clientRepository.findAll()).thenReturn(clientList);
    }
}
