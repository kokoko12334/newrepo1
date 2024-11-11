package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.OrderItem;
import store.dto.ProductDTO;
import store.repository.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class ProductServiceTest {
    ProductService productService = SetupService.load();

    @Test
    void getAllProductsTest() {
        List<ProductDTO> products = productService.getAllProducts();

        assertThat(products.size()).isEqualTo(18);
    }

    @Test
    void getKeyAboutProductTest() {
        List<String> keys = productService.getKeyAboutProduct("콜라");

        assertThat(keys.get(0)).isEqualTo("콜라:탄산2+1");
        assertThat(keys.get(1)).isEqualTo("콜라:");
    }

    @Test
    void findProductByKeyTest() {
        ProductDTO product = productService.findProductByKey("콜라:탄산2+1");

        assertThat(product.getName()).isEqualTo("콜라");
        assertThat(product.getPromotion().get().getName()).isEqualTo("탄산2+1");
    }

    @Test
    void updateTest() {
        String key = "콜라:탄산2+1";
        ProductDTO product = productService.findProductByKey(key);

        product.setQuantity(0);
        productService.update(key, product);
        ProductDTO productChanged = productService.findProductByKey(key);

        assertThat(productChanged.getQuantity()).isEqualTo(0);
    }

    @Test
    void updateStockTest() {
        OrderItem orderItem = new OrderItem("콜라", 1000, 10, 0, 2, 0);
        LocalDateTime currentDate = DateTimes.now();
        productService.updateStock(List.of(orderItem),currentDate);
        ProductDTO promotionChanged = productService.findProductByKey("콜라:탄산2+1");
        ProductDTO originalChanged = productService.findProductByKey("콜라:");

        assertThat(promotionChanged.getQuantity()).isEqualTo(0);
        assertThat(originalChanged.getQuantity()).isEqualTo(8);
    }

    @ParameterizedTest //구매, 프로모션재고, buy, get, 추가증정여부, 정가결제여부, 증정, 정가결제
    @CsvSource({
            "3, 4, 1, 1, 1, 0, 1, 2",
            "3, 2, 1, 1, 0, 1, 1, 1",
            "5, 5, 2, 1, 0, 2, 1, 2",
            "6, 5, 2, 1, 0, 3, 1, 2",
            "5, 5, 1, 2, 0, 0, 3, 2",
            "4, 4, 4, 2, 0, 4, 0, 0",
            "6, 4, 4, 2, 0, 6, 0, 0",
            "6, 6, 4, 2, 0, 0, 2, 4",
            "10, 7, 2, 1, 0, 4, 2, 4",
            "9, 10, 4, 1, 1, 0, 1, 8",
            "9, 10, 1, 4, 1, 0, 7, 2",
            "9, 5, 1, 4, 0, 4, 4, 1"
    })
    void calculateOrderTest(int purchase, int promotionStock, int buy, int get, int extraGift, int fullPriceQuantity, int gift, int orderQuantity) {
        Map<String, Integer> purchaseInfo = new HashMap<>();
        ProductService productService = SetupService.load();

        OrderItem orderItem = productService.calculateOrder("test",0, promotionStock, purchase, buy, get);

        assertThat(extraGift).isEqualTo(orderItem.getExtraGift());
        assertThat(fullPriceQuantity).isEqualTo(orderItem.getFullPriceQuantity());
        assertThat(gift).isEqualTo(orderItem.getGift());
        assertThat(orderQuantity).isEqualTo(orderItem.getOrderQuantity());

    }
}
