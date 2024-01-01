package se.magnus.microservices.api.composite;
import java.util.Objects;

public class RecommendationSummary {
    private final int recommendationId;
    private final String author;
    private final int rate;

    public RecommendationSummary(int recommendationId, String author, int rate) {
        this.recommendationId = recommendationId;
        this.author = author;
        this.rate = rate;
    }

    public int getRecommendationId() {
        return this.recommendationId;
    }


    public String getAuthor() {
        return this.author;
    }


    public int getRate() {
        return this.rate;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RecommendationSummary)) {
            return false;
        }
        RecommendationSummary recommendationSummary = (RecommendationSummary) o;
        return recommendationId == recommendationSummary.recommendationId && Objects.equals(author, recommendationSummary.author) && rate == recommendationSummary.rate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recommendationId, author, rate);
    }

    @Override
    public String toString() {
        return "{" +
            " recommendationId='" + getRecommendationId() + "'" +
            ", author='" + getAuthor() + "'" +
            ", rate='" + getRate() + "'" +
            "}";
    }
    
}
