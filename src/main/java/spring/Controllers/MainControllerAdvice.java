package spring.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import spring.Models.User;
import spring.Repos.UserRepos;

import java.security.Principal;

@ControllerAdvice
public class MainControllerAdvice {
    @Autowired
    private UserRepos userRepos;

    @ModelAttribute
    public void userAdvice(Model model, Principal principal){
        if(principal != null){
            model.addAttribute("activeUser", principal.getName());
            User user = userRepos.findByUsername(principal.getName());
            model.addAttribute("isAdmin", user.isAdmin());
            model.addAttribute("signOut", true);
        }else {
            model.addAttribute("activeUser", "<none>");
            model.addAttribute("signOut", false);
        }
    }
}
