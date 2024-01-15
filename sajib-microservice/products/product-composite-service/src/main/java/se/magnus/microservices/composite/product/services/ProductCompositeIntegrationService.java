package se.magnus.microservices.composite.product.services;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.http.HttpMethod.GET;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import se.magnus.microservices.api.core.product.Product;
import se.magnus.microservices.api.core.product.ProductService;
import se.magnus.microservices.api.core.recommendation.Recommendation;
import se.magnus.microservices.api.core.recommendation.RecommendationService;
import se.magnus.microservices.api.core.review.Review;
import se.magnus.microservices.api.core.review.ReviewService;
import se.magnus.microservices.api.exception.InvalidInputException;
import se.magnus.microservices.api.exception.NotFoundException;
import se.magnus.microservices.util.http.HttpErrorInfo;

@Component
public class ProductCompositeIntegrationService implements ProductService,ReviewService,RecommendationService{

    private final Logger log = LoggerFactory.getLogger(ProductCompositeIntegrationService.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final String productServiceUrl;
    private final String recommendationServiceUrl;
    private final String reviewServiceUrl;

    @Autowired
    public ProductCompositeIntegrationService(RestTemplate restTemplate, ObjectMapper objectMapper,
    @Value("${app.product.host}") String productHost,
    @Value("${app.product.port}") String productPort,
    @Value("${app.review.host}") String reviewHost,
    @Value("${app.review.port}") String reviewPort, 
    @Value("${app.recommendation.host}") String recommendationHost,
    @Value("${app.recommendation.port}") String recommendationPort
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        productServiceUrl = "http://"+productHost +":"+productPort+"/product/";
        recommendationServiceUrl ="http://"+recommendationHost+":"+recommendationPort+"/recommendation?productId=";
        reviewServiceUrl = "http://"+reviewHost+":"+reviewPort+"/review?productId=";
    }
    @Override
    public List<Recommendation> getRecommendations(int productId) {
       try {
        String url = recommendationServiceUrl + productId;
        log.debug("We are navigating to the api: {}",url);
        List<Recommendation> recommendations = restTemplate.exchange(url,GET,null,new ParameterizedTypeReference<List<Recommendation>>(){}).getBody();
        log.debug("Found {} recommendations for a product with id: {}", recommendations.size(), productId);
        return recommendations;     
        }
        catch (Exception ex) {
        log.warn("Got following exceptions :{}",ex.getMessage());
        return new ArrayList<>();
       }
    }

    @Override
    public List<Review> getReviews(int productId) {
        try {
        String url = reviewServiceUrl + productId;
        log.debug("We are navigating to the api: {}",url);
        List<Review> reviews = restTemplate.exchange(url,GET,null,new ParameterizedTypeReference<List<Review>>(){}).getBody();
        log.debug("Found {} reviews for a product with id: {}", reviews.size(), productId);
        return reviews;     
        }
        catch (Exception ex) {
        log.warn("Got following exceptions :{}",ex.getMessage());
        return new ArrayList<>();
       }
    }
    @Override
    public Product getProduct(int productId) {
        try {
            String url =productServiceUrl + productId;
        log.debug("Will go to getProduct api on {}",url);
        Product product = restTemplate.getForObject(url,Product.class);
        log.debug("Found a product with id {}",product.getProductId());
        return product;
        } catch (HttpClientErrorException ex) {
            switch (HttpStatus.resolve(ex.getStatusCode().value())) {
                case UNPROCESSABLE_ENTITY:
                    throw new InvalidInputException(getErrorMessage(ex));
         
                case NOT_FOUND:
                    throw new NotFoundException(getErrorMessage(ex));    
                default:
                log.warn("Got unexpected exception with code {}",ex.getStatusCode());
                log.warn("Error Body: {}",ex.getResponseBodyAsString());
                    throw ex;
            }
        }

        
    }

    private String getErrorMessage(HttpClientErrorException ex){
        try {
           return objectMapper.readValue(ex.getResponseBodyAsString(),HttpErrorInfo.class).getMessage();

        } catch (IOException e) {
            return e.getMessage();
        }
    }
    
}
