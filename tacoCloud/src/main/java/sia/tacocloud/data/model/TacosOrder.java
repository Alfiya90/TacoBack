package sia.tacocloud.data.model;



import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Component
public class TacosOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private LocalDate placedAt;
    @NotBlank (message = "Delivery name is required")
    private  String deliveryName;
    @NotBlank (message = "Street is required")
    private  String deliveryStreet;
    @NotBlank (message = "City is required")
    private  String deliveryCity;
    @NotBlank (message = "State is required")
    private  String deliveryState;
    @NotBlank (message = "Zip is required")
    private  String deliveryZip;
    @CreditCardNumber(message = "Number of card is not valid")
    private  String ccNumber;
    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Must be formatted MM/YY")
    private  String ccExpiration;
    @Digits(integer = 3, fraction = 0, message = "Invalid CVV")
    private  String ccCVV;
    private List <Taco> tacos = new ArrayList<>();

     public void addTaco(Taco taco) {
         tacos.add(taco);
    }
    public TacosOrder(){
    }

    public TacosOrder(String deliveryName, String deliveryStreet, String deliveryCity, String deliveryState, String deliveryZip, String ccNumber, String ccExpiration, String ccCVV, List<Taco> tacos) {
        this.deliveryName = deliveryName;
        this.deliveryStreet = deliveryStreet;
        this.deliveryCity = deliveryCity;
        this.deliveryState = deliveryState;
        this.deliveryZip = deliveryZip;
        this.ccNumber = ccNumber;
        this.ccExpiration = ccExpiration;
        this.ccCVV = ccCVV;
        this.tacos = tacos;
    }
    public TacosOrder(String deliveryName, String deliveryStreet, String deliveryCity, String deliveryState, String deliveryZip, String ccNumber, String ccExpiration, String ccCVV) {
        this.deliveryName = deliveryName;
        this.deliveryStreet = deliveryStreet;
        this.deliveryCity = deliveryCity;
        this.deliveryState = deliveryState;
        this.deliveryZip = deliveryZip;
        this.ccNumber = ccNumber;
        this.ccExpiration = ccExpiration;
        this.ccCVV = ccCVV;

    }


    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryStreet() {
        return deliveryStreet;
    }

    public void setDeliveryStreet(String deliveryStreet) {
        this.deliveryStreet = deliveryStreet;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryState() {
        return deliveryState;
    }

    public void setDeliveryState(String deliveryState) {
        this.deliveryState = deliveryState;
    }

    public String getDeliveryZip() {
        return deliveryZip;
    }

    public void setDeliveryZip(String deliveryZip) {
        this.deliveryZip = deliveryZip;
    }

    public String getCcNumber() {
        return ccNumber;
    }

    public void setCcNumber(String ccNumber) {
        this.ccNumber = ccNumber;
    }

    public String getCcExpiration() {
        return ccExpiration;
    }

    public void setCcExpiration(String ccExpiration) {
        this.ccExpiration = ccExpiration;
    }

    public String getCcCVV() {
        return ccCVV;
    }

    public void setCcCVV(String ccCVV) {
        this.ccCVV = ccCVV;
    }

    public List<Taco> getTacos() {
        return tacos;
    }

    public void setTacos(List<Taco> tacos) {
        this.tacos = tacos;
    }
}
