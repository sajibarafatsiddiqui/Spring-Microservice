package se.magnus.microservices.api.core.recommendation;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

public interface RecommendationService {

    @GetMapping(value="/recommendation", produces="application/json")
    List<Recommendation> getRecommendations(
        @RequestParam(value = "productId", required = true) int productId);
      
    @PostMapping(value = "/recommendation",produces="application/json",
    consumes="application/json")
    Recommendation  createRecommendation(@RequestBody Recommendation body);

    @DeleteMapping(value="/recommendation", produces="application/json")
    void deleteRecommendation(
        @RequestParam(value = "productId", required = true) int productId);
      

    
}