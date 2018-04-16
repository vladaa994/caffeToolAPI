package caffeToolAPI.dto;

import caffeToolAPI.model.Role;

import java.util.List;

/**
 * Created by pc-mg on 3/22/2018.
 */
public class UserDto {

    private String username;
    private String password;
    private int roleId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}