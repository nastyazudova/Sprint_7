package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;
import java.util.Set;


import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.junit.Assert.*;

public class OrderChecks {
    @Step("заказ создался")
    public void orderCreated(ValidatableResponse loginResponse) {
        var body = loginResponse
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .body().as(Map.class);

        assertEquals(Set.of("track"), body.keySet());
    }

    @Step("появился список заказов")
    public void orders(ValidatableResponse loginResponse) {
        var body = loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .body().as(Map.class);
        assertFalse(body.isEmpty());
    }
}
