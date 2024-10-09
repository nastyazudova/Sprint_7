package praktikum.courier;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
@Data
@AllArgsConstructor
public class Courier {
    private final String login;
    private final String password;
    private final String firstName;

    static Courier random() {
        return new Courier("Jack" + RandomStringUtils.randomAlphanumeric(5, 15),
                "P@ssw0rd123", "Sparrow");
    }

    static Courier withoutPassword() {
        return new Courier("Jack" + RandomStringUtils.randomAlphanumeric(5, 15),
                null, "Sparrow");
    }

    static Courier withoutLogin() {
        return new Courier(null,
                "P@ssw0rd123", "Sparrow");
    }

    static Courier withoutFirstName() {
        return new Courier("Jack" + RandomStringUtils.randomAlphanumeric(5, 15),
                "P@ssw0rd123", null);
    }


}