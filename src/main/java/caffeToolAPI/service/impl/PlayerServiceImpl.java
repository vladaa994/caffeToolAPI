package caffeToolAPI.service.impl;

import caffeToolAPI.dto.MessageDto;
import caffeToolAPI.dto.PlayerDto;
import caffeToolAPI.model.Player;
import caffeToolAPI.repository.PlayerRepository;
import caffeToolAPI.service.PlayerService;
import caffeToolAPI.validator.PlayerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by pc-mg on 2/5/2018.
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    PlayerValidator playerValidator;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> findall() {
        return playerRepository.findAll();
    }

    @Override
    public Player findById(int id) {
        return playerRepository.findOne(id);
    }

    @Override
    public Player save(Player player) {
        List<MessageDto> errors = playerValidator.validate(player);
        if(errors != null) {
            player.setActive(true);
            player.setWin(player.getWin());
            player.setLost(player.getLost());
            return playerRepository.save(player);
        }
        else {
            return null;
        }
    }

    @Override
    public Player update(int id, Player player, MultipartFile file, HttpServletRequest request) {
        Player pl = playerRepository.findOne(id);
        Path path = Paths.get("");
        if(request.getServerName().equals("localhost")){
            path = Paths.get("C:\\Users\\pc-mg\\Desktop\\caffeToolAPI\\src\\main\\resources\\images\\" + file.getOriginalFilename());
        }

        try {
            file.transferTo(new File(path.toAbsolutePath().toString()));
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        if(pl != null) {
            pl.setFirstname(player.getFirstname());
            pl.setLastname(player.getLastname());
            pl.setImage(file.getOriginalFilename());
            return playerRepository.save(pl);
        } else {
            return null;
        }
    }

    @Override
    public void delete(Player player) {
        player.setActive(false);
        playerRepository.save(player);
    }

    @Override
    public void enable(Player player) {
        player.setActive(true);
        playerRepository.save(player);
    }
}
