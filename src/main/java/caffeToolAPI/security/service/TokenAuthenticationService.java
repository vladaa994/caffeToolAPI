package caffeToolAPI.security.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pc-mg on 3/13/2018.
 */
public interface TokenAuthenticationService {

    void addAuthentication(HttpServletResponse response, Authentication authentication);
    Authentication getAuthentication(HttpServletRequest request);
}
