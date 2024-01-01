package se.magnus.microservices.composite.product;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;

import se.magnus.microservices.api.core.product.Product;
import se.magnus.microservices.api.core.recommendation.Recommendation;
import se.magnus.microservices.api.core.review.Review;
import se.magnus.microservices.composite.product.services.ProductCompositeIntegrationService;


@SpringBootTest(webEnvironment = RANDOM_PORT)
class ProductCompositeServiceApplicationTests {

	@Autowired
	WebClient webClient;
	@MockBean
	ProductCompositeIntegrationService productComposite;

	private final int PRODUCT_OK=1;
	private final int NO_PRODUCT=2;
	private final int INVALID_PRODUCT=3;
     


	@BeforeEach
	void setUp(){
		when(productComposite.getProduct(PRODUCT_OK)).thenReturn(new Product(1,"sa",1,"AA"));
		when(productComposite.getRecommendations(PRODUCT_OK).thenReturn(singletonList(new Recommendation(1,1,1,"as","asas","asas"))));
		when (productComposite.getReviews(PRODUCT_OK).thenReturn(singletonList(new Review(1,1,"asasa","wewewe","zxzxz","wqwqw"))));
	}


	@Test
	void contextLoads() {
	}

}
