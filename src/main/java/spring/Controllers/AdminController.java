package spring.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.Models.Product;
import spring.Models.Role;
import spring.Models.User;
import spring.Repos.UserRepos;
import spring.Service.UserService;

import javax.swing.plaf.basic.BasicBorders;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        model.addAttribute("user", new User());
        model.addAttribute("product", new Product());
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
        return "redirect: /admin";
    }

    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("product") Product product,
                             @RequestParam("myfile") MultipartFile file) throws IOException {

        System.out.println("ADDING");
        if(file != null && !file.getOriginalFilename().isEmpty()){
            File uploadFile = new File(productIconPath);
            if(!uploadFile.exists()){
                uploadFile.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + '.' + file.getOriginalFilename();
            file.transferTo(new File(productIconPath + "/" + resultFileName));
            product.setIcoPath(resultFileName);
        }

        System.out.println(product.getPriceValue());
        System.out.println(product.getIcoPath());
        System.out.println("PRODUCT ADD");
        return "redirect:/admin";
    }
}
