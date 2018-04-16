package caffeToolAPI.security.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * Created by pc-mg on 4/12/2018.
 */
public class AccountDisabledException extends AuthenticationException {

    public AccountDisabledException(String message) {
        super(message);
    }
}
