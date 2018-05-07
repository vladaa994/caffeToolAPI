package caffeToolAPI.service.impl;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.model.Player;
import caffeToolAPI.repository.PlayerRepository;
import caffeToolAPI.service.PlayerService;
import caffeToolAPI.validator.PlayerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pc-mg on 2/5/2018.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    PlayerValidator playerValidator;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> findall() {
        return playerRepository.findAll();
    }

    @Override
    public Player findById(int id) {
        return playerRepository.findOne(id);
    }

    @Override
    public Player save(Player player) {
        List<MessageDto> errors = playerValidator.validate(player);
        if(errors != null) {
            player.setActive(true);
            player.setWin(0);
            player.setLost(0);
            return playerRepository.save(player);
        }
        else {
            return null;
        }
    }

    @Override
    public Player update(Player player, int id) {
        Player pl = playerRepository.findOne(id);
        if(pl != null) {
            pl.setFirstname(player.getFirstname());
            pl.setLastname(player.getLastname());
            return playerRepository.save(pl);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Player player) {
        player.setActive(false);
        playerRepository.save(player);
    }

    @Override
    public void enable(Player player) {
        player.setActive(true);
        playerRepository.save(player);
    }
}
