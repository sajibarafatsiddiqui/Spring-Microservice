package se.magnus.microservices.core.recommendation.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DuplicateKeyException;

import se.magnus.microservices.api.core.recommendation.Recommendation;
import se.magnus.microservices.api.core.recommendation.RecommendationService;
import se.magnus.microservices.api.exception.InvalidInputException;
import se.magnus.microservices.core.recommendation.persistence.RecommendationEntity;
import se.magnus.microservices.core.recommendation.persistence.RecommendationRepository;
import se.magnus.microservices.util.http.ServiceUtil;

@RestController
public class RecommendationServiceImpl implements RecommendationService{

    private static final Logger log = LoggerFactory.getLogger((RecommendationServiceImpl.class));
    private final ServiceUtil serviceUtil;
    private final RecommendationMapper mapper;
    private final RecommendationRepository repository;

    @Autowired
    public RecommendationServiceImpl( RecommendationRepository repository,RecommendationMapper mapper,
   ServiceUtil serviceUtil) {
        this.repository=repository;
        this.mapper=mapper;
        this.serviceUtil = serviceUtil;
    }



    @Override
    public List<Recommendation> getRecommendations(int productId) {


        if (productId<1){
            throw new InvalidInputException("productId is a positive integer");
        }
        
        List<Recommendation> list = mapper.entityListToApiList(repository.findByProductId(productId));
        list.forEach(e->e.setServiceAddress(serviceUtil.getServiceAddress()));
        log.debug("getRecommendations: response size: {}", list.size());
        return list;

    }



    @Override
    public Recommendation createRecommendation(Recommendation body) {
        try {
            RecommendationEntity entity= repository.save(mapper.apiToEntity(body));
            log.debug("new recommendation added with product Id/recomendation id:{}/{}",body.getProductId(),body.getRecommendationId());
            return mapper.entityToApi(entity);
        } catch (DuplicateKeyException e) {
            throw new InvalidInputException("Dupplicate key with product Id/recomendation id:"+body.getProductId()+"/"+body.getRecommendationId());
        }
    }



    @Override
    public void deleteRecommendation(int productId) {
        log.debug("deleteRecommendations: tries to delete recommendations for the product with productId: {}", productId);
        repository.deleteAll(repository.findByProductId(productId));
    }

    
}
