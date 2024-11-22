package com.sh.product_order_service.product;

import com.sh.product_order_service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductServiceTest {
    private ProductService productService;
    private ProductPort productPort;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
        productPort = new ProductAdapter(productRepository);
        productService = new ProductService(productPort);
    }

    @Test
    void AddProduct() {
        final AddProductRequest request = generateAddProductRequest();
    }

    private AddProductRequest generateAddProductRequest() {
        // Arrange
        final String name = "상품명";
        final int price = 1000;
        final DiscountPolicy discountPolicy = DiscountPolicy.NONE;
        final AddProductRequest request = new AddProductRequest(name, price, discountPolicy);

        // Act
        productService.addProduct(request);

        // Assert


        return request;
    }
}
