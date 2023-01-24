package sia.tacocloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sia.tacocloud.dao.UserRepository;
import sia.tacocloud.data.dto.UserDTO;
import java.util.Arrays;
import java.util.List;
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       List <SimpleGrantedAuthority> roles = null;
        UserDAO user = userRepository.findByUsername(username);
        if(user != null){
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
            return new User(user.getUsername(), user.getPassword(), roles);

        }
        throw new UsernameNotFoundException("User with username " + username +" not found");
    }

    public UserDAO save (UserDTO userDTO) {
        UserDAO user = new UserDAO();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFullName(userDTO.getFullName());
        user.setStreet(userDTO.getStreet());
        user.setCity(userDTO.getCity());
        user.setState(userDTO.getState());
        user.setZip(userDTO.getZip());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return userRepository.save(user);
    }
}
