package se.magnus.microservices.api.composite;

import java.util.List;
import java.util.Objects;

import se.magnus.microservices.api.core.product.Product;
import se.magnus.microservices.api.core.recommendation.Recommendation;
import se.magnus.microservices.api.core.review.Review;

public class ProductComposite {
    private final Product product;
    private final List<Recommendation> recommendations;
    private final List<Review> reviews;


    public ProductComposite(Product product, List<Recommendation> recommendations, List<Review> reviews) {
        this.product = product;
        this.recommendations = recommendations;
        this.reviews = reviews;
    }

    public Product getProduct() {
        return this.product;
    }


    public List<Recommendation> getRecommendations() {
        return this.recommendations;
    }


    public List<Review> getReviews() {
        return this.reviews;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ProductComposite)) {
            return false;
        }
        ProductComposite productComposite = (ProductComposite) o;
        return Objects.equals(product, productComposite.product) && Objects.equals(recommendations, productComposite.recommendations) && Objects.equals(reviews, productComposite.reviews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, recommendations, reviews);
    }

    @Override
    public String toString() {
        return "{" +
            " product='" + getProduct() + "'" +
            ", recommendations='" + getRecommendations() + "'" +
            ", reviews='" + getReviews() + "'" +
            "}";
    }
}