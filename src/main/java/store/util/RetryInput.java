package store.util;

import store.view.InputView;

public class RetryInput {
    public static String getInput(InputView view, String message) {
        String input;
        while (true) {
            try {
                input = view.read(message);
                break;
            }catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        return input;
    }
}
