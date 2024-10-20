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
                // Afegir informació a l'usuari que s'ha registrat
                user = new UserModel();
                user.setUsername(rs.getString("username"));
                user.setCompany(rs.getString("company"));
                user.setHeadquarters(rs.getString("headquarters"));
                user.setRole(rs.getString("role"));
                user.setSector(rs.getString("sector"));
            }
        } catch (SQLException e)
        {
            System.out.println("Error al validar el login: " + e.getMessage());
        }

        return user;
    }

    public UserModel validateRegister(Connection con, String username, String company, String sector, String password, String repeatPassword) {
        UserModel user = null;

        // Verificar que la contrasenya sigui la mateixa
        if (!password.equals(repeatPassword))
        {
            System.out.println("Las contraseñas no coinciden.");
            return null;
        }

        String sql1 = "SELECT * FROM users WHERE username = ?";

        try
        {
            // Verificar si l'usuari existeix
            PreparedStatement pstmt1 = con.prepareStatement(sql1);
            pstmt1.setString(1, username);
            ResultSet rs1 = pstmt1.executeQuery();

            if (rs1.next()) {
                System.out.println("El nombre de usuario ya existe.");
                return null;
            }

            // Insertar en la tabla users
            String sql2 = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt2 = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            pstmt2.setString(1, username);
            pstmt2.setString(2, password);

            int affectedRows = pstmt2.executeUpdate();
            if (affectedRows == 0)
            {
                System.out.println("Error al insertar el usuario.");
                return null;
            }

            // Obtener la clave generada (user_id)
            ResultSet generatedKeys = pstmt2.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next())
            {
                userId = generatedKeys.getInt(1);  // Suponemos que el id es de tipo int
            } else
            {
                System.out.println("Error al obtener el ID del usuario.");
                return null;
            }

            // Insertar en la tabla clients
            String sql3 = "INSERT INTO clients (user_id, company, sector) VALUES (?, ?, ?)";
            PreparedStatement pstmt3 = con.prepareStatement(sql3);
            pstmt3.setInt(1, userId);
            pstmt3.setString(2, company);
            pstmt3.setString(3, sector);
            pstmt3.executeUpdate();

            // Afegir informació a l'usuari que s'ha registrat
            user = new UserModel();
            user.setUsername(username);
            user.setCompany(company);
            user.setSector(sector);
            user.setRole("");

        } catch (SQLException e) {
            System.out.println("Error al registrar el usuario: " + e.getMessage());
        }

        return user;
    }
}
