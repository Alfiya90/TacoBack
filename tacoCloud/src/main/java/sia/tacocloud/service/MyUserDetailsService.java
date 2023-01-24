package sia.tacocloud.service;

import net.bytebuddy.asm.MemberSubstitution;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;
        if(username.equals("user")) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            return new User("user", "$2a$10$PCVAk/4MzUApeOnXlOn1S.B2yufDSkgygcXjb/UxyMNS9KHz3JfNC", roles);
        }
        if(username.equals("admin")) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new User("admin", "$2a$10$xmNsK8qWZWAR6qr1aDsr3ehIjHoFSSe2SA8a6ZTIp7Y0tbp9avVWe", roles);

        }
        throw new UsernameNotFoundException("User with username " + username +" not found");
    }
}
