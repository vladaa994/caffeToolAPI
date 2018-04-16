package caffeToolAPI.service.impl;

import caffeToolAPI.model.Role;
import caffeToolAPI.repository.RoleRepository;
import caffeToolAPI.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by pc-mg on 4/13/2018.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role findOne(int id) {
        return roleRepository.findOne(id);
    }
}
