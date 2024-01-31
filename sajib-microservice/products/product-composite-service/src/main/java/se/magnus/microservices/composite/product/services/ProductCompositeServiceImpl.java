package se.magnus.microservices.composite.product.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import se.magnus.microservices.api.composite.ProductComposite;
import se.magnus.microservices.api.composite.ProductCompositeService;
import se.magnus.microservices.api.composite.RecommendationSummary;
import se.magnus.microservices.api.composite.ReviewSummary;
import se.magnus.microservices.api.composite.ServiceAddresses;
import se.magnus.microservices.api.core.product.Product;
import se.magnus.microservices.api.core.product.ProductService;
import se.magnus.microservices.api.core.recommendation.Recommendation;
import se.magnus.microservices.api.core.review.Review;
import se.magnus.microservices.api.exception.NotFoundException;
import se.magnus.microservices.util.http.ServiceUtil;

@RestController
public class ProductCompositeServiceImpl implements ProductCompositeService{


    private final ServiceUtil serviceUtil;
    private final ProductCompositeIntegrationService compositeProduct;

   @Autowired
    public ProductCompositeServiceImpl(ServiceUtil serviceUtil, ProductCompositeIntegrationService compositeProduct) {
        this.serviceUtil = serviceUtil;
        this.compositeProduct = compositeProduct;
    }


    @Override
    public ProductComposite getProduct(int productId) {
        // TODO Auto-generated method stub
      Product product = compositeProduct.getProduct(productId);
      if (product == null){
        throw new NotFoundException("No product found for productId: " + productId);
      }
      List<Recommendation> recommendations = compositeProduct.getRecommendations(productId);
      List<Review> reviews = compositeProduct.getReviews(productId);

      return createProductComposite(product, recommendations, reviews,serviceUtil.getServiceAddress());


    }

private ProductComposite createProductComposite(Product product,
List<Recommendation> recommendations,
List<Review> reviews,
String serviceAddress){

    int productId = product.getProductId();
    String name = product.getName();
    int weight = product.getWeight();

    // 2. Copy summary recommendation info, if available
    List<RecommendationSummary> recommendationSummaries =
      (recommendations == null) ? null : recommendations.stream()
        .map(r -> new RecommendationSummary(r.getRecommendationId(),r.getAuthor(),r.getRate()))
        .collect(Collectors.toList());

    // 3. Copy summary review info, if available
    List<ReviewSummary> reviewSummaries = 
      (reviews == null) ? null : reviews.stream()
        .map(r -> new ReviewSummary(r.getReviewId(), r.getAuthor(), r.getSubject()))
        .collect(Collectors.toList());

    // 4. Create info regarding the involved microservices addresses
    String productAddress = product.getServiceAddress();
    String reviewAddress = (reviews != null && reviews.size() > 0) ? reviews.get(0).getServiceAddress() : "";
    String recommendationAddress = (recommendations != null && recommendations.size() > 0) ? recommendations.get(0).getServiceAddress() : "";
    ServiceAddresses serviceAddresses = new ServiceAddresses(serviceAddress, productAddress, reviewAddress, recommendationAddress);

    return new ProductComposite(productId, name, weight,recommendationSummaries, reviewSummaries, serviceAddresses);
}


@Override
public void createProduct(ProductComposite body) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'createProduct'");
}


@Override
public void deleteProduct(int productId) {
  // TODO Auto-generated method stub
  throw new UnsupportedOperationException("Unimplemented method 'deleteProduct'");
}
    
}
