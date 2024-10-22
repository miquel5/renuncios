package utils;

import java.sql.*;

public class DatabaseConnection
{
    private static final String USER = "DW2425_G2_JOS_MIQ_RID";
    private static final String PWD = "A12345678";
    private static final String URL = "jdbc:oracle:thin:@//oracle.ilerna.com:1521/XEPDB1"; // @192.168.3.26:1521 - @oracle.ilerna.com:1521

    public static Connection connectionOracle()
    {
        Connection con = null;

        System.out.println("Intentando conectarse a la base de datos");

        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(URL, USER, PWD);
        } catch (ClassNotFoundException e)
        {
            System.out.println("No se ha encontrado el driver " + e);
        } catch (SQLException e)
        {
            System.out.println("Error en las credenciales o en la URL " + e);
        }

        System.out.println("Conectado a la base de datos");

        return con;
    }

    public static void insert(Connection con, String sql)
    {
        try
        {
            Statement st = con.createStatement();
            st.execute(sql);

            System.out.println("Insert hecho correctamente");
        } catch (SQLException e)
        {
            System.out.println("Ha habido un error en el Insert " + e);
        }
    }

    public static void update(Connection con, String sql)
    {
        try
        {
            Statement st = con.createStatement();
            st.execute(sql);

            System.out.println("Update hecho correctamente");
        } catch (SQLException e)
        {
            System.out.println("Ha habido un error en el Update " + e);
        }
    }

    public static void delete(Connection con, String sql)
    {
        try
        {
            Statement st = con.createStatement();
            st.execute(sql);

            System.out.println("Delete hecho correctamente");
        } catch (SQLException e)
        {
            System.out.println("Ha habido un error en el Delete " + e);
        }
    }

    public static String[] select(Connection con, String sql, String[] listaElementosSeleccionados)
    {
        try
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.isBeforeFirst())
            {
                String[] arrayS = new String[listaElementosSeleccionados.length];
                while (rs.next())
                {
                    for (int i = 0; i < listaElementosSeleccionados.length; i++)
                    {
                        arrayS[i] = rs.getString(listaElementosSeleccionados[i]);
                    }
                }
                return arrayS;
            } else
            {
                System.out.println("No se ha encontrado nada");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
            return new String[0];
        }

        System.out.println("Unexpected error");
        return new String[0];
    }

    public static void print(Connection con, String sql, String[] listaElementosSeleccionados)
    {
        try
        {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.isBeforeFirst())
            {
                while (rs.next())
                {
                    for (int i = 0; i < listaElementosSeleccionados.length; i++)
                    {
                        System.out.println(listaElementosSeleccionados[i] + ": " + rs.getString(listaElementosSeleccionados[i]));
                    }
                }
            } else
            {
                System.out.println("No se ha encontrado nada");
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}