package store.service;

import store.dto.ProductDTO;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll();
    }

    public List<String> getKeyAboutProduct(String name) {
        List<String> keys = new ArrayList<>();
        List<ProductDTO> products = productRepository.findAll();
        for (ProductDTO product: products) {
            if (product.getName().equals(name)) {
                keys.add(product.getCompositeKey());
            }
        }
        return keys;
    }

    public ProductDTO findProductByKey(String key) {
        return productRepository.findById(key);
    }

    public void update(String key, ProductDTO product) {
        productRepository.update(key, product);
    }

}
