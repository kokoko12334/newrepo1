package store.repository;

import org.junit.jupiter.api.Test;
import store.dto.ProductDTO;
import store.dto.PromotionDTO;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest {

    @Test
    void loadTest() {
        ProductRepository repository = new ProductRepository();
        String key = "콜라:탄산2+1";

        ProductDTO product = repository.findById(key);

        assertThat(key).isEqualTo(product.getCompositeKey());
        assertThat("콜라").isEqualTo(product.getName());
        assertThat(product.getPromotion()).isInstanceOf(PromotionDTO.class);
        assertThat("탄산2+1").isEqualTo(product.getPromotion().getName());
    }
}
