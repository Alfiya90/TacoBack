package sia.tacocloud.security;

import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sia.tacocloud.dao.UserRepository;
import sia.tacocloud.service.User;

import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    public RegistrationController (UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @CrossOrigin
    @GetMapping
    public String registerForm() {
        return "registration";
    }



    @PostMapping
    public  void processRegistration (@RequestBody User user,  HttpServletResponse response)  {
        userRepo.save(user.toUser(passwordEncoder));
        response.setStatus(HttpServletResponse.SC_OK);
    }



}
