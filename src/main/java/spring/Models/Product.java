package spring.Models;


import org.javamoney.moneta.Money;

import javax.money.Monetary;
import java.math.BigDecimal;

public class Product {
    private String name;
    private Money price;
    private BigDecimal priceValue;


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Money getPrice() {
        return Money.of(priceValue, Monetary.getCurrency("USD"));
    }
    public BigDecimal getPriceValue() {
        return priceValue;
    }
    public void setPriceValue(BigDecimal priceValue) {
        this.priceValue = priceValue;
    }
}
