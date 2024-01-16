package se.magnus.microservices.core.product.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import se.magnus.microservices.api.core.product.Product;

@Document(collection="products")
public class ProductEntity {
    @Id
    private String id;
    @Version
    private Integer version;
    @Indexed(unique = true)
    private int productId;
    
    private String name;
    private int weight;
    private String serverAddress;
  


    public ProductEntity(int productId, String name, int weight) {
   
        this.productId = productId;
        this.name = name;
        this.weight = weight;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getServerAddress() {
        return this.serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }


    
}
