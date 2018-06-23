package caffeToolAPI.controller;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.dto.UserDto;
import caffeToolAPI.model.User;
import caffeToolAPI.service.UserService;
import caffeToolAPI.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc-mg on 1/28/2018.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Autowired
    private UserValidator userValidator;


    @RequestMapping(value = "/all/active", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    public ResponseEntity<List<User>> getAllActive() {
        List<User> users = userService.findAllActive();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/all/removed", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN')")
    public ResponseEntity<List<User>> getAllRemoved() {
        List<User> users = userService.findAllRemoved();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        User user = userService.findById(id);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("User with id: " + id + " doesn't exist."), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> save(@RequestBody UserDto user) {

        boolean isAllowed = false;

        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equalsIgnoreCase("role_superadmin"))){
            isAllowed = true;
        }
        if(!isAllowed) {
            return new ResponseEntity<>(new MessageDto("You are not allowed to perform this action."), HttpStatus.BAD_REQUEST);
        }
        if(userService.findByUsername(user.getUsername()) != null) {
            return new ResponseEntity<>(new MessageDto("Username already exists."), HttpStatus.BAD_REQUEST);
        }
        List<MessageDto> errors = userValidator.validate(user);
        if(errors == null || errors.size() == 0){
             User usr = userService.save(user);
             return new ResponseEntity<>(usr, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> update(@RequestBody UserDto userDto, @PathVariable("id") int id) {

        if(userService.findById(id) == null) {
            return new ResponseEntity<>(new MessageDto("Username doesn't exists."), HttpStatus.BAD_REQUEST);
        }
        User usr = userService.update(userDto, id);
        if(usr != null){
            return new ResponseEntity<>(usr, HttpStatus.OK);
        }
        else {
            MessageDto messageDto = new MessageDto("User doesn't exists.");
            return new ResponseEntity<>(messageDto, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> delete(@PathVariable int id) {
        User user = userService.findById(id);
        if(user != null){
            userService.delete(user);
            return new ResponseEntity<>(new MessageDto("User successfully deleted."), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("User doesn't exists."), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/check-username", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public ResponseEntity<?> checkUsername(@RequestParam String username) {
        if(!userService.checkUsername(username)) {
            return new ResponseEntity<MessageDto>(new MessageDto("Username already exists!"), HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    public ResponseEntity<MessageDto> enableUser(@RequestParam int id) {
        User user = userService.enableUser(id);
        if(user != null) {
            return new ResponseEntity<>(new MessageDto("User successfully enabled!"), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("User with id: " + id + "doesn't exist!"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/current-user", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<User> getCurrentUser() {
        User user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
