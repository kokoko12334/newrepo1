package store.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.dto.PromotionDTO;
import store.repository.PromotionRepository;

import java.util.List;

public class PromotionServiceTest {
    PromotionRepository promotionRepository = new PromotionRepository();
    PromotionService promotionService = new PromotionService(promotionRepository);

    @Test
    void getAllPromotionsTest() {
        List<PromotionDTO> promotions = promotionService.getAllPromotions();

        Assertions.assertThat(3).isEqualTo(promotions.size());
    }

    @Test
    void findPromotionByNameTest() {
        PromotionDTO promotion = promotionService.findPromotionByName("반짝할인");

        Assertions.assertThat("반짝할인").isEqualTo(promotion.getName());
        Assertions.assertThat(1).isEqualTo(promotion.getBuy());
        Assertions.assertThat(1).isEqualTo(promotion.getGet());
    }
}
