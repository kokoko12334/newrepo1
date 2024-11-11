package store.dto;

import java.util.Optional;

public class ProductDTO {
    private String name;
    private int price;
    private int quantity;
    private Optional<PromotionDTO> promotion;

    public ProductDTO(String name, int price, int quantity, Optional<PromotionDTO> promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Optional<PromotionDTO> getPromotion() {
        return promotion;
    }

    public void setPromotion(Optional<PromotionDTO> promotion) {
        this.promotion = promotion;

    }

    public String getCompositeKey() {
        return name + ":" + promotion.map(PromotionDTO::getName).orElse("");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("- " + name + " " + String.format("%,d", price) + "원 ");
        if (quantity > 0) {
            result.append(String.format("%,d", quantity)).append("개");
        } else {
            result.append("재고 없음");
        }

        promotion.ifPresent(promo -> result.append(" ").append(promo.getName()));
        return result.toString();
    }
}
