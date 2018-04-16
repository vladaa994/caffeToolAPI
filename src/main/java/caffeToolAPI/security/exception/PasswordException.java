package caffeToolAPI.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by pc-mg on 3/16/2018.
 */
public class PasswordException extends AuthenticationException{

    public PasswordException(String msg) {
        super(msg);
    }
}
