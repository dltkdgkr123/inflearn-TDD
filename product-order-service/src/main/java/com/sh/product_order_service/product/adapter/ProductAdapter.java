package com.sh.product_order_service.product.adapter;

import com.sh.product_order_service.product.application.port.ProductPort;
import com.sh.product_order_service.product.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductAdapter implements ProductPort {
    private final ProductRepository productRepository;

    public ProductAdapter(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(final Product product) {
        productRepository.save(product);
    }

}
