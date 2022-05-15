package spring.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.Models.Product;
import spring.Models.Role;
import spring.Models.User;
import spring.Repos.ProductRepos;
import spring.Repos.UserRepos;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepos userRepos;

    private final ProductRepos productRepos;

    private final PasswordEncoder passwordEncoder;

    public UserService(ProductRepos productRepos, UserRepos userRepos, PasswordEncoder passwordEncoder) {
        this.productRepos = productRepos;
        this.userRepos = userRepos;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean addNewUser(User user){
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(Role.USER));
        userRepos.save(user);
        return true;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepos.findByUsername(username);
    }

    public void edit(User user, Map<String, String> form) {
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        for (String key : form.keySet()) {
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
            switch (key){
                case "username":
                    user.setUsername(form.get(key));
                    break;
                case "name":
                    user.setName(form.get(key));
                    break;
                case "lastname":
                    user.setLastname(form.get(key));
                    break;
                case "fathername":
                    user.setFathername(form.get(key));
                    break;
            }
        }
        userRepos.save(user);
        log.info("IN edit USER: {} SUCCESSFUL EDIT", user.getUsername());
    }

    public List<Product> search(String searchContent){
        List<Product> products = new ArrayList<Product>();
        System.out.println(searchContent);
        for (Product product : productRepos.findAll()) {
            if(product.getName().contains(searchContent)){
                products.add(product);
            }
        }
        log.info("IN serach SERACH {}", searchContent);
        return  products;
    }

}
