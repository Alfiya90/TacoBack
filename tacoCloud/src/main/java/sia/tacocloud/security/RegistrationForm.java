package sia.tacocloud.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import sia.tacocloud.service.UserDAO;

@Data
public class RegistrationForm {
    private String userName;
    private String password;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    public UserDAO toUser(PasswordEncoder passwordEncoder) {
        return new UserDAO( userName, passwordEncoder.encode(password), fullName,
                street, city, state, zip, phoneNumber);
    }
}
