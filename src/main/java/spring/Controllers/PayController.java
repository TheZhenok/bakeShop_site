package spring.Controllers;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.Models.Product;
import spring.Repos.ProductRepos;

import java.io.IOException;

@Controller
public class PayController {

    @Value("${stripe.key.public}")
    private String API_PUBLIC_KEY;

    private final ProductRepos productRepos;

    public PayController(ProductRepos productRepos) {
        this.productRepos = productRepos;
    }

    @GetMapping("/buy/{id}")
    public String buyCard(@PathVariable Long id, Model model){
        Product product = productRepos.findById(id).orElseThrow();
        model.addAttribute("product", product);
        return "pay/card_pay";
    }

    @GetMapping("/buy/{id}/charge")
    public String chargePage(@PathVariable Long id, Model model) throws IOException {
        Product product = productRepos.getById(id);
        model.addAttribute("product", product);
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);
        return "pay/charge";
    }

}