package se.magnus.microservices.api.composite;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ProductCompositeService {
    
    @GetMapping("/product-composite/{productId}")
    ProductComposite getProduct(@PathVariable int productId);
}
