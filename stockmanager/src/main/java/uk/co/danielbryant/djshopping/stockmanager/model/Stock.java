package uk.co.danielbryant.djshopping.stockmanager.model;

import javax.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock {

    @Id
    private String productId;
    private String sku;
    private int amountAvailable;

    protected Stock() {
    }

    public Stock(String productId, String sku, int amountAvailable) {
        this.productId = productId;
        this.sku = sku;
        this.amountAvailable = amountAvailable;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }
}
