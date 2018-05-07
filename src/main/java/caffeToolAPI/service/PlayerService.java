package caffeToolAPI.service;

import caffeToolAPI.model.Player;

import java.util.List;

/**
 * Created by pc-mg on 2/5/2018.
 */
public interface PlayerService {

    List<Player> findall();

    Player findById(int id);

    Player save(Player player);

    Player update(Player player, int id);

    void delete(Player player);

    void enable(Player player);
}
