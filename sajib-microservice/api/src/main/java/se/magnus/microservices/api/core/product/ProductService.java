package se.magnus.microservices.api.core.product;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import reactor.core.publisher.Mono;



public interface ProductService {
    @GetMapping(value="/product/{productId}",produces="application/json")
    Mono<Product> getProduct(@PathVariable int productId);

    @PostMapping(value="/product/",produces="application/json",consumes="application/json")
    Mono<Product> createProduct(@RequestBody Product body);

    @DeleteMapping(value="/product/{productId}")
    Mono<Void> deleteProduct(@PathVariable int productId);
}