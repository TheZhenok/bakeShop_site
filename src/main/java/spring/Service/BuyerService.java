package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.Models.Buyer;
import spring.Models.Order;
import spring.Models.PaymentMethod;
import spring.Models.Product;
import spring.Repos.BuyerRepos;
import spring.Repos.OrderRepos;
import spring.Repos.ProductRepos;
import spring.Repos.UserRepos;

import java.security.Principal;
import java.util.Collections;

@Service
public class BuyerService {
    private final ProductRepos productRepos;
    private final BuyerRepos buyerRepos;
    private final OrderRepos orderRepos;
    private final UserRepos userRepos;

    public BuyerService(ProductRepos productRepos, BuyerRepos buyerRepos, OrderRepos orderRepos, UserRepos userRepos) {
        this.productRepos = productRepos;
        this.buyerRepos = buyerRepos;
        this.orderRepos = orderRepos;
        this.userRepos = userRepos;
    }

    public Buyer setBuyerFromPrincipalAndProduct(Principal principal, Product product){
        Buyer buyer = new Buyer();
        buyer.setUser(userRepos.findByUsername(principal.getName()));
        Order order = new Order();
        order.setProduct(product);
        orderRepos.save(order);
        buyer.setOrder(order);
        buyer.setMethod(Collections.singleton(PaymentMethod.CARD));
        buyerRepos.save(buyer);
        return buyer;
    }
}
