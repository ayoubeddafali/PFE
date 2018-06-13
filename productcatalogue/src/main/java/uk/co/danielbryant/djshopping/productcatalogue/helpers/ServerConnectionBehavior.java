package uk.co.danielbryant.djshopping.productcatalogue.helpers;


import java.sql.Connection;

public interface ServerConnectionBehavior {
    Connection getConnection();
    String getConnectionURL();
    String getConnectionDetails();
    String getTablesSchemaQuery();

}
