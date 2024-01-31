package se.magnus.microservices.api.core.product;

public class Product {
    private int productId;
    private String name;
    private int weight;
    private String serviceAddress;

    public Product(int productId, String name, int weight, String serviceAddress) {
        this.productId = productId;
        this.name = name;
        this.weight = weight;
        this.serviceAddress = serviceAddress;
    }


    public int getProductId() {
        return this.productId;
    }


    public String getName() {
        return this.name;
    }


    public int getWeight() {
        return this.weight;
    }


    public String getServiceAddress() {
        return this.serviceAddress;
    }

    public void setServiceAddress(String serviceAddress){
       this.serviceAddress = serviceAddress;

    }
    
    }