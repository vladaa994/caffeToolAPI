package caffeToolAPI.controller;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.model.Game;
import caffeToolAPI.model.User;
import caffeToolAPI.repository.GameRepository;
import caffeToolAPI.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by pc-mg on 2/3/2018.
 */
@RestController
@RequestMapping(value = "/game")
public class GameController {

    private GameService gameService;
    private GameRepository gameRepository;

    @Autowired
    public GameController(GameService gameService, GameRepository gameRepository) {
        this.gameService = gameService;
        this.gameRepository = gameRepository;
    }


    @RequestMapping(value = "/all/active", method = RequestMethod.GET)
    public ResponseEntity<List<Game>> getAllActive() {
        List<Game> activeGames = gameService.findAllActive();
        return new ResponseEntity<>(activeGames, HttpStatus.OK);
    }

    @RequestMapping(value = "/all/finished", method = RequestMethod.GET)
    public ResponseEntity<List<Game>> getAllFinished() {
        List<Game> finishedGames = gameService.findAllFinished();
        return new ResponseEntity<>(finishedGames, HttpStatus.OK);
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
        if(game != null) {
            Game save = gameService.save(game);
            return new ResponseEntity<>(save, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("You must fill out all fields."), HttpStatus.BAD_REQUEST);
        }
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

    @RequestMapping(value = "/finish/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> finishGame(@PathVariable int id) {
        Game game = gameService.finish(id);
        if(game != null) {
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("Game with id: " + id + " doesn't exist."), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/pay/{id}/winner/{winnerId}/lost/{lostId}", method = RequestMethod.GET)
    public ResponseEntity<?> payGame(@PathVariable int id, @PathVariable int winnerId, @PathVariable int lostId) {
        Game game = gameService.pay(id, winnerId, lostId);
        if(game != null) {
            return new ResponseEntity<>(game, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(new MessageDto("Game with id: " + id + " doesn't exist."), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/findall/{page}-{size}", method = RequestMethod.GET)
    public ResponseEntity<Page<Game>> findAllPaginated(@PathVariable int page, @PathVariable int size) {
        Page<Game> games = gameService.findAllWithPagin(page, size);
        return new ResponseEntity<>(games, HttpStatus.OK);
    }

}
