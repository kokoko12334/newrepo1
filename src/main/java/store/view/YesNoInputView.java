package store.view;

import store.error.ErrorMessages;

public class YesNoInputView extends InputView {
    @Override
    public void validate(String input) {
        if (!input.equals("Y") && !input.equals("N")) {
            throw new IllegalArgumentException(ErrorMessages.NOT_VALID_FORMAT.getMessage());
        }
    }
}
