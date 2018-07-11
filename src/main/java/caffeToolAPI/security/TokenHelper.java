package caffeToolAPI.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

/**
 * Created by pc-mg on 3/13/2018.
 */
@Component
public class TokenHelper {

    @Value("${jwt.expires_in}")
    private int EXPIRATION_TIME;

    @Value("${jwt.secret}")
    private String SECRET;

    @Value("${jwt.token_prefix}")
    private String TOKEN_PREFIX;

    @Value("${jwt.header}")
    private String TOKEN_HEADER;

    @Autowired
    private UserDetailsService userDetailsService;

    public TokenDto build(String username) {
        Date now = new Date();

        String JWT = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        return new TokenDto(TOKEN_PREFIX + JWT, username);
    }

    public UserDetails parse(String token) {
        String username = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        return userDetailsService.loadUserByUsername(username);
    }


    public int getEXPIRATION_TIME() {
        return EXPIRATION_TIME;
    }

    public String getSECRET() {
        return SECRET;
    }

    public String getTOKEN_PREFIX() {
        return TOKEN_PREFIX;
    }

    public String getTOKEN_HEADER() {
        return TOKEN_HEADER;
    }
}
