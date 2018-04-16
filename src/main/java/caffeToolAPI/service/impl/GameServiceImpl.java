package caffeToolAPI.service.impl;

import caffeToolAPI.model.Game;
import caffeToolAPI.model.Player;
import caffeToolAPI.model.User;
import caffeToolAPI.repository.GameRepository;
import caffeToolAPI.service.GameService;
import caffeToolAPI.service.PlayerService;
import caffeToolAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by pc-mg on 2/3/2018.
 */
@Service
public class GameServiceImpl implements GameService{

    private GameRepository gameRepository;
    private UserService userService;
    private PlayerService playerService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserService userService, PlayerService playerService){
        this.gameRepository = gameRepository;
        this.userService = userService;
        this.playerService = playerService;
    }

    @Override
    public List<Game> findall() {
        return gameRepository.findAll();
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
        game.setPlayers(players);
        game.setStartTime(new Date());
        game.setDeleted(false);
        return gameRepository.save(game);
    }

    @Override
    public Game update(Game game, int id) {
        Game gameFind = findById(id);
        if(gameFind != null) {
            gameFind.setStartTime(game.getStartTime());
            gameFind.setEndTime(game.getEndTime());
            gameFind.setTableNumber(game.getTableNumber());
            gameFind.setPaid(game.getPaid());
            gameFind.setUser(game.getUser());
            gameFind.setLeague(game.getLeague());
            gameFind.setPlayers(game.getPlayers());
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
}
