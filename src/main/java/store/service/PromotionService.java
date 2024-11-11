package store.service;

import store.dto.PromotionDTO;
import store.repository.PromotionRepository;

import java.util.List;
import java.util.Map;

public class PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public List<PromotionDTO> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public PromotionDTO findPromotionByName(String name) {
        return promotionRepository.findById(name);
    }
}
