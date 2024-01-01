package se.magnus.microservices.core.recommendation.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import se.magnus.microservices.api.core.recommendation.Recommendation;
import se.magnus.microservices.api.core.recommendation.RecommendationService;
import se.magnus.microservices.api.exception.InvalidInputException;
import se.magnus.microservices.util.http.ServiceUtil;

@RestController
public class RecommendationServiceImpl implements RecommendationService{

    private static final Logger log = LoggerFactory.getLogger((RecommendationServiceImpl.class));
    private final ServiceUtil serviceUtil;

    @Autowired
    public RecommendationServiceImpl(ServiceUtil serviceUtil) {
        this.serviceUtil = serviceUtil;
    }



    @Override
    public List<Recommendation> getRecommendations(int productId) {


        if (productId<1){
            throw new InvalidInputException("productId is a positive integer");
        }
        if (productId == 113){
            log.debug("No recommendations found for productId: {}", productId);

            return new ArrayList<>();
        }
        
        List<Recommendation> list = new ArrayList<>();

        list.add(new Recommendation(productId,2,1,"sajib",serviceUtil.getServiceAddress(),"great"));
        list.add(new Recommendation(productId,2,2,"arafat",serviceUtil.getServiceAddress(),"great"));
        list.add(new Recommendation(productId,2,3,"siddiqui",serviceUtil.getServiceAddress(),"great"));

        return list;

    }

    
}
