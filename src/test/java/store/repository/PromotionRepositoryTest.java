package store.repository;

import org.junit.jupiter.api.Test;
import store.dto.PromotionDTO;

import static org.assertj.core.api.Assertions.assertThat;

public class PromotionRepositoryTest {

    @Test
    void loadTest() {
        PromotionRepository promotions = new PromotionRepository();
        String name = "탄산2+1";

        PromotionDTO promotion = promotions.findById(name);

        assertThat(promotion).isInstanceOf(PromotionDTO.class);
        assertThat(name).isEqualTo(promotion.getName());
    }

}
