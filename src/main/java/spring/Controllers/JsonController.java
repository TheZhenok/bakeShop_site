package spring.Controllers;

import com.stripe.model.Coupon;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import spring.Models.Buyer;
import spring.Models.Product;
import spring.Repos.ProductRepos;
import spring.Repos.UserRepos;
import spring.Service.BuyerService;
import spring.Service.StripeService;
import spring.Untils.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
public class JsonController {
    @Value("${stripe.key.secret}")
    private String stripeApi;

    @Value("${stripe.key.public}")
    private String API_PUBLIC_KEY;

    private final StripeService stripeService;

    private final ProductRepos productRepos;

    private final UserRepos userRepos;

    private final BuyerService buyerService;

    public JsonController(StripeService stripeService, ProductRepos productRepos, UserRepos userRepos, BuyerService buyerService) {
        this.stripeService = stripeService;
        this.productRepos = productRepos;
        this.userRepos = userRepos;
        this.buyerService = buyerService;
    }

    @GetMapping("/json")
    public ResponseEntity getJson(){
        List<Product> allProducts = productRepos.findAll();
        JSONObject productsJson = new JSONObject(allProducts);
        return ResponseEntity.ok(productsJson);
    }

    @PostMapping("/create-subscription")
    public @ResponseBody
    Response createSubscription(String email, String token, String plan, String coupon) {

        if (token == null || plan.isEmpty()) {
            return new Response(false, "Stripe payment token is missing. Please try again later.");
        }

        String customerId = stripeService.createCustomer(email, token);

        if (customerId == null) {
            return new Response(false, "An error accurred while trying to create customer");
        }

        String subscriptionId = stripeService.createSubscription(customerId, plan, coupon);

        if (subscriptionId == null) {
            return new Response(false, "An error accurred while trying to create subscription");
        }

        return new Response(true, "Success! your subscription id is " + subscriptionId);
    }

    @PostMapping("/cancel-subscription")
    public @ResponseBody Response cancelSubscription(String subscriptionId) {

        boolean subscriptionStatus = stripeService.cancelSubscription(subscriptionId);

        if (!subscriptionStatus) {
            return new Response(false, "Faild to cancel subscription. Please try again later");
        }

        return new Response(true, "Subscription cancelled successfully");
    }

    @PostMapping("/coupon-validator")
    public @ResponseBody Response couponValidator(String code) {

        Coupon coupon = stripeService.retriveCoupon(code);

        if (coupon != null && coupon.getValid()) {
            String details = (coupon.getPercentOff() == null ? "$" + (coupon.getAmountOff() / 100)
                    : coupon.getPercentOff() + "%") + "OFF" + coupon.getDuration();
            return new Response(true, details);
        }
        return new Response(false, "This coupon code is not available. This may be because it has expired or has "
                + "already been applied to your account.");
    }

    @PostMapping("/create-charge")
    public @ResponseBody Response createCharge(String email, String token, String money, String idProduct, Principal principal) throws IOException {
        BigDecimal tengeToDollar = stripeService.convertMoney(money);

        if (token == null) {
            return new Response(false, "Stripe payment token is missing. please try again later.");
        }

        String chargeId = stripeService.createCharge(email, token, tengeToDollar.intValue() * 100);// money

        if (chargeId == null) {
            return new Response(false, "An error accurred while trying to charge.");
        }

        // You may want to store charge id along with order information
        Product product = productRepos.getById(Long.getLong(idProduct));
        Buyer buyer = buyerService.setBuyerFromPrincipalAndProduct(principal, product);
        return new Response(true, "Success your charge id is " + chargeId);
    }
}
