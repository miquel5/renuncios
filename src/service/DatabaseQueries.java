package service;

import model.ServiceModel;
import model.UserModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static app.Main.con;

public class DatabaseQueries
{
    // Login
    public UserModel validateLogin(String username, String password)
    {
        UserModel user = null;

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            // Afegir informació de usuari
            if (rs.next())
            {
                user = new UserModel();
                user.setUsername(rs.getString("username"));
                user.setCompany("");
                user.setSede("");
                user.setRole(rs.getString("role"));
                user.setSector("");
            }
        } catch (SQLException e)
        {
            System.out.println("Error al validar el login: " + e.getMessage());
        }

        return user;
    }

    // Register
    public UserModel validateRegister(String username, String company, String sector, String password, String repeatPassword, String role) {
        UserModel user = null;

        if (!password.equals(repeatPassword))
        {
            System.out.println("Las contraseñas no coinciden.");
            return null;
        }

        try
        {
            con.setAutoCommit(false); // Iniciar transacció

            // Taula users
            String insertUserSQL = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertUserSQL))
            {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, role);
                pstmt.executeUpdate();
            }

            // Taula clients
            String cif = generateCif(company);

            String insertClientSQL = "INSERT INTO clients (cif, company_name, sector, username) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertClientSQL))
            {
                pstmt.setString(1, cif);
                pstmt.setString(2, company);
                pstmt.setString(3, sector);
                pstmt.setString(4, username);
                pstmt.executeUpdate();
            }

            // Taula offices
            // TODO: Afegir insert a la taula (Se suposa que és per ip)

            con.commit(); // Tancar transacció

            user = new UserModel();
            user.setUsername(username);
            user.setCompany(company);
            user.setSector(sector);
            user.setRole(role);

        } catch (SQLException e)
        {
            try
            {
                con.rollback();
            } catch (SQLException ex)
            {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.out.println("Error al registrar: " + e.getMessage());
        } finally
        {
            try
            {
                con.setAutoCommit(true);
            } catch (SQLException e)
            {
                System.out.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }

        return user;
    }

    public String generateCif(String company_name)
    {
        String initials = company_name.substring(0, Math.min(3, company_name.length())).toUpperCase();
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        String formattedNumber = String.format("%04d", randomNumber);
        return initials + formattedNumber;
    }

    // Mostrar productes
    public static List<ServiceModel> products()
    {
        List<ServiceModel> productList = new ArrayList<>();
        String sql = "SELECT * FROM service";

        /*if (!type.isEmpty())
        {
            sql = "SELECT * FROM service WHERE typee = ?";
        }*/

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            /*if (!type.isEmpty())
            {
                pstmt.setString(1, type);
            }*/

            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                int numC = rs.getInt("numC");
                int numS = rs.getInt("numS");
                String typee = rs.getString("typee");
                String txt = rs.getString("txt");
                Date dataI = rs.getDate("dataI");
                Date dataF = rs.getDate("dataF");
                String sizee = rs.getString("sizee");
                boolean color = rs.getBoolean("color");
                double price = rs.getDouble("price");

                ServiceModel service = new ServiceModel(numC, numS, typee, txt, dataI, dataF, sizee, color, price);
                productList.add(service);
            }
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return productList;
    }

    public static void generateTiked()
    {
        // TODO: Afegir els inserts (depen si la persona si vol pagar la facura en mesos)
    }

}
