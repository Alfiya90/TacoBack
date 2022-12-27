package sia.tacocloud.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.swing.*;

public interface UserDetailService {

    UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException;
}
