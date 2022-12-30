package sia.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import sia.tacocloud.dao.UserRepository;
import sia.tacocloud.service.UserDetailService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();   // метод шифрования
    }

  /*  @Bean
    public InMemoryUserDetailsManager userDetailService (PasswordEncoder encoder){
        List <UserDetails> userList = new ArrayList<>();
        userList.add(new User("buzz", encoder.encode("password"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
        userList.add( new  User("woody", encoder.encode("password"),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
        return  new InMemoryUserDetailsManager(userList);

    }*/


    @Bean
    public UserDetailService userDetailService (UserRepository userRepo) {
        return username -> {
            sia.tacocloud.service.User user = userRepo.findByUsername(username);
            if( user != null) return user;
            throw new UsernameNotFoundException("User with '" + username + "' not found");
        };
    }

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception {
        return http
                .cors(withDefaults())
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/register", "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")// определяет путь к странице входа
                .and()
                .build();
       /* return http
                .csrf()
                .disable()
                .authorizeRequests()
                //Доступ для не зарегистрированных пользователей
                .antMatchers("/registration/email/",
                        "/login",
                        "/user/config",
                        "/logout",
                        "/authentication-fail",
                        "/dic/regions",
                        "/images/").permitAll()
                //Все остальные страницы требуют аутентификации
                .anyRequest().authenticated()
                .and()
                //Настройка для входа в систему
                .formLogin()
                .loginPage("/login")
                .successHandler(successHandler())
                //Перенаправление на страницу c ошибкой при ошибке
                .failureHandler(failureHandler())
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll();*/
    }



}
