package controller;

import service.DatabaseQueries;
import utils.DatabaseConnection;

import java.sql.Connection;

public class GeneralController
{
    private DatabaseQueries queries;
    private Connection con;

    public GeneralController()
    {
        this.con = DatabaseConnection.connectionOracle();
        this.queries = new DatabaseQueries();
    }

    public static String whatService(int service)
    {
        if (service == 1)
        {
            return "Web";
        } else if(service == 2)
        {
            return "Flayer";
        } else if (service == 3)
        {
            return "Pancarta";
        } else
        {
            return "";
        }
    }
}
