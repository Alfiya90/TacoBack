package sia.tacocloud.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class UserDTO {
    private  static final  long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long id;
    @NonNull
    private String username;
    @NonNull
    private String password;
    @NonNull
    private String fullName;
    @NonNull
    private String street;
    @NonNull
    private String city;
    @NonNull
    private String state;
    @NonNull
    private String zip;
    @NonNull
    private String phoneNumber;

}
