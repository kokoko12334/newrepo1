package store.service;

import org.junit.jupiter.api.Test;
import store.dto.ProductDTO;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductServiceTest {
    PromotionRepository promotionRepository = new PromotionRepository();
    ProductRepository productRepository = new ProductRepository(promotionRepository);
    ProductService productService = new ProductService(productRepository);

    @Test
    void getAllProductsTest() {
        List<ProductDTO> products = productService.getAllProducts();

        assertThat(16).isEqualTo(products.size());
    }

    @Test
    void getKeyAboutProductTest() {
        List<String> keys = productService.getKeyAboutProduct("콜라");

        assertThat("콜라:탄산2+1").isEqualTo(keys.get(0));
        assertThat("콜라:").isEqualTo(keys.get(1));

    }

    @Test
    void findProductByKeyTest() {
        ProductDTO product = productService.findProductByKey("콜라:탄산2+1");

        assertThat("콜라").isEqualTo(product.getName());
        assertThat("탄산2+1").isEqualTo(product.getPromotion().get().getName());
    }

    @Test
    void updateTest() {
        String key = "콜라:탄산2+1";
        ProductDTO product = productService.findProductByKey(key);

        product.setQuantity(0);
        productService.update(key, product);
        ProductDTO productChanged = productService.findProductByKey(key);

        assertThat(0).isEqualTo(productChanged.getQuantity());
    }
}
