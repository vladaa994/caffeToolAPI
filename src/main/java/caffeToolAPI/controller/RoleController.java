package caffeToolAPI.controller;

import caffeToolAPI.model.Role;
import caffeToolAPI.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by pc-mg on 4/13/2018.
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Role>> getAll() {
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }
}
