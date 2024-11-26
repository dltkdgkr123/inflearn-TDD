package com.sh.product_order_service.product;

import com.sh.product_order_service.ApiTest;
import com.sh.product_order_service.product.application.service.AddProductRequest;
import com.sh.product_order_service.product.domain.DiscountPolicy;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//@SpringBootTest
class ProductApiTest extends ApiTest {

    @Test
    void add_product() {
        // Arrange
        final var request = generateAddProductRequest();

        // Act : API 요청
        final var response = generateAddProductRequest(request);

        // Assert : 상태 코드 201 확인
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private static ExtractableResponse<Response> generateAddProductRequest(AddProductRequest request) {
        final ExtractableResponse<Response> response =
                RestAssured.given().log().all()
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .body(request)
                        .when()
                        .post("/product")
                        .then()
                        .log().all().extract();
        return response;
    }

    private AddProductRequest generateAddProductRequest() {
        // Arrange
        final String name = "상품명";
        final int price = 1000;
        final DiscountPolicy discountPolicy = DiscountPolicy.NONE;
        final AddProductRequest request = new AddProductRequest(name, price, discountPolicy);

        // Act

        // Assert

        return request;
    }
}
