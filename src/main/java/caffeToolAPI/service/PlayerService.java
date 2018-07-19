package caffeToolAPI.service;

import caffeToolAPI.dto.PlayerDto;
import caffeToolAPI.model.Player;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by pc-mg on 2/5/2018.
 */
public interface PlayerService {

    List<Player> findall();

    Player findById(int id);

    Player save(Player player);

    Player update(int id, Player player, MultipartFile file, HttpServletRequest request);

    void delete(Player player);

    void enable(Player player);
}
