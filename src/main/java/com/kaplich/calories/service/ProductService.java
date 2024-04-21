package com.kaplich.calories.service;

import com.kaplich.calories.cache.CacheEntity;
import com.kaplich.calories.dto.ProductDto;
import com.kaplich.calories.mapper.ProductMapper;
import com.kaplich.calories.model.Product;
import com.kaplich.calories.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private CacheEntity productCache;
    public List<ProductDto> findAllProducts() {

        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            ProductDto productDto = ProductMapper.toDto(product);
            productDtoList.add(productDto);
        }
        productCache.put("all", productDtoList);
        return productDtoList;
    }

    public ProductDto saveProduct(final ProductDto productDto) {

        if (productRepository.
                findByProductName(productDto.getProductName()) == null) {
            productRepository.
                    save(ProductMapper.toEntity(productDto));
            productCache.
                    put(productDto.getProductName(), productDto);
        }
        return productDto;
    }

    public ProductDto findByProductName(final String nameOfProduct) {
        Product product = productRepository
                .findByProductName(nameOfProduct);
        return ProductMapper.toDto(product);
    }

    public Product updateProduct(final String productName,
                                 final String newProductName) {
        Product newProduct = productRepository.findByProductName(productName);
        if (newProduct == null) {
            return null;
        }
        newProduct.setProductName(newProductName);
        productCache.put(newProductName, ProductMapper.toDto(newProduct));
        return newProduct;
    }

    public void deleteProduct(final String nameOfProduct) {
        Product productToDelete =
                productRepository.findByProductName(nameOfProduct);
        if (productToDelete != null) {
            productRepository.delete(productToDelete);
            productCache.remove(productToDelete.getProductName());
        }
    }

}
