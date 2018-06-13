package uk.co.danielbryant.djshopping.productcatalogue.services;

import uk.co.danielbryant.djshopping.productcatalogue.helpers.DBManager;
import uk.co.danielbryant.djshopping.productcatalogue.model.Product;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductService {

    //{productId, Product}
    private Map<String, Product> fakeProductDAO = new HashMap<>();

    public ProductService() {
    }

    public void init(){

    }

    public List<Product> getProducts() {
        DBManager dbm = new DBManager();
        String query = "Select * from products";
        try {
            ResultSet res = dbm.ExecuteResultSet(query);
            while (res.next()){
                fakeProductDAO.put(res.getString("id"), new Product(
                        res.getString("id"),
                        res.getString("name"),
                        res.getString("description"),
                        res.getBigDecimal("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(fakeProductDAO.values());
    }

    public Optional<Product> getProduct(String id) {
        return Optional.ofNullable(fakeProductDAO.get(id));
    }
}
