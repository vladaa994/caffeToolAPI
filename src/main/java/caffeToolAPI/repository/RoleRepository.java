package caffeToolAPI.repository;

import caffeToolAPI.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by pc-mg on 4/13/2018.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
