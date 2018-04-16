package caffeToolAPI.controller;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.model.Game;
import caffeToolAPI.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by pc-mg on 2/3/2018.
 */
@RestController
@RequestMapping(value = "/game")
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Game>> findall() {
        List<Game> games = gameService.findall();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id) {
        Game game = gameService.findById(id);
        if(game != null){
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("Game with id: " + id + " doesn't exist."), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody Game game) {
        return new ResponseEntity<>(gameService.save(game), HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody Game game, @PathVariable int id) {
        Game gameUpdate = gameService.update(game, id);
        if (gameUpdate != null) {
            return new ResponseEntity<>(gameUpdate, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("Game with id: " + id + " doesn't exists."), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<MessageDto> delete(@PathVariable int id) {
        if(gameService.delete(id)) {
            return new ResponseEntity<>(new MessageDto("Game successfully deleted"), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("Game with id: " + id + " doesn't exist."), HttpStatus.BAD_REQUEST);
        }
    }

}
