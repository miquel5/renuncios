package app;

import controller.CartController;
import controller.SearchController;
import model.TypeModel;
import utils.DatabaseConnection;
import view.Frame;

import java.lang.reflect.Type;
import java.sql.*;

public class Main
{
    public static Connection con;

    public static void main(String[] args)
    {
        con = DatabaseConnection.connectionOracle();
        new Frame();

        // Afegir productes per defecte
        SearchController newProduct = new SearchController();
        newProduct.addType(1, "Web", "", "2024-01-01", "2024-12-31", "Mediano", true, 190.99);
        newProduct.addType(2, "Web", "", "2024-02-01", "2024-12-31", "Pequeño", true, 169.99);
        newProduct.addType(3, "Flayer", "", "2024-03-01", "", "", false, 39.99);
        newProduct.addType(4, "Pancarta", "", "2024-04-01", "2024-12-31", "", true, 149.99);
    }
}
