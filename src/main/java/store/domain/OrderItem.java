package store.domain;

public class OrderItem {
    private final String name;
    private final int price;
    private int orderQuantity;
    private int fullPriceQuantity;
    private int gift;
    private int extraGift;

    public OrderItem(String name, int price, int orderQuantity, int fullPriceQuantity, int gift, int extraGift) {
        this.name = name;
        this.price = price;
        this.orderQuantity = orderQuantity;
        this.fullPriceQuantity = fullPriceQuantity;
        this.gift = gift;
        this.extraGift = extraGift;
    }

    public boolean checkFullPrice() {
        return fullPriceQuantity != 0;
    }

    public boolean checkExtraGift() {
        return extraGift != 0;
    }

    public void addGift() {
        gift += extraGift;
    }
    public void addOrderQuantity() {
        orderQuantity += fullPriceQuantity;
    }

    public String getName() {
        return name;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public int getFullPriceQuantity() {
        return fullPriceQuantity;
    }

    public int getGift() {
        return gift;
    }

    public int getExtraGift() {
        return extraGift;
    }

    public int getPrice() {
        return price;
    }
}
