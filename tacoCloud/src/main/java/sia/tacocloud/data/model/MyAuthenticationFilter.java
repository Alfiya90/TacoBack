package sia.tacocloud.data.model;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sia.tacocloud.service.JwtDAO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFilter extends OncePerRequestFilter {
@Autowired
    JwtDAO jwtDAO;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
       try { String jwtToken = extractTokenFromRequest(request);
        if(StringUtils.hasText(jwtToken) && jwtDAO.validateToken(jwtToken)){
            UserDetails userDetails = new User(jwtDAO.getUsernameFromToken(jwtToken), "", jwtDAO.getRolesFromToken(jwtToken));
            UsernamePasswordAuthenticationToken UsernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, "", jwtDAO.getRolesFromToken(jwtToken));
            SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken);
        } else {
            System.out.println("Cannot set the Security Context");
            
        }} catch (ExpiredJwtException e) {
           request.setAttribute("exception", e);
       } catch (BadCredentialsException e) {
           request.setAttribute("exception", e);
       }
        filterChain.doFilter(request, response);
    }



    public String extractTokenFromRequest (HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7, bearerToken.length());
        }
        return  "You need authorization";
    }

}
