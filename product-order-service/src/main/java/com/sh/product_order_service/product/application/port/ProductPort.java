package com.sh.product_order_service.product.application.port;

import com.sh.product_order_service.product.domain.Product;

public interface ProductPort {
    void save(final Product product);
}