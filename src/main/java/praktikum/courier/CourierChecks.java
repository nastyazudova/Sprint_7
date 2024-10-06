package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;
import java.util.Set;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;

public class CourierChecks {
    @Step("создался успешно")
    public void checkCreated(ValidatableResponse response) {
        boolean created = response
                .assertThat()
                .statusCode(HTTP_CREATED)
                .extract()
                .path("ok");
        assertTrue(created);
    }

    @Step("создать не получилось")
    public void checkFailed(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);

        assertEquals("Недостаточно данных для создания учетной записи", body.get("message"));
        assertEquals(Set.of("message"), body.keySet());
    }

    @Step("создать не получилось, такой логин уже есть")
    public void checkFailedSameLogin(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .extract()
                .body().as(Map.class);

        assertEquals("Этот логин уже используется", body.get("message"));
        assertEquals(Set.of("message"), body.keySet());
    }

    @Step("залогинился")
    public int checkLoggedIn(ValidatableResponse loginResponse) {
        int id = loginResponse
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("id");

        assertNotEquals(0, id);

        return id;
    }

    @Step("не залогинился без логина или пароля")
    public void checkNotLoggedIn(ValidatableResponse loginResponse) {
        var body = loginResponse
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .extract()
                .body().as(Map.class);

        assertEquals("Недостаточно данных для входа", body.get("message"));
        assertEquals(Set.of("message"), body.keySet());
    }

    @Step("не залогинился с неправильным логином или паролем")
    public void checkNotLoggedInWithWrongLoginOrPassword(ValidatableResponse loginResponse) {
        var body = loginResponse
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .extract()
                .body().as(Map.class);

        assertEquals("Учетная запись не найдена", body.get("message"));
        assertEquals(Set.of("message"), body.keySet());
    }

    @Step("удалился")
    public void deleted(ValidatableResponse response) {
        boolean created = response
                .assertThat()
                .statusCode(HTTP_OK)
                .extract()
                .path("ok");
        assertTrue(created);
    }
}