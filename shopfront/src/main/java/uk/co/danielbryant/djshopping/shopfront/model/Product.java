package uk.co.danielbryant.djshopping.shopfront.model;

import org.springframework.beans.factory.annotation.Value;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")

public class Product {
    @Id
    private String id;
    @Value("NULL")
    private String sku;
    @Value("NULL")
    private String name;
    @Value("NULL")
    private String description;
    @Value("100")
    private BigDecimal price;
    @Value("100")
    private int amountAvailable;

    public Product(String id, String name, String description, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(String id, String sku, String name, String description, BigDecimal price, int amountAvailable) {
        this.id = id;
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.price = price;
        this.amountAvailable = amountAvailable;
    }

    public String getId() {
        return id;
    }

    public String getSku() {
        return sku;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }
}
