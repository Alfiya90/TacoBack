package sia.tacocloud.security;

import org.hibernate.persister.walking.spi.WalkingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sia.tacocloud.dao.UserRepository;

import sia.tacocloud.data.model.JwtAuthenticationEntryPoint;
import sia.tacocloud.data.model.MyAuthenticationFilter;
import sia.tacocloud.service.MyUserDetailsService;
import sia.tacocloud.service.UserDAO;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyAuthenticationFilter authenticationFilter;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    @Lazy
    private MyUserDetailsService userDetailsService;
    @Bean
    public PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();// метод шифрования
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return  super.authenticationManagerBean();
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







    public void configure (HttpSecurity http) throws Exception {
        http.cors(withDefaults())
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/showingredients").hasRole("USER")
                .antMatchers("/register", "/authenticate", "/registration").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
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
