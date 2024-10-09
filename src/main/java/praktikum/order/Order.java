package praktikum.order;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Order {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Number rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;


}
