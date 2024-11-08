package store.view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import store.error.ErrorMessages;

public class YesNoInputViewTest {
    InputView view = new YesNoInputView();

    @Test
    void validTest() {
        view.validate("Y");
        view.validate("N");
    }

    @Test
    void validExceptionTest() {

        Assertions.assertThatThrownBy(() ->{
            view.validate("DDDD");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.NOT_VALID_FORMAT.getMessage());
    }
}
