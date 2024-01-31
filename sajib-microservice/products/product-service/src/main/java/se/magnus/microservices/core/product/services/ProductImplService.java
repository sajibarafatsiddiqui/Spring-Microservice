package se.magnus.microservices.core.product.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

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
  public Product createProduct(Product product){
    try {
      ProductEntity entity = mapper.apitoEntity(product);
      ProductEntity newEntity = repository.save(entity);
      LOG.debug("New product create with productId: {}",entity.getProductId());
      return mapper.entitytoApi(newEntity);
    } catch (DuplicateKeyException e) {
      throw new InvalidInputException("Duplicate key, productId: "+ product.getProductId());
    }
  }

  @Override
  public Product getProduct(int productId) {
    LOG.debug("/product return the found product for productId={}", productId);

    if (productId < 1) {
      throw new InvalidInputException("Invalid productId: " + productId);
    }

   ProductEntity entity= repository.findByProductId(productId).orElseThrow(() -> new NotFoundException("The product with productId "+productId+" not available"));
   Product response =mapper.entitytoApi(entity);
   response.setServiceAddress(serviceUtil.getServiceAddress());
   LOG.debug("Got the product with id: {}",response.getProductId());
    return response;
  }

  
  @Override
  public void deleteProduct(int productId) {
    LOG.debug("Trying in to delete product with id : {}",productId);
    repository.findByProductId(productId).ifPresent(e->repository.delete(e));
  }
    
}
