package utils;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.*;

public class DatabaseConnection
{
    private static final String USER = "DW2425_G2_JOS_MIQ_RID";
    private static final String PWD = "A12345678";
    private static final String IP_LOCAL = "192.168.3.26";
    private static final String HOSTNAME_REMOTO = "oracle.ilerna.com";
    private static final String PUERTO = "1521";
    private static final String SID = "XEPDB1";

    public static Connection connectionOracle()
    {
        Connection con = null;
        String url;

        // Verificar si la IP local es accesible
        if (isConnect(IP_LOCAL))
        {
            url = "jdbc:oracle:thin:@//" + IP_LOCAL + ":" + PUERTO + "/" + SID;
        } else
        {
            url = "jdbc:oracle:thin:@//" + HOSTNAME_REMOTO + ":" + PUERTO + "/" + SID;
        }

        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, USER, PWD);
            System.out.println("Conectado a la base de datos..");
        } catch (ClassNotFoundException e)
        {
            System.out.println("No se ha encontrado el driver " + e);
        } catch (SQLException e)
        {
            System.out.println("Error en las credenciales o en la URL " + e);
        }

        return con;
    }

    public static boolean isConnect(String ip)
    {
        try
        {
            InetAddress address = InetAddress.getByName(ip);
            return address.isReachable(1000); // 1s de ping
        } catch (IOException e)
        {
            System.err.println("Error al verificar la IP: " + e.getMessage());
            return false;
        }
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