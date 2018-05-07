package caffeToolAPI.controller;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.model.Player;
import caffeToolAPI.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pc-mg on 2/5/2018.
 */
@RestController
@RequestMapping(value = "/player")
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public ResponseEntity<List<Player>> findActive() {
        List<Player> active = playerService.findall().stream()
                .filter(p -> p.getActive())
                .collect(Collectors.toList());
        return new ResponseEntity<>(active, HttpStatus.OK);
    }

    @RequestMapping(value = "/removed", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public ResponseEntity<List<Player>> findRemoved() {
        List<Player> removed = playerService.findall().stream()
                .filter(p -> !p.getActive())
                .collect(Collectors.toList());
        return new ResponseEntity<>(removed, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Player player = playerService.findById(id);
        if(player != null){
            return new ResponseEntity<>(player, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("Player with id: " + id + " doesn't exist."), HttpStatus.NOT_FOUND);
        }
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public ResponseEntity<?> save(@RequestBody Player player) {
        if (player != null) {
            Player result = playerService.save(player);
            if (result != null) {
                return new ResponseEntity<>(result, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new MessageDto("Couldn't create new player"), HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(new MessageDto("You must fill out all fields!"), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Player player, @PathVariable int id) {
        Player pl = playerService.update(player, id);
        if(pl == null) {
            return new ResponseEntity<>(new MessageDto("Player with id: " + id + " doesn't exist."), HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(pl, HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public ResponseEntity<?> delete(@PathVariable int id) {
        Player player = playerService.findById(id);
        if(player != null) {
            playerService.delete(player);
            return new ResponseEntity<>(new MessageDto("Player has been succesfully deleted."), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("Player with id: " + id + " doesn't exist."), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/enable/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
    public ResponseEntity<?> enable(@PathVariable int id) {
        Player player = playerService.findById(id);
        if(player != null) {
            playerService.enable(player);
            return new ResponseEntity<>(new MessageDto("Player has been successfully enabled."), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("Player with id: " + id + " doesn't exist."), HttpStatus.BAD_REQUEST);
        }
    }
}
