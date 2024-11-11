package store.controller;

import store.domain.OrderItem;
import store.service.ProductService;
import store.service.ReceiptService;
import store.util.RetryInput;
import store.view.InputView;
import store.view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class StoreController {
    private final ProductService productService;
    private final InputView orderInputView;
    private ReceiptService receiptService;

    public StoreController(ProductService productService, InputView orderInputView) {
        this.productService = productService;
        this.orderInputView = orderInputView;
    }

    public void run(LocalDateTime currentDate) {
        productService.printMenu();
        String input = RetryInput.getInput(orderInputView, "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        Map<String, Integer> purchaseInfo = getPurchaseInfo(input);
        List<OrderItem> orderItems = productService.GenerateOrderItems(purchaseInfo, currentDate);
        receiptService = new ReceiptService(orderItems);
        askPromotionAndQuantity();
        boolean useMembership = receiptService.askMembership();
        OutputView outputView = new OutputView(receiptService.getOrderItems());

        outputView.printOrderSummary(useMembership);
        productService.updateStock(receiptService.getOrderItems(), currentDate);
        boolean reBuy = receiptService.askMoreItems();
        if (reBuy) {
            run(currentDate);
        }
    }

    private void askPromotionAndQuantity() {
        receiptService.askAddGift();
        receiptService.askAddQuantity();
    }

    private Map<String, Integer> getPurchaseInfo(String input) {
        List<String> purchases = List.of(input.split(","));
        Map<String, Integer> purchaseInfo = new HashMap<>();
        for (String purchase: purchases) {
            List<String> nameAndQuantity = splitInput(purchase);
            String name = nameAndQuantity.get(0);
            int quantity = Integer.parseInt(nameAndQuantity.get(1));
            purchaseInfo.put(name, quantity);
        }
        return purchaseInfo;
    }

    private List<String> splitInput(String text) {
        String trimmedText;
        trimmedText = text.substring(1, text.length() - 1);
        List<String> hyphenSplited =  Arrays.asList(trimmedText.split("-"));
        String productName = hyphenSplited.get(0);
        String quantity = hyphenSplited.get(1);
        return Arrays.asList(productName, quantity);
    }
}
