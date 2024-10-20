package service;

import model.UserModel;

import java.sql.*;

public class DatabaseQueries
{
    public UserModel validateLogin(Connection con, String username, String password)
    {
        UserModel user = null;

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try
        {
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                // Afegir informació a l'usuari que s'ha loguejat
                user = new UserModel();
                user.setUsername(rs.getString("username"));
                user.setCompany("");
                user.setHeadquarters("");
                user.setRole("Admin"); //TODO: Afegir un rol
                user.setSector("");
            }
        } catch (SQLException e)
        {
            System.out.println("Error al validar el login: " + e.getMessage());
        }

        return user;
    }

    public UserModel validateRegister(Connection con, String username, String company, String sector, String password, String repeatPassword) {
        UserModel user = null;

        // Verificar que les contrasenyes siguin les mateixes
        if (!password.equals(repeatPassword))
        {
            System.out.println("Las contraseñas no coinciden.");
            return null;
        }

        String sql1 = "SELECT * FROM users WHERE username = ?";

        /*try
        {
            // Verificar que el usuario no exista
            PreparedStatement pstmt1 = con.prepareStatement(sql1);
            pstmt1.setString(1, username);
            ResultSet rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                System.out.println("El nombre de usuario ya existe.");
                return null;
            }

            // Insertar usuario
            String sql2 = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt2 = con.prepareStatement(sql2);
            pstmt2.setString(1, username);
            pstmt2.setString(2, password);
            pstmt2.executeUpdate();

            // Crear UserModel
            user = new UserModel();
            user.setUsername(username);
            user.setCompany(company);
            user.setSector(sector);

            // Tancar recursos
            rs1.close();
            pstmt1.close();
            pstmt2.close();

        } catch (SQLException e)
        {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }*/

        return user;
    }
}
