package store.view;

import store.error.ErrorMessages;
import store.service.ProductService;
import store.service.SetupService;

import java.util.Arrays;
import java.util.List;

public class OrderInputView extends InputView {
    ProductService productService = SetupService.load();

    @Override
    public void validate(String input) {
        String[] splitedInput = input.split(",");
        if (input.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.NOT_VALID_FORMAT.getMessage());
        }
        for (String text: splitedInput) {
            hasBrackets(text);
            validStock(text);
        }
    }

    private void hasBrackets(String text) {
        if (text.charAt(0) != '[' || text.charAt(text.length() - 1) != ']') {
            throw new IllegalArgumentException(ErrorMessages.NOT_VALID_FORMAT.getMessage());
        }
    }

    private void validStock(String text) {
        String trimmedText = text.substring(1, text.length() - 1); // 양쪽에 있는 "[", "]" 제거
        List<String> hyphenSplited =  Arrays.asList(trimmedText.split("-"));
        String productName = hyphenSplited.get(0);
        String quantity = hyphenSplited.get(1);

        validProductName(productName);
        validQuantity(productName, quantity);

    }

    private void validProductName(String productName) {
        List<String> keys = productService.getKeyAboutProduct(productName);
        if (keys.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.PRODUCT_NOT_FOUND.getMessage());
        }
    }

    private void validQuantity(String productName, String quantity) {
        int quantityInt;
        try{
            quantityInt = Integer.parseInt(quantity);
        }catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorMessages.NOT_VALID_FORMAT.getMessage());
        }
        List<String> keys = productService.getKeyAboutProduct(productName);
        checkQuantity(keys, quantityInt);
    }

    private void checkQuantity(List<String> keys, int quantityInt) {
        int sum = 0;
        for (String key: keys) {
            sum +=productService.findProductByKey(key).getQuantity();
        }
        if (sum < quantityInt) {
            throw new IllegalArgumentException(ErrorMessages.EXCEEDS_STOCK_QUANTITY.getMessage());
        }
    }
}
