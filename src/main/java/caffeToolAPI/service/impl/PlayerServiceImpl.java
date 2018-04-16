package caffeToolAPI.service.impl;

import caffeToolAPI.model.Player;
import caffeToolAPI.repository.PlayerRepository;
import caffeToolAPI.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pc-mg on 2/5/2018.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

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
        return playerRepository.save(player);
    }

    @Override
    public Player update(Player player) {
        return null;
    }

    @Override
    public void delete(Player player) {

    }
}
