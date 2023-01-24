package sia.tacocloud.service;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import java.util.*;

@Data
@Component
public class JwtDAO {

    private String secret;
    private int jwtExpirationInMs;
    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }
    @Value("${jwt.jwtExpirationInMs}")
    public void setJwtExpirationInMs(int jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
        if(roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            claims.put("isAdmin", true);
        }
        if(roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            claims.put("isUser", true);
        }
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public  String doGenerateToken (Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis()+ jwtExpirationInMs) )
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public  boolean validateToken(String authToken) {
      try{
          Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
      return true;
      } catch (SignatureException|MalformedJwtException|UnsupportedJwtException|IllegalArgumentException e){
          throw new BadCredentialsException("INVALID_CREDENTIALS", e);
      } catch (ExpiredJwtException e){
          throw e;
      }
    }

    public String getUsernameFromToken(String token){
       Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
       return claims.getSubject();
    }

    public List<SimpleGrantedAuthority> getRolesFromToken(String token){
        List<SimpleGrantedAuthority> roles = null;
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        Boolean isAdmin = claims.get("isAdmin", Boolean.class);
        Boolean isUser = claims.get("isUser", Boolean.class);
        if(isAdmin != null && isAdmin ) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if(isUser != null && isUser ) {
            roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return  roles;
    }


}
