package caffeToolAPI.service.impl;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.dto.UserDto;
import caffeToolAPI.model.Role;
import caffeToolAPI.model.User;
import caffeToolAPI.repository.UserRepository;
import caffeToolAPI.service.RoleService;
import caffeToolAPI.service.UserService;
import caffeToolAPI.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.security.util.Password;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc-mg on 1/28/2018.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllActive() {
        return userRepository.findAllActive();
    }

    @Override
    public List<User> findAllRemoved() {
        return userRepository.findAllRemoved();
    }

    @Override
    public User findById(int id) {
        User user = userRepository.findOne(id);
        if(user != null){
            return user;
        }
        else{
            return null;
        }
    }

    @Override
    public User save(UserDto userDto) {
        List<Role> roles = new ArrayList<>();

        Role role = roleService.findOne(userDto.getRoleId());
        if(role != null) {
            roles.add(role);
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setIsActive(true);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User update(UserDto userDto, int id) {
        User usr = findById(id);
        List<MessageDto> errors;
        if(usr != null){
            if(userDto.getPassword() == null){
                errors = userValidator.checkEditUsername(userDto.getUsername());
                if(errors == null || errors.size() == 0){
                    usr.setUsername(userDto.getUsername());
                    return userRepository.save(usr);
                }
                else {
                    return null;
                }
            }
            else {
                errors = userValidator.validate(userDto);
                if(errors == null || errors.size() == 0) {
                    usr.setUsername(userDto.getUsername());
                    usr.setPassword(userDto.getPassword());
                    return userRepository.save(usr);
                }
                else {
                    return null;
                }
                }
            }
        else {
            return null;
        }
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void delete(User user) {
        user.setIsActive(false);
        userRepository.save(user);
    }


    @Override
    public boolean checkUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public User enableUser(int id) {
        User user = userRepository.findOne(id);
        if(user != null) {
            user.setIsActive(true);
            return userRepository.save(user);
        }
        else {
            return null;
        }
    }
}
