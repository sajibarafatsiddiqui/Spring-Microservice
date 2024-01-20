package se.magnus.microservices.core.product;

import static org.mockito.ArgumentMatchers.refEq;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;

public abstract class MongoDbTestBase {
   private static MongoDBContainer database = new MongoDBContainer("mongo:6.0.4");
    
   static {
    database.start();
   }

   @DynamicPropertySource
   static void setProperties(DynamicPropertyRegistry registry){
    registry.add("spring.data.mongodb.host",database::getHost);
    registry.add("spring.data.mongodb.port",() -> database.getMappedPort(2707));
    registry.add("spring.data.mongodb.database",()->"test");

   }
}
