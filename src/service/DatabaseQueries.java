package service;

import model.ServiceModel;
import model.UserModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static app.Main.con;

public class DatabaseQueries {

    // Login
    public UserModel validateLogin(String username, String password) {
        UserModel user = null;

        String sql = "SELECT * FROM usuario WHERE usuario = ? AND contrasenya = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserModel();
                user.setUsername(rs.getString("usuario"));
                user.setCompany("");  // No hay campo directo de empresa en usuario
                user.setSede("");  // Campo no especificado
                user.setRole(rs.getString("rol"));
                user.setSector("");  // No hay campo de sector en usuario
            }
        } catch (SQLException e) {
            System.out.println("Error al validar el login: " + e.getMessage());
        }

        return user;
    }

    // Register
    public UserModel validateRegister(String username, String company, String sector, String password, String repeatPassword, String role) {
        UserModel user = null;

        if (!password.equals(repeatPassword)) {
            System.out.println("Las contraseñas no coinciden.");
            return null;
        }

        try {
            con.setAutoCommit(false);

            // Tabla usuario
            String insertUserSQL = "INSERT INTO usuario (usuario, contrasenya, rol) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertUserSQL)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, role);
                pstmt.executeUpdate();
            }

            // Tabla cliente
            String cif = generateCif(company);

            String insertClientSQL = "INSERT INTO cliente (cif, empresa, sector, usuario) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertClientSQL)) {
                pstmt.setString(1, cif);
                pstmt.setString(2, company);
                pstmt.setString(3, sector);
                pstmt.setString(4, username);
                pstmt.executeUpdate();
            }

            con.commit();

            user = new UserModel();
            user.setUsername(username);
            user.setCompany(company);
            user.setSector(sector);
            user.setRole(role);

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.out.println("Error al registrar: " + e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }

        return user;
    }

    public String generateCif(String company_name) {
        String initials = company_name.substring(0, Math.min(3, company_name.length())).toUpperCase();
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        String formattedNumber = String.format("%04d", randomNumber);
        return initials + formattedNumber;
    }

    // Mostrar servicios
    public static List<ServiceModel> products() {
        List<ServiceModel> productList = new ArrayList<>();
        String sql = "SELECT * FROM servicio";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int numC = rs.getInt("numc");
                int numS = rs.getInt("nums");
                int tipo = rs.getInt("tipo");
                String txt = rs.getString("txt");
                Date dataI = rs.getDate("datai");
                Date dataF = rs.getDate("dataf");
                int mida = rs.getInt("mida");
                int color = rs.getInt("color");
                double precio = rs.getDouble("precio");

                ServiceModel service = new ServiceModel(numC, numS, tipo, txt, dataI, dataF, mida, color, precio);
                productList.add(service);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return productList;
    }

    public static void generateTiked() {
        // TODO: Implementación pendiente, insertar lógica de recibo en función de modalidad de pago
    }
}
