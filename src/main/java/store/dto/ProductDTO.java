package store.dto;

public class ProductDTO {
    private String name;
    private int price;
    private int quantity;
    private ProductDTO promotion;

    public ProductDTO(String name, int price, int quantity, ProductDTO promotion) {
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

    public ProductDTO getPromotion() {
        return promotion;
    }

    public void setPromotion(ProductDTO promotion) {
        this.promotion = promotion;
    }

    public String getCompositeKey() {
        return name + ":" + promotion.getName();
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", promotion=" + promotion +
                '}';
    }
}
