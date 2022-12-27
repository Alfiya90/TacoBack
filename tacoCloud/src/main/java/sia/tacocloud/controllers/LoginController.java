package sia.tacocloud.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/login")
public class LoginController {

    @CrossOrigin
    @GetMapping
    public  String Login () {
        return  "success";
    }
}
