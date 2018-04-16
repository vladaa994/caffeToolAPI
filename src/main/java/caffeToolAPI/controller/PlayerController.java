package caffeToolAPI.controller;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.model.Player;
import caffeToolAPI.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by pc-mg on 2/5/2018.
 */
@RestController
@RequestMapping(value = "/player")
@Secured({"ROLE_USER", "ROLE_ADMIN"})
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Player>> findall() {
        return new ResponseEntity<>(playerService.findall(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
    public ResponseEntity<?> save(@RequestBody Player player) {
        if(player != null) {
            return new ResponseEntity<Object>(playerService.save(player), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("You must fill out all fields!"), HttpStatus.BAD_REQUEST);
        }
    }
}
