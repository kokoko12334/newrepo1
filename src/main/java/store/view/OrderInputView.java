package store.view;

import store.error.ErrorMessages;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderInputView extends InputView {
    @Override
    public void validate(String input) {
        String[] splitedInput = input.split(",");

        for (String text: splitedInput) {
            hasBrackets(text);
            validStock(text);
        }

    }

    private void hasBrackets(String text) {
        if (text.charAt(0) != '[' || text.charAt(text.length()) != ']') {
            throw new IllegalArgumentException(ErrorMessages.NOT_VALID_FORMAT.getMessage());
        }
    }

    private void validStock(String text) {
        String trimmedText = text.substring(1, text.length() - 1); // 양쪽에 있는 "[", "]" 제거
        List<String> hyphenSplited =  Arrays.asList(trimmedText.split("-"));
        String productName = hyphenSplited.getFirst();
        String quantity = hyphenSplited.getLast();
        validProductName(productName);
        validQuantity(quantity);

    }

    private boolean validProductName(String productName) {
        return true;
    }

    private boolean validQuantity(String quantity) {
        return true;
    }

}
