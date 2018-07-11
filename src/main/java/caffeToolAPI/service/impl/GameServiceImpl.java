package caffeToolAPI.service.impl;

import caffeToolAPI.helper.Helper;
import caffeToolAPI.model.Game;
import caffeToolAPI.model.Player;
import caffeToolAPI.model.User;
import caffeToolAPI.repository.GameRepository;
import caffeToolAPI.service.GameService;
import caffeToolAPI.service.PlayerService;
import caffeToolAPI.service.UserService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by pc-mg on 2/3/2018.
 */
@Service
public class GameServiceImpl implements GameService{

    private GameRepository gameRepository;
    private UserService userService;
    private PlayerService playerService;
    private Helper helper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService, PlayerService playerService, Helper helper){
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.playerService = playerService;
        this.helper = helper;
    }

    @Override
    public List<Game> findall() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> findAllActive() {
        return findall().stream()
                .filter(g -> !g.getIsPaid())
                .collect(Collectors.toList());
    }

    @Override
    public List<Game> findAllFinished() {
        return findall().stream()
                .filter(g -> g.getIsPaid())
                .collect(Collectors.toList());
    }

    @Override
    public Game findById(int id) {
        return gameRepository.findOne(id);
    }

    @Override
    public Game save(Game game) {
        User user = userService.findById(game.getUser().getId());
        List<Player> players = new ArrayList<>();
        if (user != null) {
            game.setUser(game.getUser());
        }
        if(game.getPlayers() != null) {
            game.getPlayers().forEach(item -> {
                Player player = playerService.findById(item.getId());
                if(player != null){
                    players.add(player);
                }
            });
        }

        game.setType(game.getType());
        game.setPlayers(players);
        game.setDeleted(false);
        return gameRepository.save(game);
    }

    @Override
    public Game update(Game game, int id) {
        Game gameFind = findById(id);
        List<Player> players = new ArrayList<>();
        if(gameFind != null) {
            game.getPlayers().forEach(p -> {
                        Player player = playerService.findById(p.getId());
                        if(player != null) {
                            players.add(player);
                        }
                    });
            gameFind.setStartTime(game.getStartTime());
            gameFind.setEndTime(game.getEndTime());
            gameFind.setTableNumber(game.getTableNumber());
            gameFind.setPaid(game.getPaid());
            gameFind.setUser(game.getUser());
            gameFind.setType(game.getType());
            gameFind.setPlayers(players);
            gameFind.setDeleted(game.getIsDeleted());
            return gameRepository.save(gameFind);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean delete(int id) {
        Game game = findById(id);
        if(game != null) {
            game.setDeleted(true);
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public Game finish(int id) {
        Game game = gameRepository.findOne(id);
        float bill = 0;

        if(game != null) {
            game.setEndTime(new Date());
            float timePlayed = helper.getMinutesFromMilis(game.getEndTime().getTime() - game.getStartTime().getTime());
            bill = timePlayed * 5;
            game.setBill(bill);
            gameRepository.save(game);
            return game;
        }
        else {
            return null;
        }
    }

    @Override
    public Game pay(int id) {
        Game game = gameRepository.findOne(id);
        if(game != null) {
            game.setPaid(true);
            gameRepository.save(game);
            return game;
        }
        else {
            return null;
        }

    }

    @Override
    public Page<Game> findAllWithPagin(int page, int size) {
        return gameRepository.findAllWithPagin(new PageRequest(page, size));
    }
}
