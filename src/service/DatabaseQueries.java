package service;

import controller.CartController;
import controller.GeneralController;
import model.CartModel;
import model.ServiceModel;
import model.UserModel;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static app.Main.con;

public class DatabaseQueries
{
    // Login
    public UserModel validateLogin(String username, String password)
    {
        String sql = "SELECT usuario.usuario, usuario.rol, cliente.cif, cliente.empresa, cliente.sector " + "FROM usuario " + "LEFT JOIN cliente ON usuario.usuario = cliente.usuario " + "WHERE usuario.usuario = ? AND usuario.contrasenya = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next())
            {
                UserModel user = UserModel.getInstance();
                user.setUsername(rs.getString("usuario"));
                user.setRole(rs.getString("rol"));
                user.setCif(rs.getString("cif") != null ? rs.getString("cif") : "");
                user.setCompany(rs.getString("empresa") != null ? rs.getString("empresa") : "");
                user.setSector(rs.getString("sector") != null ? rs.getString("sector") : "");
                user.setSede(""); // TODO: gestionar sede correctament

                return user;
            } else
            {
                System.out.println("Usuario no encontrado o contraseña incorrecta.");
                return null;
            }
        } catch (SQLException e)
        {
            System.out.println("Error al validar el login: " + e.getMessage());
            return null;
        }
    }

    // Register
    public UserModel validateRegister(String username, String company, String sector, String cif, String password, String repeatPassword, String role) {
        UserModel user = null;

        if (!password.equals(repeatPassword))
        {
            System.out.println("Las contraseñas no coinciden.");
            return null;
        }

        try
        {
            con.setAutoCommit(false);

            // Tabla usuario
            String insertUserSQL = "INSERT INTO usuario (usuario, contrasenya, rol) VALUES (?, ?, ?)";

            try (PreparedStatement pstmt = con.prepareStatement(insertUserSQL))
            {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, role);

                pstmt.executeUpdate();
            }

            String insertClientSQL = "INSERT INTO cliente (cif, empresa, sector, usuario) VALUES (?, ?, ?, ?)";

            try (PreparedStatement pstmt = con.prepareStatement(insertClientSQL))
            {
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
            user.setCif(cif);
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

    // Generar els serveis
    public static List<ServiceModel> products() {
        List<ServiceModel> productList = new ArrayList<>();

        // Generar dates per defecte
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaFin = fechaActual.plusMonths(1);

        // Webs disponibles
        productList.addAll(filterWeb(Date.valueOf(fechaActual), Date.valueOf(fechaFin)));

        // Pancartes disponibles
        productList.addAll(filterBanner(Date.valueOf(fechaActual), Date.valueOf(fechaFin)));

        // Flayers disponibles
        productList.addAll(filterFlayer(Date.valueOf(fechaActual), Date.valueOf(fechaFin)));

        // Barrejar les cartes
        //Collections.shuffle(productList);

        return productList;
    }

    public static List<ServiceModel> filterWeb(Date datai, Date dataf)
    {
        String sql = "SELECT * FROM web";
        List<ServiceModel> webServices = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                int uniqueId = rs.getInt("uniqueId");
                int idw = rs.getInt("idw");
                String nombre = rs.getString("nombre");
                String enlace = rs.getString("enlace");
                double preup = rs.getDouble("preup");
                double preum = rs.getDouble("preum");
                double preug = rs.getDouble("preug");

                ServiceModel service = new ServiceModel(uniqueId, 2,0, 0, 1, "", null, datai, dataf, 2, 1, preum * 30, "", idw, nombre, enlace, preup, preum, preug, 0, "", "", 0.0, 0, "", "", 0.0);

                webServices.add(service);
            }
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return webServices;
    }

    public static List<ServiceModel> filterBanner(Date datai, Date dataf)
    {
        String sql = "SELECT * FROM localizacion";
        List<ServiceModel> bannerServices = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                int uniqueId = rs.getInt("uniqueId");
                int idl = rs.getInt("idl");
                String descrip = rs.getString("descrip");
                String coordenadas = rs.getString("coordenadas");
                double precio = rs.getDouble("precio");

                ServiceModel service = new ServiceModel(uniqueId, 2,0, 0, 2, "", null, datai, dataf, 0, 1, precio * 30, "", 0, "", "", 0.0, 0.0, 0.0, idl, descrip, coordenadas, precio, 0, "", "", 0.0);

                bannerServices.add(service);
            }
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return bannerServices;
    }

    public static List<ServiceModel> filterFlayer(Date datai, Date dataf)
    {
        String sql = "SELECT * FROM barrio";
        List<ServiceModel> flayerServices = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                int uniqueId = rs.getInt("uniqueId");
                int cp = rs.getInt("cp");
                String poblacion = rs.getString("poblacion");
                String provincia = rs.getString("provincia");
                double preu = rs.getDouble("preu");

                ServiceModel service = new ServiceModel(uniqueId, 1,0,0, 3, "", null, datai, dataf, 0, 1, preu * 30, "", 0, "", "", 0.0, 0.0, 0.0, 0, "", "", 0.0, cp, poblacion, provincia, preu);

                flayerServices.add(service);
            }
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        return flayerServices;
    }

    // Generar un tíquet
    public static void generateTicket()
    {
        CartModel cartModel = CartModel.getInstance();
        ArrayList<Integer> list = cartModel.getList();
        CartController cartController = new CartController();
        UserModel user = UserModel.getInstance(); // Usuari actual

        // Consultes SQL
        String sqlContractacio = "INSERT INTO contractacion(numc, datac, estado, cif) VALUES(?, sysdate, ?, ?)";
        String sqlServicio = "INSERT INTO servicio(nums, numc, tipo, txt, imatge, datai, dataf, mida, color, precio, pagamento, cp, idw, idl) " + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlRecibo = "INSERT INTO recibo(numr, pagado, numc, nums) VALUES (?, ?, ?, ?)";

        int numC = findNewContract();
        int numS = findNewService();
        int numR = findNewRecibo();

        try
        {
            // Iniciar transacció
            con.setAutoCommit(false);

            // Insertar contractació
            try (PreparedStatement pstmtContractacio = con.prepareStatement(sqlContractacio))
            {
                pstmtContractacio.setInt(1, numC);
                pstmtContractacio.setString(2, "Activo"); // todo
                pstmtContractacio.setString(3, user.getCif());

                pstmtContractacio.executeUpdate();
            }

            // Insertar servicios
            for (Integer serviceId : list)
            {
                // Buscar el mateix servei de la llista
                ServiceModel serviceModel = cartController.findService(serviceId);

                // Transformar dates
                java.sql.Date sqlDataI = new java.sql.Date(serviceModel.getDataI().getTime());
                java.sql.Date sqlDataF = new java.sql.Date(serviceModel.getDataF().getTime());

                try (PreparedStatement pstmtServicio = con.prepareStatement(sqlServicio))
                {
                    pstmtServicio.setInt(1, numS);
                    pstmtServicio.setInt(2, numC);
                    pstmtServicio.setInt(3, serviceModel.getTipo());
                    pstmtServicio.setString(4, serviceModel.getTxt());
                    pstmtServicio.setBlob(5, serviceModel.getImatge());
                    pstmtServicio.setDate(6, sqlDataI);
                    pstmtServicio.setDate(7, sqlDataF);
                    pstmtServicio.setInt(8, serviceModel.getMida());
                    pstmtServicio.setInt(9, serviceModel.getColor());
                    pstmtServicio.setDouble(10, serviceModel.getPrecio());
                    pstmtServicio.setString(11, serviceModel.getPagamento());

                    if (serviceModel.getTipo() == 1)
                    {
                        pstmtServicio.setNull(12, java.sql.Types.INTEGER);
                        pstmtServicio.setInt(13, serviceModel.getIdw());
                        pstmtServicio.setNull(14, java.sql.Types.INTEGER);
                    } else if (serviceModel.getTipo() == 2)
                    {
                        pstmtServicio.setNull(12, java.sql.Types.INTEGER);
                        pstmtServicio.setNull(13, java.sql.Types.INTEGER);
                        pstmtServicio.setInt(14, serviceModel.getIdl());
                    } else if (serviceModel.getTipo() == 3)
                    {
                        pstmtServicio.setInt(12, serviceModel.getCp());
                        pstmtServicio.setNull(13, java.sql.Types.INTEGER);
                        pstmtServicio.setNull(14, java.sql.Types.INTEGER);
                    }

                    pstmtServicio.executeUpdate();
                }

                try (PreparedStatement pstmtRecibo = con.prepareStatement(sqlRecibo))
                {
                    pstmtRecibo.setInt(1, numR);
                    pstmtRecibo.setInt(2, 1);
                    pstmtRecibo.setInt(3, numC);
                    pstmtRecibo.setInt(4, numS);

                    pstmtRecibo.executeUpdate();
                }

                numS++;
                numR++;
            }

            // Enviar transacció
            con.commit();

            //JOptionPane.showMessageDialog(null, "El pedido se ha realizado correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("El pedido se ha realizado correctamente");

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
    }

    // Buscar el seguent contracte
    public static int findNewContract()
    {
        int maxNumc = 0;

        String sql = "SELECT max(numc) FROM contractacion";

        try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery())
        {
            if (rs.next())
            {
                maxNumc = rs.getInt(1) + 1;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return maxNumc;
    }

    // Buscar el seguent servei
    public static int findNewService()
    {
        int maxNums = 0;

        String sql = "SELECT max(nums) FROM servicio";

        try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery())
        {
            if (rs.next())
            {
                maxNums = rs.getInt(1) + 1;
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return maxNums;
    }

    // Buscar el seguent servei
    public static int findNewRecibo()
    {
        int maxNums = 0;

        String sql = "SELECT max(numr) FROM recibo";

        try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery())
        {
            if (rs.next())
            {
                maxNums = rs.getInt(1) + 1;
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return maxNums;
    }

    // Filtrar per tiquets d'un usuari
    public static Object[][] selectTiquet()
    {
        String sql = "SELECT recibo.numr, recibo.pagado, contractacion.datac, contractacion.estado, " +
                "servicio.datai, servicio.dataf, servicio.precio " +
                "FROM recibo " +
                "JOIN contractacion ON recibo.numc = contractacion.numc " +
                "JOIN servicio ON recibo.nums = servicio.nums " +
                "WHERE contractacion.cif = ?";

        UserModel user = UserModel.getInstance();
        String cif = user.getCif();

        List<Object[]> dataList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, cif);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                int reciboNum = rs.getInt("numr");
                String pagado = GeneralController.withColor(rs.getInt("pagado"));
                Date fechaC = rs.getDate("datac");
                String estadoContrato = rs.getString("estado");
                Date fechaInicio = rs.getDate("datai");
                Date fechaFin = rs.getDate("dataf");
                double precio = rs.getDouble("precio");

                dataList.add(new Object[]{reciboNum, pagado, fechaC, estadoContrato, fechaInicio, fechaFin, precio});
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        return dataList.toArray(new Object[dataList.size()][]);
    }

    // Filtrar per servicios d'un usuari
    public static Object[][] selectServicios()
    {
        String sql ="SELECT servicio.nums, servicio.tipo, servicio.precio, servicio.datai, servicio.dataf,servicio.mida, servicio.color " +
                "FROM servicio JOIN contractacion ON servicio.numc = contractacion.numc " +
                "WHERE contractacion.cif = ?";

        UserModel user = UserModel.getInstance();
        String cif = user.getCif();

        List<Object[]> dataList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, cif);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                int Numserv = rs.getInt("nums");
                int tipo = rs.getInt("tipo");
                String tipo1 =GeneralController.whatService(tipo);
                double precio = rs.getDouble("precio");
                Date fechaInicio = rs.getDate("datai");
                Date fechaFin = rs.getDate("dataf");
                int mida = rs.getInt("mida");
                String mida1 = GeneralController.whatSize(mida);
                int color = rs.getInt("color");
                String color1 = GeneralController.withColor(color);


                dataList.add(new Object[]{Numserv, tipo1, precio, fechaInicio, fechaFin, mida1, color1});
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        return dataList.toArray(new Object[dataList.size()][]);
    }
}
