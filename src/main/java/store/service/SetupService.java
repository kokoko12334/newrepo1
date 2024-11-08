package store.service;

import store.repository.ProductRepository;
import store.repository.PromotionRepository;

public class SetupService {

    public static ProductService load() {
        PromotionRepository promotionRepository = new PromotionRepository();
        ProductRepository productRepository = new ProductRepository(promotionRepository);
        return new ProductService(productRepository);
    }
}
