package caffeToolAPI.validator;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.model.Player;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc-mg on 5/3/2018.
 */
@Component
public class PlayerValidator {

    List<MessageDto> errors = new ArrayList();

    public List<MessageDto> validate(Player player) {

        if(!checkFirstname(player.getFirstname())) {
            errors.add(new MessageDto("Firstname must contain at least 3 characters"));
        }
        if(!checkLastname(player.getLastname())){
            errors.add(new MessageDto("Lastname must contain at least 3 characters"));
        }

        return errors;
    }

    private boolean checkFirstname(String firstname) {
        if(firstname.isEmpty() || firstname.length() < 3) {
            return false;
        }
        else {
            return true;
        }
    }

    private boolean checkLastname(String lastname) {
        if(lastname.isEmpty() || lastname.length() < 3) {
            return false;
        }
        else {
            return true;
        }
    }
}
