package se.magnus.microservices.core.product;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static java.util.stream.IntStream.rangeClosed;
import se.magnus.microservices.core.product.persistence.ProductEntity;
import se.magnus.microservices.core.product.persistence.ProductRepository;

@DataMongoTest
public class PersistenceTests extends MongoDbTestBase{
    
    @Autowired
    ProductRepository repository;

    ProductEntity savedEntity;

    @BeforeEach
    void setupDb(){
        repository.deleteAll();
        ProductEntity newEntity= new ProductEntity(1,"rice",25);
        savedEntity=repository.save(newEntity);
        assertEqualProducts(newEntity,savedEntity);

    }

    @Test
    void create(){
         ProductEntity secondEntity= new ProductEntity(2,"bean",25);
         repository.save(secondEntity);
         ProductEntity foundEntity=repository.findByProductId(secondEntity.getProductId()).get();
         assertEqualProducts(secondEntity, foundEntity);
         assertEquals(2,repository.count());
    }

    @Test
    void update(){
        savedEntity.setName("wheat");
        repository.save(savedEntity);
        ProductEntity foundEntity = repository.findByProductId(savedEntity.getProductId()).get();
        assertEquals("wheat",foundEntity.getName());
    } 

    @Test
    void delete(){
        repository.delete(savedEntity);
        assertFalse(repository.existsById(savedEntity.getId()));
    }  

    @Test
    void getProductId(){
        Optional<ProductEntity> getEntity = repository.findByProductId(savedEntity.getProductId());
        assertTrue(getEntity.isPresent());
        assertEqualProducts(savedEntity, getEntity.get());

    }

    @Test
    void duplicateError(){
        assertThrows(DuplicateKeyException.class,
        ()->{
            ProductEntity hello = new ProductEntity(savedEntity.getProductId(),"asa",12);
            repository.save(hello);
        });
    }

    @Test
    void Pageing(){
        repository.deleteAll();
        java.util.List<ProductEntity> products = rangeClosed(10001,10010).mapToObj(x-> new ProductEntity(x,"prod"+x,x)).collect(Collectors.toList()); 
        repository.saveAll(products);
        Pageable nextPage= PageRequest.of(0,4,ASC,"productId");
        nextPage=testNextPage(nextPage, "[10001, 10002, 10003, 10004]", true);
        nextPage=testNextPage(nextPage, "[10005, 10006, 10007, 10008]", true);
        nextPage=testNextPage(nextPage,"[10009, 10010]",false);

    }

    @Test
    void optimisticLockError(){
        ProductEntity entity1 = repository.findByProductId(savedEntity.getProductId()).get();
        ProductEntity entity2 = repository.findByProductId(savedEntity.getProductId()).get();
        entity1.setName("saq");
        repository.save(entity1);
        assertThrows(OptimisticLockingFailureException.class, ()->{entity2.setName("n6");repository.save(entity2);});
    }

    private Pageable testNextPage(Pageable nextPage,String expectProductIds,boolean hasNextPage){
        Page<ProductEntity> products = repository.findAll(nextPage);
        assertEquals(expectProductIds,products.getContent().stream().map(x->x.getProductId()).collect(Collectors.toList()).toString());
        assertEquals(hasNextPage,products.hasNext());
        return products.nextPageable();
    }

    private void assertEqualProducts(ProductEntity expectedEntity, ProductEntity actualEntity){
    assertEquals(expectedEntity.getId(),actualEntity.getId());
    assertEquals(expectedEntity.getVersion(),actualEntity.getVersion());
    assertEquals(expectedEntity.getProductId(),actualEntity.getProductId());
    assertEquals(expectedEntity.getName(),actualEntity.getName());
    assertEquals(expectedEntity.getWeight(),actualEntity.getWeight());
    }
    
}
