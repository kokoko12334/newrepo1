package store.service;

import camp.nextstep.edu.missionutils.DateTimes;
import store.domain.OrderItem;
import store.dto.ProductDTO;
import store.repository.ProductRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    public void updateStock(List<OrderItem> orderItems, LocalDateTime currentDate) {
        for (OrderItem orderItem : orderItems) {
            int totalPurchase = orderItem.getOrderQuantity() + orderItem.getGift();
            ProductDTO promotionProduct = productRepository.getPromotionProductByName(orderItem.getName());
            ProductDTO originalProduct = productRepository.getProductByName(orderItem.getName());
            int promotionStock = getStockQuantity(promotionProduct, currentDate);
            int originalStock = originalProduct.getQuantity();

            List<Integer> updatedStocks = calculateStock(promotionStock, originalStock, totalPurchase);
            updateProductStock(promotionProduct, updatedStocks.get(0));
            updateProductStock(originalProduct, updatedStocks.get(1));
        }
    }

    private boolean checkDate(ProductDTO product, LocalDateTime currentDate) {
        LocalDateTime startDate = product.getPromotion().get().getStartDate();
        LocalDateTime endDate = product.getPromotion().get().getEndDate();
        return !currentDate.isBefore(startDate) && !currentDate.isAfter(endDate);
    }

    private int getStockQuantity(ProductDTO product, LocalDateTime currentDate) {
        if (product == null || !checkDate(product, currentDate)) {
            return 0;
        }
        return product.getQuantity();
    }

    private List<Integer> calculateStock(int promotionStock, int originalStock, int totalPurchase) {
        if (promotionStock >= totalPurchase) {
            return List.of(promotionStock - totalPurchase, originalStock);
        }
        return List.of(0, originalStock - (totalPurchase - promotionStock));
    }

    private void updateProductStock(ProductDTO product, int updatedQuantity) {
        if (product == null) return;
        product.setQuantity(updatedQuantity);
        productRepository.update(product.getCompositeKey(), product);
    }

    public List<OrderItem> GenerateOrderItems(Map<String, Integer> purchaseInfo, LocalDateTime currentDate) {
        OrderItem orderItem;
        List<OrderItem> orderItems = new ArrayList<>();

        for (Map.Entry<String, Integer> entry: purchaseInfo.entrySet()) {
            orderItem = generateEach(entry.getKey(), entry.getValue(), currentDate);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    public OrderItem generateEach(String name, int purchase, LocalDateTime currentDate) {
        ProductDTO promotionProduct = productRepository.getPromotionProductByName(name);
        ProductDTO product = productRepository.getProductByName(name);
        if (promotionProduct != null && checkDate(promotionProduct, currentDate)) {
            int promotionStock = promotionProduct.getQuantity();
            int buy = promotionProduct.getPromotion().get().getBuy();
            int get = promotionProduct.getPromotion().get().getGet();
            return calculateOrder(name, product.getPrice(), promotionStock, purchase, buy, get);
        }
        return new OrderItem(name, product.getPrice(), purchase, 0, 0,0);
    }

    public OrderItem calculateOrder(String name, int price, int promotionStock, int purchase, int buy, int get) {
        int orderQuantity = 0;
        int gift = 0;
        int fullPriceQuantity = 0;
        int extraGift = 0;

        while (purchase > 0) {
            if (promotionStock - (orderQuantity + gift) > 0) {
                if (promotionStock - (orderQuantity + gift) >= buy + get) {
                    orderQuantity += buy;
                    purchase -= buy;
                    gift += get;
                    purchase -= get;
                }else{
                    if (purchase >= buy && (promotionStock - (orderQuantity + gift + buy) > 0)) {
                        orderQuantity+= buy;
                        purchase -= buy;
                        gift += promotionStock - (orderQuantity + gift);
                        purchase = 0;
                    }else {
                        fullPriceQuantity += purchase;
                        purchase = 0;
                    }
                }
            }else {
                fullPriceQuantity += purchase;
                purchase = 0;
            }
        }
        if (purchase < 0) {
            extraGift = -purchase;
            gift -= extraGift;
            purchase = 0;
        }
        return new OrderItem(name, price, orderQuantity, fullPriceQuantity, gift, extraGift);
    }

    public void printMenu() {
        List<ProductDTO> products = productRepository.findAll();
        Map<String, List<ProductDTO>> groupedProducts = new LinkedHashMap<>();
        for (ProductDTO product : products) {
            groupedProducts.computeIfAbsent(product.getName(), k -> new ArrayList<>()).add(product);
        }
        System.out.println("안녕하세요. W편의점입니다.\n현재 보유하고 있는 상품입니다.\n");
        for (Map.Entry<String, List<ProductDTO>> entry : groupedProducts.entrySet()) {
            String productName = entry.getKey();
            List<ProductDTO> productList = entry.getValue();
            for (ProductDTO product : productList) {
                System.out.println(product);
            }
        }
    }
}
