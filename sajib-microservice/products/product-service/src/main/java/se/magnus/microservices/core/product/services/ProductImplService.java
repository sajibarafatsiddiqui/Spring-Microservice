package se.magnus.microservices.core.product.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;
import static java.util.logging.Level.FINE;
import reactor.core.publisher.Mono;
import se.magnus.microservices.api.core.product.Product;
import se.magnus.microservices.api.core.product.ProductService;
import se.magnus.microservices.api.exception.InvalidInputException;
import se.magnus.microservices.api.exception.NotFoundException;
import se.magnus.microservices.core.product.persistence.ProductEntity;
import se.magnus.microservices.core.product.persistence.ProductRepository;
import se.magnus.microservices.util.http.ServiceUtil;

@RestController
public class ProductImplService implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductImplService.class);

  private final ServiceUtil serviceUtil;
  private final ProductRepository repository;
  private final ProductMapper mapper;

  @Autowired
  public ProductImplService(ServiceUtil serviceUtil, ProductRepository repository, ProductMapper mapper) {
    this.serviceUtil = serviceUtil;
    this.mapper= mapper;
    this.repository=repository;
  }

  @Override
  public Mono<Product> createProduct(Product product){
  
      ProductEntity entity = mapper.apiToEntity(product);
     
      return repository.save(entity).log(LOG.getName(),FINE)
      .onErrorMap(DuplicateKeyException.class,ex->new InvalidInputException("Duplicate key, productId: "+ product.getProductId()))
      .map(e->mapper.entityToApi(e));
      
  }

  @Override
  public Mono<Product> getProduct(int productId) {
    
    if (productId < 1) {
      throw new InvalidInputException("Invalid productId: " + productId);
    }
    LOG.info("Will get product info for id={}", productId);
  //  ProductEntity entity= repository.findByProductId(productId).orElseThrow(() -> new NotFoundException("The product with productId "+productId+" not available"));
  //  Product response =mapper.entitytoApi(entity);
  //  response.setServiceAddress(serviceUtil.getServiceAddress());
  //  LOG.debug("Got the product with id: {}",response.getProductId());
  //   return response;
  return repository.findByProductId(productId)
         .switchIfEmpty(Mono.error(new NotFoundException("The product with productId "+productId+" not available")))
         .log(LOG.getName(),FINE) 
         .map(e -> mapper.entityToApi(e))
         .map(e -> setServiceAddress(e)) ;
  }

  
  @Override
  public Mono<Void> deleteProduct(int productId) {
    if (productId < 1) {
      throw new InvalidInputException("Invalid productId: " + productId);
    }
    LOG.debug("Trying in to delete product with id : {}",productId);
   return repository.findByProductId(productId).log(LOG.getName(),FINE).map(e->repository.delete(e)).flatMap(e->e);
  }

  private Product setServiceAddress(Product e) {
    e.setServiceAddress(serviceUtil.getServiceAddress());
    return e;
  }
    
}
