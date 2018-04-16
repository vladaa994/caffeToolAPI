package caffeToolAPI.repository;

import caffeToolAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pc-mg on 1/28/2018.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    @Query("select u from User u where u.active=true")
    List<User> findAllActive();

    @Query("select u from User u where u.active=false")
    List<User> findAllRemoved();
}
