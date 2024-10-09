package praktikum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

public class OrderListTest {
    private OrderClient client = new OrderClient();
    private OrderChecks check = new OrderChecks();

    @Test
    @DisplayName("заказ создан")
    public void orderListNotEmpty() {
        ValidatableResponse createResponse = client.getListOfOrders();
        check.orders(createResponse);
    }
}
