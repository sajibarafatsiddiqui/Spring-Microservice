package se.magnus.microservices.api.core.review;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReviewService{

    Mono<Review> createReview(@RequestBody Review body);
    
    @GetMapping(value="/review",produces="application/json")
    Flux<Review> getReviews(
        @RequestParam(value="productId",required = true) int productId);

    Mono<Void> deleteReview(int ProductId);

}