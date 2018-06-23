package caffeToolAPI.service;

import caffeToolAPI.model.Game;

import java.util.List;

/**
 * Created by pc-mg on 2/3/2018.
 */
public interface GameService {

    List<Game> findall();

    List<Game> findAllActive();

    List<Game> findAllFinished();

    Game findById(int id);

    Game save(Game game);

    Game update(Game game, int id);

    boolean delete(int id);

    Game finish(int id);

    Game pay(int id);
}
