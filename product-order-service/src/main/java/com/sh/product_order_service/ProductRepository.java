package com.sh.product_order_service;

import java.util.HashMap;
import java.util.Map;

public class ProductRepository {
    private long sequence = 0L;
    private Map<Long, Product> persistence = new HashMap<>();

    public void save(final Product product) {
        product.assignId(++sequence);
        persistence.put(product.getId(), product);
    }
}
