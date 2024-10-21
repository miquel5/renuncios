package service;

import model.UserModel;
import java.sql.*;
import java.util.Random;

public class DatabaseQueries
{
    // Login
    public UserModel validateLogin(Connection con, String username, String password)
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
                user.setHeadquarters("");
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
    public UserModel validateRegister(Connection con, String username, String company, String sector, String password, String repeatPassword, String role) {
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

            String insertClientSQL = "INSERT INTO clients (cif, company_name, sector, user_id) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertClientSQL))
            {
                pstmt.setString(1, cif);
                pstmt.setString(2, company);
                pstmt.setString(3, sector);
                pstmt.setString(4, username);
                pstmt.executeUpdate();
            }

            // Taula offices
            // TODO: Afegir insert a la taula

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

    private String generateCif(String company_name)
    {
        String initials = company_name.substring(0, Math.min(3, company_name.length())).toUpperCase();
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        String formattedNumber = String.format("%04d", randomNumber);
        return initials + formattedNumber;
    }

}
