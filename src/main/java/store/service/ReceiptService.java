package store.service;

import store.domain.OrderItem;

import store.util.RetryInput;
import store.view.InputView;
import store.view.YesNoInputView;

import java.util.ArrayList;
import java.util.List;


public class ReceiptService {
    private static final String QUANTITY_MESSAGE = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)";
    private static final String GIFT_MESSAGE = "현재 %s은(는) %d개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)";
    private static final String MEMBERSHIP_MESSAGE = "멤버십 할인을 받으시겠습니까? (Y/N)";
    private static final String MORE_MESSAGE = "감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)";

    private final List<OrderItem> orderItems;
    private final InputView yesNoInputView = new YesNoInputView();

    public ReceiptService(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems);
    }

    public void askAddGift() {
        for (OrderItem orderItem: orderItems) {
            if (orderItem.checkExtraGift()) {
                String message = String.format(GIFT_MESSAGE, orderItem.getName(), orderItem.getExtraGift());
                String answer = RetryInput.getInput(yesNoInputView, message);
                if (answer.equals("Y")) {
                    orderItem.addGift();
                }
            }
        }
    }

    public void askAddQuantity() {
        for (OrderItem orderItem: orderItems) {
            if (orderItem.checkFullPrice()) {
                String message = String.format(QUANTITY_MESSAGE, orderItem.getName(), orderItem.getFullPriceQuantity());
                String answer = RetryInput.getInput(yesNoInputView, message);
                if (answer.equals("Y")) {
                    orderItem.addOrderQuantity();
                }
            }
        }
    }

    public boolean askMembership() {
        String answer = RetryInput.getInput(yesNoInputView, MEMBERSHIP_MESSAGE);
        return answer.equals("Y");
    }

    public boolean askMoreItems() {
        String answer = RetryInput.getInput(yesNoInputView, MORE_MESSAGE );
        return answer.equals("Y");
    }

}