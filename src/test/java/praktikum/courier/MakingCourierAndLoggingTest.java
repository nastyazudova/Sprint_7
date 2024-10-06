package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class MakingCourierAndLoggingTest {
    private CourierClient client = new CourierClient();
    private CourierChecks check = new CourierChecks();

    int courierId;

    @After
    public void deleteCourier() {
        if (courierId != 0) {
            ValidatableResponse response = client.delete(courierId);
            check.deleted(response);
        }
    }

    @Test
    @DisplayName("курьера можно создать и залогиниться")
    public void courierMakingAndLogging() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("нельзя создать двух одинаковых курьеров")
    public void courierTwoSame() {
        var courier = new Courier("zastya", "password", "user" );
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);

        var courier1 = new Courier("zastya", "password", "user" );
        ValidatableResponse createResponse1 = client.createCourier(courier1);
        check.checkFailedSameLogin(createResponse1);

        var creds1 = CourierCredentials.fromCourier(courier1);
        ValidatableResponse loginResponse1 = client.logIn(creds1);
        courierId = check.checkLoggedIn(loginResponse1);
    }

    @Test
    @DisplayName("нельзя создать курьера без логина")
    public void cannotCreateWithoutLogin() {
        var courier = Courier.withoutLogin();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkFailed(createResponse);
    }

    @Test
    @DisplayName("нельзя создать курьера без пароля")
    public void cannotCreateWithoutPassword() {
        var courier = Courier.withoutPassword();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkFailed(createResponse);
    }

    @Test
    @DisplayName("можно создать курьера без имени (необязательное поле)")
    public void canCreateWithoutFirstName() {
        var courier = Courier.withoutFirstName();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);
    }

    @Test
    @DisplayName("нельзя залогиниться без логина")
    public void courierLoggingWithoutLogin() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        var creds = CourierCredentials.fromCourierWithoutLogin(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.checkNotLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("нельзя залогиниться без пароля")
    public void courierLoggingWithoutPassword() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        var creds = CourierCredentials.fromCourierWithoutPassword(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.checkNotLoggedIn(loginResponse);
    }



    @Test
    @DisplayName("нельзя залогиниться с неправильным логином")
    public void courierLoggingWithoutWithWrongLogin() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        var creds = CourierCredentials.fromCourierWithWrongLogin(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.checkNotLoggedInWithWrongLoginOrPassword(loginResponse);
    }

    @Test
    @DisplayName("нельзя залогиниться с неправильным паролем")
    public void courierLoggingWithWrongPassword() {
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.checkCreated(createResponse);

        var creds = CourierCredentials.fromCourierWithWrongPassword(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.checkNotLoggedInWithWrongLoginOrPassword(loginResponse);
    }
}
