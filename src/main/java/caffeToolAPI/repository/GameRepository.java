package caffeToolAPI.repository;

import caffeToolAPI.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pc-mg on 2/3/2018.
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Integer>{

}
