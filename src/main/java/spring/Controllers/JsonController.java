package spring.Controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.Models.Product;
import spring.Repos.ProductRepos;

import java.util.List;

@RestController
public class JsonController {
    @Autowired
    private ProductRepos productRepos;

    @GetMapping("/json")
    public List getJson(){
        List<Product> allProducts = productRepos.findAll();
        JSONObject productsJson = new JSONObject(allProducts);
        return allProducts;
    }
}
