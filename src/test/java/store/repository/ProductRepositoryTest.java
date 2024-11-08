package store.repository;

import org.junit.jupiter.api.Test;
import store.dto.ProductDTO;
import store.dto.PromotionDTO;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;

public class ProductRepositoryTest {

    @Test
    void loadTest() {
        PromotionRepository promotions = new PromotionRepository();
        ProductRepository products = new ProductRepository(promotions);
        String key = "콜라:탄산2+1";

        ProductDTO product = products.findById(key);

        assertThat(key).isEqualTo(product.getCompositeKey());
        assertThat("콜라").isEqualTo(product.getName());
        assertThat(product.getPromotion()).containsInstanceOf(PromotionDTO.class);
        assertThat("탄산2+1").isEqualTo(product.getPromotion().get().getName());
    }
}
