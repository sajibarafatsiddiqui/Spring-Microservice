package se.magnus.microservices.core.product.services;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import se.magnus.microservices.api.core.product.Product;
import se.magnus.microservices.core.product.persistence.ProductEntity;

@Mapper(componentModel="spring")
public interface ProductMapper {
    @Mappings({
        @Mapping(target = "serviceAddress", ignore = true)
    })
    Product entitytoApi(ProductEntity entity);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "version", ignore = true)
    })
    ProductEntity apitoEntity(Product api);
}
