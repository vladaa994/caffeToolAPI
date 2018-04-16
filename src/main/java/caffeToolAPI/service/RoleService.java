package caffeToolAPI.service;

import caffeToolAPI.model.Role;

import java.util.List;

/**
 * Created by pc-mg on 4/13/2018.
 */
public interface RoleService {

    List<Role> getAll();

    Role findOne(int id);
}
