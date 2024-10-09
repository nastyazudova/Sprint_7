package praktikum.courier;

public class CourierCredentials {
    private final String login;
    private final String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials fromCourier(praktikum.courier.Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword());
    }

    public static CourierCredentials fromCourierWithoutLogin(praktikum.courier.Courier courier) {
        return new CourierCredentials(null, courier.getPassword());
    }

    public static CourierCredentials fromCourierWithoutPassword(praktikum.courier.Courier courier) {
        return new CourierCredentials(courier.getLogin(), null);
    }

    public static CourierCredentials fromCourierWithWrongLogin(praktikum.courier.Courier courier) {
        return new CourierCredentials(courier.getLogin() + "a1", courier.getPassword());
    }

    public static CourierCredentials fromCourierWithWrongPassword(praktikum.courier.Courier courier) {
        return new CourierCredentials(courier.getLogin(), courier.getPassword() + "a1");
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}