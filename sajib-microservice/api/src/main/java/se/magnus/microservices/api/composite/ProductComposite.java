package se.magnus.microservices.api.composite;

import java.util.List;
import java.util.Objects;

import se.magnus.microservices.api.core.product.Product;
import se.magnus.microservices.api.core.recommendation.Recommendation;
import se.magnus.microservices.api.core.review.Review;

public class ProductComposite {
    private final int productId;
  private final String name;
  private final int weight;
  
    private final List<RecommendationSummary> recommendations;
    private final List<ReviewSummary> reviews;
    private final ServiceAddresses serviceAddresses;
    

    public ProductComposite(int productId, String name, int weight, List<RecommendationSummary> recommendations, List<ReviewSummary> reviews, ServiceAddresses serviceAddresses) {
        this.productId = productId;
        this.name = name;
        this.weight = weight;
        this.recommendations = recommendations;
        this.reviews = reviews;
        this.serviceAddresses = serviceAddresses;
    }

    public int getProductId() {
        return this.productId;
    }


    public String getName() {
        return this.name;
    }


    public int getWeight() {
        return this.weight;

    }

    public List<RecommendationSummary> getRecommendations() {
        return this.recommendations;
    }


    public List<ReviewSummary> getReviews() {
        return this.reviews;
    }


    public ServiceAddresses getServiceAddresses() {
        return this.serviceAddresses;
    }


   
 
    
}