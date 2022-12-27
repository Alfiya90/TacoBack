package sia.tacocloud.service;


import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
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
 @Override
 public boolean isAccountNonExpired() {
  return true;
 }

 @Override
 public boolean isAccountNonLocked() {
  return false;
 }

 @Override
 public boolean isCredentialsNonExpired() {
  return true;
 }

 @Override
 public boolean isEnabled() {
  return true;
 }

}
