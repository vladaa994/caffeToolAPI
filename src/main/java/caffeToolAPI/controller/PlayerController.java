package caffeToolAPI.controller;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.dto.PlayerDto;
import caffeToolAPI.model.Player;
import caffeToolAPI.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public ResponseEntity<?> getById(@PathVariable int id, HttpServletRequest request) {
        Player player = playerService.findById(id);
        if(player != null){
            if(player.getImage() != null) {
                String pathToImage = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/images/";
                player.setImage(pathToImage + player.getImage());
            }
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
    public ResponseEntity<?> update(@PathVariable int id,
                                    @RequestPart(value = "player") Player player,
                                    @RequestPart(value = "file", required = false) MultipartFile file,
                                    HttpServletRequest request) {
        Player p = playerService.update(id, player, file, request);
        if(p == null) {
            return new ResponseEntity<>(new MessageDto("Player with id: " + id + " doesn't exist."), HttpStatus.BAD_REQUEST);
        }
        else {
            String pathToImage = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/images/";
            p.setImage(pathToImage + p.getImage());
            return new ResponseEntity<>(p, HttpStatus.OK);
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
