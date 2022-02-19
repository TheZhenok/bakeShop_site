package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import spring.Models.User;
import spring.Repos.UserRepos;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepos userRepos;

    public String addNewUser(User user){
        return "";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepos.findByUsername(username);
    }
}
