package controller;

import service.DatabaseQueries;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.text.DecimalFormat;

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
            return "Pancarta";
        } else if (service == 3)
        {
            return "Flayer";
        } else
        {
            return "";
        }
    }

    // Traduir quina mida
    public static String whatSize(int size)
    {
        if (size == 1)
        {
            return "Pequeño";
        } else if (size == 2)
        {
            return "Mediano";
        } else if(size == 3)
        {
            return "Grande";
        } else
        {
            return "";
        }
    }

    // Saber opció color
    public static String withColor(int color)
    {
        if (color == 1)
        {
            return "Sí";
        } else if (color == 2)
        {
            return "No";
        } else
        {
            return "";
        }
    }

    // Saber opció de pago
    public static String whatPayment(int mes)
    {
        if (mes == 1)
        {
            return "Único";
        } else if (mes == 2)
        {
            return "Mensual";
        } else
        {
            return "";
        }
    }

    // Passar de sede a int
    public static String whatSedeInt(String sedeString)
    {
        if (sedeString.equals("Madrid"))
        {
            return "1";
        } else if (sedeString.equals("Barcelona"))
        {
            return "2";
        } else if (sedeString.equals("Sevilla"))
        {
            return "3";
        } else
        {
            return "";
        }
    }

    // Passar de int a sede
    public static String whatSedeString(String sedeInt)
    {
        if (sedeInt.equals("1"))
        {
            return "Madrid";
        } else if (sedeInt.equals("2"))
        {
            return "Barcelona";
        } else if (sedeInt.equals("3"))
        {
            return "Sevilla";
        } else
        {
            return "";
        }
    }

    // Limitar els números double a 2 digits
    public static String formatPrice(double price)
    {
        DecimalFormat df = new DecimalFormat("#0.00");
        return df.format(price);
    }

}
