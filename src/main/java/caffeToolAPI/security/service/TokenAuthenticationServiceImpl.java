package caffeToolAPI.security.service;

import caffeToolAPI.security.TokenDto;
import caffeToolAPI.security.TokenHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pc-mg on 3/13/2018.
 */
@Service
public class TokenAuthenticationServiceImpl implements TokenAuthenticationService{

    @Autowired
    private TokenHelper tokenHelper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void addAuthentication(HttpServletResponse response, Authentication authentication) {
        String user = authentication.getName();
        TokenDto jwt = tokenHelper.build(user);
        try {
            response.getWriter().write(objectMapper.writeValueAsString(jwt));
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(tokenHelper.getTOKEN_HEADER());

        if(token != null && token.startsWith(tokenHelper.getTOKEN_PREFIX())){
            UserDetails user = null;
            try {
                user = tokenHelper.parse(token);
            }catch (ExpiredJwtException ex){
                ex.printStackTrace();
            }catch (UnsupportedJwtException ex){
                ex.printStackTrace();
            }catch (MalformedJwtException ex){
                ex.printStackTrace();
            }catch (SignatureException ex){
                ex.printStackTrace();
            }catch (IllegalArgumentException ex){
                ex.printStackTrace();
            }
            if(user != null){
                return new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
            }
            else {
                return null;
            }
        }

        return null;
    }
}
