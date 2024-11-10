package app;

import service.DatabaseQueries;
import utils.DatabaseConnection;
import view.Frame;

import java.sql.*;

public class Main
{
    public static Connection con;

    public static void main(String[] args)
    {
        con = DatabaseConnection.connectionOracle();
        new Frame();

        // Iniciar
        DatabaseQueries.products();
    }
}
