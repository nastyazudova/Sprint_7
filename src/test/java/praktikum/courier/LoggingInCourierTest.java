package praktikum.courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;


public class LoggingInCourierTest {
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
    @DisplayName("курьер может залогиниться")
    public void courierMakingAndLogging() {
        var courier = Courier.random();
        client.createCourier(courier);


        var creds = CourierCredentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.checkLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("нельзя залогиниться без логина")
    public void courierLoggingWithoutLogin() {
        var courier = Courier.random();
        client.createCourier(courier);

        var creds = CourierCredentials.fromCourierWithoutLogin(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.checkNotLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("нельзя залогиниться без пароля")
    public void courierLoggingWithoutPassword() {
        var courier = Courier.random();
        client.createCourier(courier);

        var creds = CourierCredentials.fromCourierWithoutPassword(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.checkNotLoggedIn(loginResponse);
    }

    @Test
    @DisplayName("нельзя залогиниться с неправильным логином")
    public void courierLoggingWithoutWithWrongLogin() {
        var courier = Courier.random();
        client.createCourier(courier);

        var creds = CourierCredentials.fromCourierWithWrongLogin(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.checkNotLoggedInWithWrongLoginOrPassword(loginResponse);
    }

    @Test
    @DisplayName("нельзя залогиниться с неправильным паролем")
    public void courierLoggingWithWrongPassword() {
        var courier = Courier.random();
        client.createCourier(courier);

        var creds = CourierCredentials.fromCourierWithWrongPassword(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        check.checkNotLoggedInWithWrongLoginOrPassword(loginResponse);
    }
}
