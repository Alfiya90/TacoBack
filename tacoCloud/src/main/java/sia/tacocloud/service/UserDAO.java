package sia.tacocloud.service;


import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class UserDAO implements UserDetails {
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



  /* public User( String userName, String password, String fullName,
                String street, String city, String state, String zip, String phoneNumber) {
    this.userName = userName;
    this. password = password;
    this.fullName = fullName;
    this.street = street;
    this.city = city;
    this.state = state;
    this.zip = zip;
    this.phoneNumber = phoneNumber;
   };*/

// возвращает набор привилегий пользователя.
 public Collection <? extends GrantedAuthority> getAuthorities() {
       return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
   }

   //Актуальность аккаунта

 public boolean isAccountNonExpired() {
  return true;
 }


 public boolean isAccountNonLocked() {
  return false;
 }


 public boolean isCredentialsNonExpired() {
  return true;
 }


 public boolean isEnabled() {
  return true;
 }

    public UserDAO toUser(PasswordEncoder passwordEncoder) {
        return new UserDAO( username, passwordEncoder.encode(password), fullName,
                street, city, state, zip, phoneNumber);
    }
}

