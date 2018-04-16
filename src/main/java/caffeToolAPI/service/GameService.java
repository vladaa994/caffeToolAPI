package caffeToolAPI.service;

import caffeToolAPI.model.Game;

import java.util.List;

/**
 * Created by pc-mg on 2/3/2018.
 */
public interface GameService {

    List<Game> findall();

    Game findById(int id);

    Game save(Game game);

    Game update(Game game, int id);

    boolean delete(int id);
}
