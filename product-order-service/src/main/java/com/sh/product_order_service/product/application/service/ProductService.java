package com.sh.product_order_service.product.application.service;

import com.sh.product_order_service.product.application.port.ProductPort;
import com.sh.product_order_service.product.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductService {
    private final ProductPort productPort;

    public ProductService(final ProductPort productPort) {
        this.productPort = productPort;
    }

    public void addProduct(final AddProductRequest request) {
        final Product product = new Product(request.name(), request.price(), request.discountPolicy());
        productPort.save(product);
    }
}
