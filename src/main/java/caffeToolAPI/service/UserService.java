package caffeToolAPI.service;

import caffeToolAPI.dto.UserDto;
import caffeToolAPI.model.User;

import java.util.List;

/**
 * Created by pc-mg on 1/28/2018.
 */
public interface UserService {

    List<User> findAllActive();

    List<User> findAllRemoved();

    User findById(int id);

    User save(UserDto user);

    User update(UserDto user, int id);

    User findByUsername(String username);

    void delete(User user);

    boolean checkUsername(String username);

    User enableUser(int id);
}
