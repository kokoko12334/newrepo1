package store;

import camp.nextstep.edu.missionutils.DateTimes;
import store.controller.StoreController;
import store.service.ProductService;
import store.service.SetupService;
import store.view.InputView;
import store.view.OrderInputView;

import java.time.LocalDateTime;


public class Application {
    public static void main(String[] args) {
        ProductService productService = SetupService.load();
        InputView orderInputView = new OrderInputView(productService);
        StoreController controller = new StoreController(productService, orderInputView);
        LocalDateTime currentDate = DateTimes.now();
        controller.run(currentDate);
    }
}
