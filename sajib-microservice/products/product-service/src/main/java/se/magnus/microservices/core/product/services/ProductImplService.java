package se.magnus.microservices.core.product.services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import se.magnus.microservices.api.core.product.Product;
import se.magnus.microservices.api.core.product.ProductService;
import se.magnus.microservices.api.exception.InvalidInputException;
import se.magnus.microservices.api.exception.NotFoundException;
import se.magnus.microservices.util.http.ServiceUtil;

@RestController
public class ProductImplService implements ProductService {

    private static final Logger LOG = LoggerFactory.getLogger(ProductImplService.class);

  private final ServiceUtil serviceUtil;

  @Autowired
  public ProductImplService(ServiceUtil serviceUtil) {
    this.serviceUtil = serviceUtil;
  }

  @Override
  public Product getProduct(int productId) {
    LOG.debug("/product return the found product for productId={}", productId);

    if (productId < 1) {
      throw new InvalidInputException("Invalid productId: " + productId);
    }

    if (productId == 13) {
      throw new NotFoundException("No product found for productId: " + productId);
    }

    return new Product(productId, "name-" + productId, 123, serviceUtil.getServiceAddress());
  }
    
}
