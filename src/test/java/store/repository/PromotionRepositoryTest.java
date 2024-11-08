package store.repository;

import org.junit.jupiter.api.Test;
import store.dto.PromotionDTO;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionRepositoryTest {

    @Test
    void loadTest() {
        PromotionRepository repository = new PromotionRepository();
        String name = "탄산2+1";

        PromotionDTO promotion = repository.findById(name);

        assertThat(promotion).isInstanceOf(PromotionDTO.class);
        assertThat(name).isEqualTo(promotion.getName());
    }

}
