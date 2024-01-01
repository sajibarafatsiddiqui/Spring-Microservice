package se.magnus.microservices.core.review.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import se.magnus.microservices.api.core.review.Review;
import se.magnus.microservices.api.core.review.ReviewService;
import se.magnus.microservices.api.exception.InvalidInputException;
import se.magnus.microservices.util.http.ServiceUtil;


@RestController
public class ReviewServiceImpl implements ReviewService {

    private static final Logger log = LoggerFactory.getLogger((ReviewServiceImpl.class));
    private final ServiceUtil serviceUtil;

    @Autowired
    public ReviewServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }



    @Override
    public List<Review> getReviews(int productId) {


        if (productId<1){
            throw new InvalidInputException("productId is a positive integer");
        }
        if (productId == 113){
            log.debug("No recommendations found for productId: {}", productId);

            return new ArrayList<>();
        }
        
        List<Review> list = new ArrayList<>();

        list.add(new Review(productId,1,"sajib","vaseline","great!",serviceUtil.getServiceAddress()));
        list.add(new Review(productId,2,"sajib","vaseline","great!",serviceUtil.getServiceAddress()));
        list.add(new Review(productId,3,"sajib","vaseline","great!",serviceUtil.getServiceAddress()));

        return list;
    
}
}