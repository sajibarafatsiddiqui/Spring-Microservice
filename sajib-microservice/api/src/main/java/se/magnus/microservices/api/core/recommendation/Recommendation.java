package se.magnus.microservices.api.core.recommendation;
import java.util.Objects;

public class Recommendation {
 
    private final int productId,rate,recommendationId;
    private final String author,serviceAddress,content;

    public Recommendation() {
        productId =0;
        rate = 0;
        recommendationId=0;
        author=null;
        serviceAddress=null;
        content=null;

    }

    public Recommendation(int productId, int rate, int recommendationId, String author, String serviceAddress, String content) {
        this.productId = productId;
        this.rate = rate;
        this.recommendationId = recommendationId;
        this.author = author;
        this.serviceAddress = serviceAddress;
        this.content = content;
    }

    public int getProductId() {
        return this.productId;
    }


    public int getRate() {
        return this.rate;
    }


    public int getRecommendationId() {
        return this.recommendationId;
    }


    public String getAuthor() {
        return this.author;
    }


    public String getServiceAddress() {
        return this.serviceAddress;
    }


    public String getContent() {
        return this.content;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Recommendation)) {
            return false;
        }
        Recommendation recommendation = (Recommendation) o;
        return productId == recommendation.productId && rate == recommendation.rate && recommendationId == recommendation.recommendationId && Objects.equals(author, recommendation.author) && Objects.equals(serviceAddress, recommendation.serviceAddress) && Objects.equals(content, recommendation.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, rate, recommendationId, author, serviceAddress, content);
    }

    @Override
    public String toString() {
        return "{" +
            " productId='" + getProductId() + "'" +
            ", rate='" + getRate() + "'" +
            ", recommendationId='" + getRecommendationId() + "'" +
            ", author='" + getAuthor() + "'" +
            ", serviceAddress='" + getServiceAddress() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }

    

}


