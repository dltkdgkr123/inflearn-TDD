package com.sh.product_order_service.product.application.service;

import com.sh.product_order_service.product.application.port.ProductPort;
import com.sh.product_order_service.product.domain.Product;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductApiService {
    private final ProductPort productPort;

    public ProductApiService(final ProductPort productPort) {
        this.productPort = productPort;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Response> addProduct(@RequestBody final AddProductRequest request) {
        final Product product = new Product(request.name(), request.price(), request.discountPolicy());
        productPort.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
