package spring.Controllers;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.Models.Product;
import spring.Models.Role;
import spring.Models.User;
import spring.Repos.ProductRepos;
import spring.Repos.UserRepos;
import spring.Service.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Controller
public class MainController {

    private final UserService userService;

    private final UserRepos userRepos;

    private final ProductRepos productRepos;

    public MainController(UserService userService, UserRepos userRepos, ProductRepos productRepos) {
        this.userService = userService;
        this.userRepos = userRepos;
        this.productRepos = productRepos;
    }

    @GetMapping()
    public String mainPage(Model model){

        System.out.println("Start page!");

        model.addAttribute("user", new User());
        return "index";
    }


    @GetMapping("/register")
    public String acceptRegister(){

        return "accept_register";
    }


    @PostMapping("register")
    public String registerUser(Model model,
                               @Valid @ModelAttribute("user")User user,
                               BindingResult bindingResult){
        User userThe = userRepos.findByUsername("TheZhenok");
        if(userThe != null) {
            System.out.println(userThe.getName());
        }
        if (!user.equalsUser()){
            model.addAttribute("isFalsePassword", true);
            model.addAttribute("errorRepeatPassword", "Пароли не совпадают");
            return "index";
        }
        if(bindingResult.hasErrors()){
            return "index";
        }
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepos.save(user);
        model.addAttribute("userName", user.getName());
        return "accept_register";
    }


    @GetMapping("/genertion_users")
    public String generationUsers(){
        final int MAX_GENERATION_VALUE = 100;
        Faker faker = new Faker();
        User user = new User();
        List<User> users = userRepos.findAll();
        if(users.size() > MAX_GENERATION_VALUE){
            return "Users is full";
        }

        for (int i = 0; i < MAX_GENERATION_VALUE; i++) {
            faker = new Faker(new Random(i));
            user = new User();
            user.setName(faker.name().firstName());
            user.setLastname(faker.name().lastName());
            user.setFathername(faker.name().prefix());
            user.setUsername(faker.name().username());
            user.setPassword(faker.pokemon().name());
            user.setRepeatPassword(user.getPassword());
            user.setPay(100);
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepos.save(user);
        }
        return "Generation is successful";
    }

    @PostMapping("search")
    public String searchProduct(
            @RequestParam("searchContent") String searchContent,
            Model model){
        List<Product> products = new ArrayList<Product>();
        System.out.println(searchContent);
        model.addAttribute("user", new User());
        for (Product product : productRepos.findAll()) {
            if(product.getName().contains(searchContent)){
                products.add(product);
            }
        }
        if(products.isEmpty()){
            model.addAttribute("isEmpty", true);
        }else {
            model.addAttribute("isEmpty", false);
        }
        model.addAttribute("productsSearch", products);
        model.addAttribute("searchContent", searchContent);
        return "search";
    }


}
