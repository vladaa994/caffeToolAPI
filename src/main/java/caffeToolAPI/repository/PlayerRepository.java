package caffeToolAPI.repository;

import caffeToolAPI.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pc-mg on 2/5/2018.
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer>{

}
