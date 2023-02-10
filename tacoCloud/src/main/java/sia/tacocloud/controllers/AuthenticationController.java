package sia.tacocloud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sia.tacocloud.dao.UserRepository;
import sia.tacocloud.data.dto.UserDTO;
import sia.tacocloud.data.model.AuthenticationRequest;
import sia.tacocloud.data.model.AuthenticationResponse;
import sia.tacocloud.service.JwtDAO;
import sia.tacocloud.service.MyUserDetailsService;

@RestController
@RequestMapping
public class AuthenticationController {
    @Autowired
    MyUserDetailsService userDetailsService;


    @Autowired
    JwtDAO jwtDAO;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try{authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (DisabledException e){
            throw new Exception("USER _DISABLED",e);
        }
        catch (BadCredentialsException e){
            throw new Exception("INVALID_CREDENTIALS",e);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtDAO.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userDetailsService.save(userDTO));

    }
}
