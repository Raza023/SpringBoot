package com.example.orderclientservice.restclient;

import com.example.orderclientservice.entity.Product;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ProductClientService {

    private final RestClient restClient;

    public ProductClientService(RestClient restClient) {
        this.restClient = restClient;
    }

    // 🔹 POST
    public Product createProduct(Product product) {
        return restClient.post()
                .uri("/products")
                .body(product)
                .retrieve()
                .body(Product.class);
    }

    // 🔹 PUT
    public Product updateProduct(Long id, Product product) {
        return restClient.put()
                .uri("/products/{id}", id)
                .body(product)
                .retrieve()
                .body(Product.class);
    }

    // 🔹 DELETE
    public void deleteProduct(Long id) {
        restClient.delete()
                .uri("/products/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }

    //GET
    public Product getProduct(Long id) {
        return restClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        (req, res) -> {
                            throw new RuntimeException("Product Not Found");
                        })
                .body(Product.class);
    }
}
