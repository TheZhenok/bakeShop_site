package spring.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.Models.Product;
import spring.Models.Role;
import spring.Models.User;
import spring.Repos.UserRepos;
import spring.Service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Value("${upload.path.product}")
    private String productIconPath;

    private final UserRepos userRepos;

    private final UserService userService;

    public AdminController(UserRepos userRepos, UserService userService) {
        this.userRepos = userRepos;
        this.userService = userService;
    }


    @GetMapping()
    public String adminMain(Model model, Principal principal){
        List<User> users = userRepos.findAll();
        model.addAttribute("isAdmin", Role.ADMIN);
        model.addAttribute("users", users);
        return "user_list";
    }


    @GetMapping("/{id}")
    public String currentUser(@PathVariable("id") Long id,
                              Model model){
        Optional<User> user = userRepos.findById(id);

        System.out.println(user.get().getUsername());
        return "/current_user";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("Id") Long id){
        Optional<User> user = userRepos.findById(id);
        if(user.get() != null){
            userRepos.delete(user.get());
        }
        return "redirect: admin";
    }

    @GetMapping("/product/add")
    public String addProductPage(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("product", new Product());
        return "product-add";
    }

    @PostMapping("addProduct")
    public String addProduct(){
        return "product-add";
    }
}
