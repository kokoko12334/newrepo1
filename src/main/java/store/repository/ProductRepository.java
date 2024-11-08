package store.repository;

import store.dto.ProductDTO;
import store.dto.PromotionDTO;
import store.util.FileLoader;

import java.util.*;

public class ProductRepository {
    private final Map<String, ProductDTO> products;
    private final PromotionRepository promotions;

    public ProductRepository(PromotionRepository promotions) {
        this.promotions = promotions;
        this.products = loadFile();
    }

    private Map<String, ProductDTO> loadFile() {
        List<String> lines = FileLoader.load("products.md");
        Map<String, ProductDTO> products = new HashMap<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i).split(",");
            ProductDTO product = generateProductDTO(line);
            products.put(product.getCompositeKey(), product);
        }
        return products;
    }

    private ProductDTO generateProductDTO(String[] line) {
        String name = line[0];
        int price = Integer.parseInt(line[1]);
        int quantity = Integer.parseInt(line[2]);
        String promotionName = line[3];
        Optional<PromotionDTO> promotion = Optional.ofNullable(promotions.findById(promotionName));
        return new ProductDTO(name, price, quantity, promotion);
    }

    public void save(ProductDTO product) {
        String key = product.getCompositeKey();
        products.put(key, product);
    }

    public ProductDTO findById(String key) {
        return products.get(key);
    }

    public List<ProductDTO> findAll() {
        return new ArrayList<>(products.values());
    }

    public void update(String key, ProductDTO updatedProduct) {
        products.put(key, updatedProduct);
    }

    public void deleteById(String key) {
        products.remove(key);
    }
}
