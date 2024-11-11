package store.repository;

import store.dto.ProductDTO;
import store.dto.PromotionDTO;
import store.util.FileLoader;

import java.util.*;

public class ProductRepository {
    private static ProductRepository instance;
    private final Map<String, ProductDTO> products;
    private final PromotionRepository promotions;

    public ProductRepository(PromotionRepository promotions) {
        this.promotions = promotions;
        this.products = loadFile();
        check();
    }

    private Map<String, ProductDTO> loadFile() {
        List<String> lines = FileLoader.load("products.md");
        Map<String, ProductDTO> products = new LinkedHashMap<>();

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

    public ProductDTO getPromotionProductByName(String name) {
        List<String> keys = getKeyAboutProduct(name);
        for (String key: keys) {
            String[] splited = key.split(":");
            if (splited.length == 2) {
                return findById(key);
            }
        }
        return null;
    }

    public void check() {
        List<String> names = getAllNames();
        for (String name: names) {
            List<String> keys = getKeyAboutProduct(name);
            if (keys.size() == 1 && keys.getFirst().split(":").length == 2) {
                String key = keys.getFirst();
                ProductDTO product = findById(key);
                int price = product.getPrice();
                ProductDTO originalProduct = new ProductDTO(name, price, 0, Optional.empty());
                products.put(originalProduct.getCompositeKey(), originalProduct);
            }
        }
    }

    public List<String> getAllNames() {
        List<ProductDTO> products = findAll();
        Set<String> unique = new HashSet<>();
        for (ProductDTO product: products) {
            unique.add(product.getName());
        }
        return new ArrayList<>(unique);
    }

    public ProductDTO getProductByName(String name) {
        List<String> keys = getKeyAboutProduct(name);
        String keyName = "";
        for (String key: keys) {
            String[] splited = key.split(":");
            if (splited.length == 1) {
                keyName = key;
                break;
            }
        }
        return findById(keyName);
    }

    public List<String> getKeyAboutProduct(String name) {
        List<String> keys = new ArrayList<>();
        List<ProductDTO> products = findAll();
        for (ProductDTO product: products) {
            if (product.getName().equals(name)) {
                keys.add(product.getCompositeKey());
            }
        }
        return keys;
    }

    public ProductDTO findById(String key) {
        return products.get(key);
    }

    public List<ProductDTO> findAll() {
        return new ArrayList<>(products.values());
    }

    public List<String> findAllKeys() {
        return new ArrayList<>(products.keySet());
    }

    public void update(String key, ProductDTO updatedProduct) {
        products.put(key, updatedProduct);
    }
}
