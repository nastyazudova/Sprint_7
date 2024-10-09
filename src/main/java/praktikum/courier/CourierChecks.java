package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.Map;
import java.util.Set;

import static java.net.HttpURLConnection.*;
import static org.junit.Assert.*;
import org.assertj.core.api.SoftAssertions;

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


        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(body.get("message"))
                .isEqualTo("Недостаточно данных для создания учетной записи");
        soft.assertThat(body.keySet())
                .isEqualTo(Set.of("message"));
        soft.assertAll();
    }

    @Step("создать не получилось, такой логин уже есть")
    public void checkFailedSameLogin(ValidatableResponse response) {
        var body = response
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .extract()
                .body().as(Map.class);

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(body.get("message"))
                .isEqualTo("Этот логин уже используется");
        soft.assertThat(body.keySet())
                .isEqualTo(Set.of("message"));
        soft.assertAll();

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

        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(body.get("message"))
                .isEqualTo("Недостаточно данных для входа");
        soft.assertThat(body.keySet())
                .isEqualTo(Set.of("message"));
        soft.assertAll();

    }

    @Step("не залогинился с неправильным логином или паролем")
    public void checkNotLoggedInWithWrongLoginOrPassword(ValidatableResponse loginResponse) {
        var body = loginResponse
                .assertThat()
                .statusCode(HTTP_NOT_FOUND)
                .extract()
                .body().as(Map.class);


        SoftAssertions soft = new SoftAssertions();
        soft.assertThat(body.get("message"))
                .isEqualTo("Учетная запись не найдена");
        soft.assertThat(body.keySet())
                .isEqualTo(Set.of("message"));
        soft.assertAll();
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