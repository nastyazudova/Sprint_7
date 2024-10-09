package praktikum.order;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderTest {
    String[] color;

    public OrderTest(String[] color) {

        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] orderData() {
        return new Object[][]{
                { new String[]{"BLACK"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"GREY"}},
                {null}

        };
    }

    private OrderClient client = new OrderClient();
    private OrderChecks check = new OrderChecks();

    @Test
    @DisplayName("заказ создан")
    public void orderMaking() {
        var order = new Order("Naruto", "Uchiha", "Konoha, 142 apt.","4", "89991698319", 5, "2020-06-06",
                "Saske, come back to Konoha", color);
        ValidatableResponse createResponse = client.createOrder(order);
        check.orderCreated(createResponse);
    }



}

