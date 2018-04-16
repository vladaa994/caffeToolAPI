package caffeToolAPI.security;

import caffeToolAPI.dto.UserDto;
import caffeToolAPI.security.exception.AccountDisabledException;
import caffeToolAPI.security.exception.PasswordException;
import caffeToolAPI.security.service.AccountService;
import caffeToolAPI.security.service.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pc-mg on 3/13/2018.
 */
@CrossOrigin
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public JWTLoginFilter(String url, AuthenticationManager authenticationManager){
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

//        //preflight fix
        if(CorsUtils.isPreFlightRequest(request)){
            response.setStatus(HttpServletResponse.SC_OK);
            return null;
        }

        UserDto user = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);
        UserDetails userChecked = accountService.loadUserByUsername(user.getUsername());
        if(userChecked != null) {
            if(!userChecked.isEnabled()){
                throw new AccountDisabledException("This account has been disabled!");
            }
            if(!passwordEncoder.matches(user.getPassword(), userChecked.getPassword())) {
                throw new PasswordException("Password is not correct.");
            }
        }

        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(userChecked.getUsername(), userChecked.getPassword(), userChecked.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        tokenAuthenticationService.addAuthentication(response, authResult);
    }
}
