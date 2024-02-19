package se.magnus.microservices.core.review.services;
import static java.util.logging.Level.FINE;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import se.magnus.microservices.api.core.review.Review;
import se.magnus.microservices.api.core.review.ReviewService;
import se.magnus.microservices.api.exception.InvalidInputException;
import se.magnus.microservices.core.review.persistence.ReviewEntity;
import se.magnus.microservices.core.review.persistence.ReviewRepository;
import se.magnus.microservices.util.http.ServiceUtil;


@RestController
public class ReviewServiceImpl implements ReviewService {

    private static final Logger log = LoggerFactory.getLogger((ReviewServiceImpl.class));
    private final ServiceUtil serviceUtil;
    private final ReviewRepository repository;
    private final ReviewMapper mapper;
    private final Scheduler scheduler;

    @Autowired
    public ReviewServiceImpl(@Qualifier("jdbcScheduler") Scheduler scheduler,ReviewRepository repository, ReviewMapper mapper, ServiceUtil serviceUtil) {
        this.scheduler= scheduler;
        this.repository=repository;
        this.mapper= mapper;
        this.serviceUtil = serviceUtil;
    }



    @Override
    public Flux<Review> getReviews(int productId) {


        if (productId<1){
            throw new InvalidInputException("productId is a positive integer");
        }
    
       return Mono.fromCallable(()->internalGetReviews(productId))
        .flatMapMany(Flux::fromIterable)
        .log(log.getName(),FINE)
        .subscribeOn(scheduler);
    
}


    public List<Review> internalGetReviews(int productId){
       log.debug("We wil willl get alll revies for product with id: "+ productId);
       List<ReviewEntity> entities= repository.findByProductId(productId);
       List<Review> apis= mapper.entityListToApiList(entities);
       apis.forEach(e->e.setServiceAddress(serviceUtil.getServiceAddress()));
       log.debug("Response size: {}", apis.size());
return apis;

    }
    @Override
    public Mono<Review> createReview(Review body) {
        if (body.getProductId() < 1) {
      throw new InvalidInputException("Invalid productId: " + body.getProductId());
    }
        // TODO Auto-generated method stub
        return Mono.fromCallable(()->internalCreateReview(body)).subscribeOn(scheduler);
    }

    private Review internalCreateReview(Review body){
        try {
            ReviewEntity entity=repository.save(mapper.apiToEntity(body));
            log.debug("New review saved with review_id/product_id: {}/{}",body.getReviewId(),body.getProductId());
            return mapper.entityToApi(entity);
        } catch (DuplicateKeyException e) {
            throw new InvalidInputException("Duplicate entry not allowed");
        }
    }



    @Override
    public Mono<Void> deleteReview(int ProductId) {
     return Mono.fromRunnable(()->internalDeleteReviews(ProductId)).subscribeOn(scheduler).then();   
    }
    private void internalDeleteReviews(int ProductId){
        log.debug("We are going to delete all the reviews related to product: "+ProductId);
        repository.deleteAll(repository.findByProductId(ProductId));
    }
}