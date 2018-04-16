package caffeToolAPI.security.service;

import caffeToolAPI.model.User;
import caffeToolAPI.repository.UserRepository;
import caffeToolAPI.security.model.UserDtl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc-mg on 3/13/2018.
 */
@Service
public class AccountService implements UserDetailsService {

    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    private UserRepository userRepository;
    private boolean isActive;

    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User: " + username + " doesn't exist.");
        }
        this.isActive = user.getisActive();
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getName())));

//        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        UserDtl userDetails = new UserDtl(user.getUsername(), user.getPassword(), authorities, user.getisActive());
        return userDetails;
    }

}
