package spring.Controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.Models.Product;
import spring.Models.Role;
import spring.Models.User;
import spring.Repos.ProductRepos;
import spring.Repos.UserRepos;
import spring.Service.ProductService;
import spring.Service.UserService;

import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    @Value("${upload.path.product}")
    private String productIconPath;

    private final UserRepos userRepos;

    private final UserService userService;

    private final ProductService productService;

    private final ProductRepos productRepos;

    public AdminController(UserRepos userRepos, UserService userService, ProductService productService, ProductRepos productRepos) {
        this.userRepos = userRepos;
        this.userService = userService;
        this.productService = productService;
        this.productRepos = productRepos;
    }


    @GetMapping()
    public String adminMain(Model model, Principal principal){
        List<Product> allProducts = productRepos.findAll();
        List<User> users = userRepos.findAllActiveUsers();
        model.addAttribute("user", new User());
        model.addAttribute("product", new Product());
        model.addAttribute("allProducts", allProducts);
        model.addAttribute("isAdmin", Role.ADMIN);
        model.addAttribute("users", users);
        return "user_list";
    }

    @GetMapping("/{id}")
    public String currentUser(@PathVariable("id") Long id,
                              Model model){
        Optional<User> user = userRepos.findById(id);

        model.addAttribute("user", user.get());
        model.addAttribute("roles", Role.values());
        return "admin_current_user";
    }

    @GetMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        Optional<Product> product = productRepos.findById(id);
        productRepos.delete(product.get());
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        User user = userRepos.findById(id).orElseThrow();
        user.setActive(false);
        return "redirect:/admin";
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
        productRepos.save(product);
        System.out.println(product.getPriceValue());
        System.out.println(product.getIcoPath());
        System.out.println("PRODUCT ADD");
        return "redirect:/admin";
    }

    @PostMapping("/{id}/edit")
    public String adminEditUser(@PathVariable("id") Long id,
                                @RequestParam Map<String, String> form){
        User user = userRepos.findById(id).orElseThrow();
        userService.edit(user, form);
        return "redirect:/admin/{id}";
    }

    @GetMapping("/product/{id}")
    public String currentProduct(@PathVariable Long id,
            Model model){
        Product product = productRepos.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "current_product";
    }

    @PostMapping("/product/{id}/edit")
    public String editProduct(@PathVariable Long id,
                              @RequestParam Map<String, String> form,
                              @RequestParam("icoPath") MultipartFile file) throws IOException {
        productService.edit(productRepos.findById(id).orElseThrow(), form, file);
        return "redirect:/admin/product/{id}";
    }
}
