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

    // Traduir quin servei és de la DB
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

    // Traduir quina mida
    public static String whatSize(int size)
    {
        // TODO: Afegir lógica per cada tipus (1 petit, 2 mitjá i 3 gran)
        return "";
    }

    // Saber opció color
    public static String withColor(int color)
    {
        // TODO: Saber si vol color o no (1 és si)
        return "";
    }
}
