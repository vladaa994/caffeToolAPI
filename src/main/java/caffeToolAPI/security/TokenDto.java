package caffeToolAPI.security;

/**
 * Created by pc-mg on 3/14/2018.
 */
public class TokenDto {
    private String token;

    public TokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
