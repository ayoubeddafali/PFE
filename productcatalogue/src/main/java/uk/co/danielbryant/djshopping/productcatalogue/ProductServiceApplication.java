package uk.co.danielbryant.djshopping.productcatalogue;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import uk.co.danielbryant.djshopping.productcatalogue.healthchecks.BasicHealthCheck;
import uk.co.danielbryant.djshopping.productcatalogue.configuration.ProductServiceConfiguration;
import uk.co.danielbryant.djshopping.productcatalogue.resources.ProductResource;
import uk.co.danielbryant.djshopping.productcatalogue.helpers.DBManager;


public class ProductServiceApplication extends Application<ProductServiceConfiguration> {
    public static void main(String[] args) throws Exception {
        new ProductServiceApplication().run(args);
    }

    @Override
    public String getName() {
        return "product-list-service";
    }

    @Override
    public void initialize(Bootstrap<ProductServiceConfiguration> bootstrap) {
        // nothing to do yet
        System.out.println("INITIALIZE PRODUCT CATALOG");
        DBManager dbm = new DBManager();
        String query = "CREATE TABLE IF NOT EXISTS `products` (\n" +
                "  `id` varchar(255) NOT NULL,\n" +
                "  `amount_available` int(11) DEFAULT 0,\n" +
                "  `description` varchar(255) DEFAULT NULL,\n" +
                "  `name` varchar(255) DEFAULT NULL,\n" +
                "  `price` decimal(19,2) DEFAULT NULL,\n" +
                "  `sku` varchar(255) DEFAULT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
        String queries[] = {
                "INSERT INTO products(id, name, amount_available, description, price ) VALUES ('1', 'Widget', 0, 'Premium ACME Widgets' , " +  1.20 + ")",
                "INSERT INTO products(id, name, amount_available, description, price ) VALUES ('2', 'Sprocket', 0, 'Grade B sprockets', " + 4.10 + ")",
                "INSERT INTO products(id, name, amount_available, description, price ) VALUES ('3', 'Anvil', 0, 'Large Anvils' , " +  45.50 + ")",
                "INSERT INTO products(id, name, amount_available, description, price ) VALUES ('4', 'Cogs', 0, 'Grade Y cogs' , " + 1.80 + ")",
                "INSERT INTO products(id, name, amount_available, description, price ) VALUES ('5', 'Multitool', 0, 'Multitools', " + 154.10 + ")"
        };
        if (dbm.ExecuteNonQuery(query)) {
            System.out.println("Table Created IF NOT EXISTS");
            for (int i = 0 ; i < queries.length ; i ++){
                if (dbm.ExecuteNonQuery(queries[i])){
                    System.out.println("Entry Added to products table");
                }
                else {
                    System.out.println("Error why adding product " + i);
                }
            }
        }

    }

    @Override
    public void run(ProductServiceConfiguration config,
                    Environment environment) {
        final BasicHealthCheck healthCheck = new BasicHealthCheck(config.getVersion());
        environment.healthChecks().register("template", healthCheck);

        Injector injector = Guice.createInjector();
        environment.jersey().register(injector.getInstance(ProductResource.class));
    }
}