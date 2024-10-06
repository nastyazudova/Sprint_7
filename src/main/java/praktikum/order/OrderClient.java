package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;

public class OrderClient extends Client {
    private static final String ORDER_PATH = "orders";

    @Step("создать заказ")
    public ValidatableResponse createOrder(praktikum.order.Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then().log().all();
    }

    @Step("получить список заказов")
    public ValidatableResponse getListOfOrders() {
        return spec()
                .when()
                .get(ORDER_PATH)
                .then().log().all();
    }
}