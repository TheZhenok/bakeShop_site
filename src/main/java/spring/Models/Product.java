package spring.Models;


import org.aspectj.weaver.ast.Or;
import org.javamoney.moneta.Money;

import javax.money.Monetary;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Money price;
    private BigDecimal priceValue;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcoPath() {
        return icoPath;
    }

    public void setIcoPath(String icoPath) {
        this.icoPath = icoPath;
    }

    @CollectionTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"))
    private String icoPath;


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

    public Product() {
    }

    public Product(Long id, String name, Money price, BigDecimal priceValue) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.priceValue = priceValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
