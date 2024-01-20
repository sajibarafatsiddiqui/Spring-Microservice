package se.magnus.microservices.core.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import se.magnus.microservices.core.product.persistence.ProductEntity;
import se.magnus.microservices.core.product.persistence.ProductRepository;

@DataMongoTest
public class PersistenceTest extends MongoDbTestBase{
    @Autowired
    ProductEntity savedEntity;
    ProductRepository repository;

    @BeforeEach
    void setupDb(){
        repository.deleteAll();
        ProductEntity newEntity= new ProductEntity(1,"rice",25);
        repository.save(newEntity);

    }

    private void assetEqualProducts(ProductEntity expectedEntity, ProductEntity actualEntity){
    assertEquals(expectedEntity.getId(),actualEntity.getId());
    assertEquals(expectedEntity.getVersion(),actualEntity.getVersion());
    assertEquals(expectedEntity.getProductId(),actualEntity.getProductId());
    assertEquals(expectedEntity.getName(),actualEntity.getName());
    assertEquals(expectedEntity.getWeight(),actualEntity.getWeight());
    }
    
}
