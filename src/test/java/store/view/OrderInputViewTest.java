package store.view;

import camp.nextstep.edu.missionutils.Console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.error.ErrorMessages;

import static org.assertj.core.api.Assertions.*;

public class OrderInputViewTest {
    OrderInputView view;

    @BeforeEach
    void setUp() {
        view = new OrderInputView();
    }

    @AfterEach
    void tearDown() {
        Console.close();
    }

    @Test
    void validateTest() {
        view.validate("[콜라-10],[사이다-3]");
        view.validate("[콜라-1]");
        view.validate("[비타민워터-1],");
    }

    @Test
    void validateFormatExceptionTest() {

        assertThatThrownBy(() -> {
            view.validate("[콜라-10[");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.NOT_VALID_FORMAT.getMessage());

        assertThatThrownBy(() -> {
            view.validate("");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.NOT_VALID_FORMAT.getMessage());

        assertThatThrownBy(() -> {
            view.validate("[콜라-dddd],[사이다-2]");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.NOT_VALID_FORMAT.getMessage());

        assertThatThrownBy(() -> {
            view.validate("[콜라-0].[사이다-10]");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.NOT_VALID_FORMAT.getMessage());
    }

    @Test
    void validateNotFoundExceptionTest() {

        assertThatThrownBy(() -> {
            view.validate("[아무거나-10]");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.PRODUCT_NOT_FOUND.getMessage());

        assertThatThrownBy(() -> {
            view.validate("[사이다-10],[아무-2]");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.PRODUCT_NOT_FOUND.getMessage());
    }

    @Test
    void validateQuantityExceptionTest() {

        assertThatThrownBy(() -> {
            view.validate("[콜라-10],[사이다-9999]");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.EXCEEDS_STOCK_QUANTITY.getMessage());

        assertThatThrownBy(() -> {
            view.validate("[콜라-99],[사이다-10]");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.EXCEEDS_STOCK_QUANTITY.getMessage());

        assertThatThrownBy(() -> {
            view.validate("[콜라-999],[사이다-9999]");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessages.EXCEEDS_STOCK_QUANTITY.getMessage());
    }
}
