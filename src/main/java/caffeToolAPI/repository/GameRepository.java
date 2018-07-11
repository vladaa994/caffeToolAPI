package caffeToolAPI.repository;

import caffeToolAPI.model.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pc-mg on 2/3/2018.
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Integer>{

    @Query("select g from Game g where g.endTime is not null")
    Page<Game> findAllWithPagin(Pageable pageable);
}
