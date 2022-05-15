package spring.Controllers;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import spring.Models.Product;
import spring.Models.Role;
import spring.Models.User;
import spring.Repos.ProductRepos;
import spring.Repos.UserRepos;
import spring.Service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class MainController {

    @Value("${stripe.key.public}")
    private String API_PUBLIC_KEY;

    private final PasswordEncoder passwordEncoder;

    @Value("${spring.datasource.password}")
    private String passwordSuperUser;

    private final UserService userService;

    private final UserRepos userRepos;

    private final ProductRepos productRepos;

    public MainController(UserService userService,
                          UserRepos userRepos,
                          ProductRepos productRepos,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepos = userRepos;
        this.productRepos = productRepos;
        this.passwordEncoder = passwordEncoder;
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
        if (!user.equalsUser()){
            model.addAttribute("isFalsePassword", true);
            model.addAttribute("errorRepeatPassword", "Пароли не совпадают");
            return "index";
        }
        if(bindingResult.hasErrors()){
            return "index";
        }
        userService.addNewUser(user);
        model.addAttribute("userName", user.getName());
        return "accept_register";
    }


    @PostMapping("/genertion_users")
    public RedirectView generationUsers(Model model, RedirectAttributes redir){
        final int MAX_GENERATION_VALUE = 100;
        Faker faker;
        User user;
        List<User> users = userRepos.findAll();
        RedirectView redirectView = new RedirectView("/admin", true);
        redir.addFlashAttribute("generationInfo","Полное количество пользователей");
        if(users.size() > MAX_GENERATION_VALUE){
            redir.addFlashAttribute("generationInfo","Полное количество пользователей");
            return redirectView;
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
        redir.addFlashAttribute("generationInfo","Генерация прошла успено!");
        return redirectView;
    }
    @GetMapping("/search")
    public String searchGetProduct(Model model){
        Iterable<Product> products = productRepos.findAll();
        model.addAttribute("productsSearch", products);
        return "search";
    }

    @PostMapping("search")
    public String searchProduct(
            @RequestParam("searchContent") String searchContent,
            Map<String, Object> model){
        List<Product> products = userService.search(searchContent);
        model.put("productsSearch", products);
        model.put("user", new User());
        model.put("searchContent", searchContent);
        return "search";
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model){
        User user = userRepos.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userEditor", new User());
        return "current_user";
    }

    @PostMapping("/profile/{id}/edit")
    public String editUser(@PathVariable("id") Long id,
                           @RequestParam Map<String, String> form){
        User user = userRepos.findById(id).orElseThrow();
        userService.edit(user, form);
        return "redirect:/profile";
    }

    @GetMapping("/catalog")
    public String catalog(Model model){
        model.addAttribute("products", productRepos.findAll());
        return "catalog";
    }


    @GetMapping("/create_super_user")
    public String createSuperUser(){
        User user = new User();
        if(userRepos.findByUsername("TheZhenok") != null){
            return "error";
        }
        user.setFathername("Витальевич");
        user.setUsername("TheZhenok");
        user.setName("Евгений");
        user.setLastname("Табашнюк");
        user.setPassword(passwordEncoder.encode(passwordSuperUser));
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.ADMIN));
        userRepos.save(user);

        return "redirect:/";
    }

    @GetMapping("/subscription")
    public String subscriptionPage(Model model) {
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "pay/subscription";
    }

}
