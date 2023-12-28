package se.magnus.microservices.api.core.review;
import java.util.Objects;

public class Review{
private final int productId, reviewId;
private final String author, subject, content, serviceAddress;

    public Review() {
        productId=0; reviewId=0;
        author=null; subject=null; content=null; serviceAddress=null;

    }

    public Review(int productId, int reviewId, String author, String subject, String content, String serviceAddress) {
        this.productId = productId;
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }

    public int getProductId() {
        return this.productId;
    }


    public int getReviewId() {
        return this.reviewId;
    }


    public String getAuthor() {
        return this.author;
    }


    public String getSubject() {
        return this.subject;
    }


    public String getContent() {
        return this.content;
    }


    public String getServiceAddress() {
        return this.serviceAddress;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Review)) {
            return false;
        }
        Review review = (Review) o;
        return productId == review.productId && reviewId == review.reviewId && Objects.equals(author, review.author) && Objects.equals(subject, review.subject) && Objects.equals(content, review.content) && Objects.equals(serviceAddress, review.serviceAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, reviewId, author, subject, content, serviceAddress);
    }

    @Override
    public String toString() {
        return "{" +
            " productId='" + getProductId() + "'" +
            ", reviewId='" + getReviewId() + "'" +
            ", author='" + getAuthor() + "'" +
            ", subject='" + getSubject() + "'" +
            ", content='" + getContent() + "'" +
            ", serviceAddress='" + getServiceAddress() + "'" +
            "}";
    }

}


