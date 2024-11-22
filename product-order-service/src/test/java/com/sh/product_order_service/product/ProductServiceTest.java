package com.sh.product_order_service.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

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

        // Arrange
        final String name = "상품명";
        final int price = 1000;
        final DiscountPolicy discountPolicy = DiscountPolicy.NONE;
        final AddProductRequest request = new AddProductRequest(name, price, discountPolicy);

        // Act
        productService.addProduct(request);

        // Assert

    }

    private class ProductService {
        private final ProductPort productPort;

        private ProductService(ProductPort productPort) {
            this.productPort = productPort;
        }

        public void addProduct(AddProductRequest request) {
            final Product product = new Product(request.name(), request.price, request.discountPolicy());
            productPort.save(product);
        }
    }

    class Product {
        private Long id;
        private final String name;
        private final int price;
        private final DiscountPolicy discountPolicy;

        private Product(String name, int price, DiscountPolicy discountPolicy) {
            Assert.hasText(name, "상품명은 필수입니다.");
            Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
            Assert.notNull(discountPolicy, "할인 정책은 필수입니다.");

            this.name = name;
            this.price = price;
            this.discountPolicy = discountPolicy;
        }

        public void assignId(final Long id) {
            this.id = id;
        }

        public long getId() {
            return id;
        }

    }

    private record AddProductRequest(String name, int price, DiscountPolicy discountPolicy) {
        private AddProductRequest {
            Assert.hasText(name, "상품명은 필수입니다.");
            Assert.isTrue(price > 0, "상품 가격은 0보다 커야 합니다.");
            Assert.notNull(discountPolicy, "할인 정책은 필수입니다.");
        }
    }

    private enum DiscountPolicy {
        NONE
    }

    private interface ProductPort {
        void save(final Product product);
    }

    private class ProductAdapter implements ProductPort {
        private final ProductRepository productRepository;

        private ProductAdapter(final ProductRepository productRepository) {
            this.productRepository = productRepository;
        }

        @Override
        public void save(final Product product) {
            productRepository.save(product);
        }

    }

    private class ProductRepository {
        private long sequence = 0L;
        private Map<Long, Product> persistence = new HashMap<>();

        public void save(final Product product) {
            product.assignId(++sequence);
            persistence.put(product.getId(), product);
        }
    }
}
