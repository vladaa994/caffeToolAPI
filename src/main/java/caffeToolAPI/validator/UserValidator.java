package caffeToolAPI.validator;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.dto.UserDto;
import caffeToolAPI.model.User;
import caffeToolAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc-mg on 1/31/2018.
 */
@Component
public class UserValidator{

    private List<MessageDto> errors = new ArrayList<MessageDto>();

    public List<MessageDto> validate(UserDto user) {
        if(!(checkUsername(user.getUsername()))){
            MessageDto messageDto = new MessageDto("Username must contain at least 3 characters");
            errors.add(messageDto);

        }
        if(!(checkPassword(user.getPassword()))){
            MessageDto messageDto = new MessageDto("Password must contain at least 4 characters");
            errors.add(messageDto);
        }
        return errors;
    }

    private boolean checkUsername(String username){
        if(username == null || username.isEmpty() || username.length() < 3){
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkPassword(String password){
        if(password == null || password.isEmpty() || password.length() < 4){
            return false;
        }
        else {
            return true;
        }
    }
}

