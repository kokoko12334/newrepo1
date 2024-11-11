package store.view;

import store.domain.OrderItem;

import java.util.List;

public class OutputView {
    private final List<OrderItem> orderItems;

    public OutputView(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void printOrderSummary(boolean membership) {
        printHeader();
        printOrderItems();
        printGifts();
        int totalQuantity = calculateTotalQuantity();
        int totalPrice = calculateTotalPrice();
        int promotionDiscount = calculatePromotionDiscount();
        int fullPrice = calculateFullPrice();
        int membershipDiscount = calculateMembershipDiscount(membership, fullPrice);

        printSummary(totalQuantity, totalPrice, promotionDiscount, membershipDiscount);
    }

    private void printHeader() {
        System.out.println("==============W 편의점===============");
        System.out.printf("%-10s %-5s %-10s\n", "상품명", "수량", "금액");
    }

    private void printOrderItems() {
        for (OrderItem orderItem : orderItems) {
            System.out.printf("%-10s %,d %,d\n", orderItem.getName(), orderItem.getOrderQuantity() + orderItem.getGift(), orderItem.getPrice() * orderItem.getOrderQuantity());
        }
    }

    private void printGifts() {
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getGift() > 0) {
                System.out.println("=============증정===============");
                System.out.printf("%-10s %,d\n", orderItem.getName(), orderItem.getGift());
            }
        }
    }

    private int calculateTotalQuantity() {
        int totalQuantity = 0;
        for (OrderItem orderItem : orderItems) {
            totalQuantity += orderItem.getOrderQuantity() + orderItem.getGift();
        }
        return totalQuantity;
    }

    private int calculateTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getPrice() * (orderItem.getOrderQuantity() + orderItem.getGift());
        }
        return totalPrice;
    }

    private int calculatePromotionDiscount() {
        int promotionDiscount = 0;
        for (OrderItem orderItem : orderItems) {
            promotionDiscount += orderItem.getPrice() * orderItem.getGift();
        }
        return promotionDiscount;
    }

    private int calculateFullPrice() {
        int fullPrice = 0;
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getGift() == 0) {
                fullPrice += orderItem.getPrice() * orderItem.getOrderQuantity();
            } else {
                fullPrice += orderItem.getPrice() * orderItem.getFullPriceQuantity();
            }
        }
        return fullPrice;
    }

    private int calculateMembershipDiscount(boolean membership, int fullPrice) {
        if (membership) {
            return (int) Math.min(8000, fullPrice * 0.3);
        }
        return 0;
    }

    private void printSummary(int totalQuantity, int totalPrice, int promotionDiscount, int membershipDiscount) {
        System.out.println("====================================");
        System.out.printf("총구매액\t\t %,d \t\t %,d\n",totalQuantity, totalPrice);
        System.out.printf("행사할인\t\t %,d\n", -promotionDiscount);
        System.out.printf("멤버십할인\t\t %,d\n", -membershipDiscount);
        System.out.printf("내실돈\t\t %,d\n",(totalPrice - promotionDiscount - membershipDiscount));
    }
}
