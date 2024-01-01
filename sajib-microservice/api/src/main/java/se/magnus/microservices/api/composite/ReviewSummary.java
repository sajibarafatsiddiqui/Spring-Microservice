package se.magnus.microservices.api.composite;
import java.util.Objects;

public class ReviewSummary {
    private final int reviewId;
    private final String author;
    private final String subject;
    public ReviewSummary(int reviewId, String author, String subject) {
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;

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


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ReviewSummary)) {
            return false;
        }
        ReviewSummary reviewSummary = (ReviewSummary) o;
        return reviewId == reviewSummary.reviewId && Objects.equals(author, reviewSummary.author) && Objects.equals(subject, reviewSummary.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, author, subject);
    }

    @Override
    public String toString() {
        return "{" +
            " reviewId='" + getReviewId() + "'" +
            ", author='" + getAuthor() + "'" +
            ", subject='" + getSubject() + "'" +
            "}";
    }
    
}
