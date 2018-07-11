package caffeToolAPI.security;

/**
 * Created by pc-mg on 3/14/2018.
 */
public class TokenDto {
    private String token;
    private String username;

    public TokenDto(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
