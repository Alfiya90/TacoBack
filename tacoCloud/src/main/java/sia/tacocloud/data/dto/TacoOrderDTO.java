package sia.tacocloud.data.dto;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class TacoOrderDTO {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date placedAt;
    @NotBlank (message = "Delivery name is required")
    private String deliveryName;
    @NotBlank (message = "Delivery street is not required")
    private String deliveryStreet;
    private String deliveryCity;
    private String deliveryState;
    private String deliveryZip;

    private String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Must be formatted MM/YY")
    private String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private String ccCVV;
    private List<TacoDTO> tacos = new ArrayList<>();

}
