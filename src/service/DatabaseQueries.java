package service;

import controller.CartController;
import controller.GeneralController;
import model.CartModel;
import model.ServiceModel;
import model.UserModel;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static app.Main.con;

public class DatabaseQueries
{
    // Login
    public UserModel validateLogin(String username, String password)
    {
        String sql = "SELECT usuario.usuario, usuario.rol, cliente.cif, cliente.empresa, cliente.sector, cliente.ids, seu.ciudad AS sedenombre " +
                     "FROM usuario " +
                     "LEFT JOIN cliente ON usuario.usuario = cliente.usuario " +
                     "LEFT JOIN seu ON cliente.ids = seu.ids " +
                     "WHERE usuario.usuario = ? AND usuario.contrasenya = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                UserModel user = UserModel.getInstance();
                user.setUsername(rs.getString("usuario"));
                user.setRole(rs.getString("rol"));
                user.setCif(rs.getString("cif") != null ? rs.getString("cif") : "");
                user.setCompany(rs.getString("empresa") != null ? rs.getString("empresa") : "");
                user.setSector(rs.getString("sector") != null ? rs.getString("sector") : "");
                user.setSede(rs.getString("ids") != null ? rs.getString("ids") : "");
                user.setSede(rs.getString("sedenombre") != null ? rs.getString("sedenombre") : ""); // Nuevo campo

                return user;
            } else {
                System.out.println("Usuario no encontrado o contraseña incorrecta.");
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error al validar el login: " + e.getMessage());
            return null;
        }
    }

    // Register
    public UserModel validateRegister(String username, String company, String sector, String cif, String password, String repeatPassword, String role, String sede)
    {
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

            String insertClientSQL = "INSERT INTO cliente (cif, empresa, sector, usuario, ids) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = con.prepareStatement(insertClientSQL))
            {
                pstmt.setString(1, cif);
                pstmt.setString(2, company);
                pstmt.setString(3, sector);
                pstmt.setString(4, username);
                pstmt.setString(5, sede);

                pstmt.executeUpdate();
            }

            con.commit();

            user = new UserModel();
            user.setUsername(username);
            user.setCompany(company);
            user.setSector(sector);
            user.setCif(cif);
            user.setRole(role);
            user.setSede(sede);
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

    // Actualitzar dades usuari
    public static void updatePerfile(String username, String company, String sector, String sede)
    {
        String sqlClient = "UPDATE cliente SET empresa = ?, ids = ?, sector = ? WHERE usuario = ?";

        try
        {
            UserModel user = UserModel.getInstance(); // Usuari actual

            // Iniciar transacción
            con.setAutoCommit(false);

            try (PreparedStatement pstmtClient = con.prepareStatement(sqlClient))
            {
                // Taula cliente
                pstmtClient.setString(1, company);
                pstmtClient.setInt(2, Integer.parseInt(sede)); // Convertir-ho a int
                pstmtClient.setString(3, sector);
                pstmtClient.setString(4, username); // Del usuari

                pstmtClient.executeUpdate();

                // Confirmar la transacción
                con.commit();

                // Actualitzar dades
                user.setCompany(company);
                user.setSede(GeneralController.whatSedeString(sede)); // Passar al sede de int a String
                user.setSector(sector);

                System.out.println("Perfil actualizado correctamente.");
            }
            catch (SQLException ex)
            {
                con.rollback(); // Deshacer cambios en caso de error
                System.out.println("Error al actualizar el perfil: " + ex.getMessage());
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Error al conectar con la base de datos: " + ex.getMessage());
        }
    }

    // Generar els serveis
    public static List<ServiceModel> products()
    {
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
                pstmtContractacio.setString(2, "Activo");
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
                    pstmtServicio.setString(4, serviceModel.getTxt()); // todo

                    // Imatge
                    Blob blob = serviceModel.getImatge();
                    byte[] imageBytes = blob.getBytes(1, (int) blob.length());
                    pstmtServicio.setBlob(5, new ByteArrayInputStream(imageBytes));

                    pstmtServicio.setDate(6, sqlDataI);
                    pstmtServicio.setDate(7, sqlDataF);
                    pstmtServicio.setInt(8, serviceModel.getMida());
                    pstmtServicio.setInt(9, serviceModel.getColor());
                    pstmtServicio.setDouble(10, serviceModel.getPrecio());

                    if (serviceModel.getTipo() == 1)
                    {
                        pstmtServicio.setString(11, "1");
                        pstmtServicio.setNull(12, java.sql.Types.INTEGER);
                        pstmtServicio.setInt(13, serviceModel.getIdw());
                        pstmtServicio.setNull(14, java.sql.Types.INTEGER);
                    } else if (serviceModel.getTipo() == 2)
                    {
                        pstmtServicio.setString(11, "1");
                        pstmtServicio.setNull(12, java.sql.Types.INTEGER);
                        pstmtServicio.setNull(13, java.sql.Types.INTEGER);
                        pstmtServicio.setInt(14, serviceModel.getIdl());
                    } else if (serviceModel.getTipo() == 3)
                    {
                        pstmtServicio.setString(11, "2");
                        pstmtServicio.setInt(12, serviceModel.getCp());
                        pstmtServicio.setNull(13, java.sql.Types.INTEGER);
                        pstmtServicio.setNull(14, java.sql.Types.INTEGER);
                    }

                    pstmtServicio.executeUpdate();
                }

                try (PreparedStatement pstmtRecibo = con.prepareStatement(sqlRecibo))
                {
                    pstmtRecibo.setInt(1, numR);
                    pstmtRecibo.setInt(2, 2);
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

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
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

                dataList.add(new Object[]{reciboNum, pagado, fechaC, estadoContrato, fechaInicio, fechaFin, precio,"Pagar"});
            }

        } catch (SQLException e)
        {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        return dataList.toArray(new Object[dataList.size()][]);
    }

    // Filtrar per servicios d'un usuari
    public static Object[][] selectServicios() {
        String sql = "SELECT servicio.nums, servicio.tipo, servicio.precio, servicio.datai, servicio.dataf, " +
                     "servicio.mida, servicio.color, servicio.imatge " + // assuming the image is in the servicio table
                     "FROM servicio JOIN contractacion ON servicio.numc = contractacion.numc " +
                     "WHERE contractacion.cif = ?";

        UserModel user = UserModel.getInstance();
        String cif = user.getCif();

        List<Object[]> dataList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, cif);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int Numserv = rs.getInt("nums");
                String tipo = GeneralController.whatService(rs.getInt("tipo"));
                double precio = rs.getDouble("precio");
                Date fechaInicio = rs.getDate("datai");
                Date fechaFin = rs.getDate("dataf");
                String mida = GeneralController.whatSize(rs.getInt("mida"));
                String color = GeneralController.withColor(rs.getInt("color"));

                // Handling the image as a byte array
                byte[] imgData = rs.getBytes("imatge");
                ImageIcon imageIcon = null;
                if (imgData != null) {
                    ImageIcon originalIcon = new ImageIcon(imgData);
                    imageIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)); // Scale the image
                }

                dataList.add(new Object[]{Numserv, imageIcon, tipo, precio, fechaInicio, fechaFin, mida, color});
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        return dataList.toArray(new Object[dataList.size()][]);
    }

    // Mostrar usuaris
    public static Object[][] selectAllUsuarios()
    {
        String sql = "SELECT u.usuario, u.rol, c.sector, c.cif, c.empresa " +
                "FROM usuario u " +
                "JOIN cliente c ON u.usuario = c.usuario";

        List<Object[]> dataList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                String usuario = rs.getString("usuario");
                String rol = rs.getString("rol");
                String sector = rs.getString("sector");
                String cif = rs.getString("cif");
                String empresa = rs.getString("empresa");

                dataList.add(new Object[]{usuario, rol, sector, cif, empresa, "Eliminar"});
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        return dataList.toArray(new Object[dataList.size()][]);
    }

    // Mostrar servicios
    public static Object[][] selectAllServicios() {
        String sql = "SELECT servicio.nums, servicio.tipo, servicio.datai, servicio.dataf, servicio.color, servicio.pagamento, servicio.imatge " +
                "FROM servicio";

        List<Object[]> dataList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int nums = rs.getInt("nums");
                String tipo = GeneralController.whatService(rs.getInt("tipo"));
                Date fechaInicio = rs.getDate("datai");
                Date fechaFin = rs.getDate("dataf");
                String color = GeneralController.withColor(rs.getInt("color"));
                String pago = GeneralController.withPay(rs.getInt("pagamento"));

                // Leer el BLOB de la base de datos
                byte[] imgData = rs.getBytes("imatge");
                ImageIcon imageIcon = null;
                if (imgData != null) {
                    ImageIcon originalIcon = new ImageIcon(imgData);
                    imageIcon = new ImageIcon(originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)); // Redimensionar la imagen
                }

                // Agregar los datos a la lista
                dataList.add(new Object[]{nums, imageIcon, tipo, fechaInicio, fechaFin, color, pago, "Eliminar"});
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        return dataList.toArray(new Object[dataList.size()][]);
    }


    // Mostrar tiquets
    public static Object[][] selectAllTiquets()
    {
        String sql = "SELECT recibo.numr, contractacion.numc, servicio.nums, contractacion.cif, " +
                     "servicio.tipo, servicio.pagamento, servicio.precio, recibo.pagado " +
                     "FROM recibo " +
                     "JOIN contractacion ON recibo.numc = contractacion.numc " +
                     "JOIN servicio ON recibo.nums = servicio.nums";

        List<Object[]> dataList = new ArrayList<>();

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next())
            {
                int numRecibo = rs.getInt("numr");
                int numContractacion = rs.getInt("numc");
                int numServicio = rs.getInt("nums");
                String cif = rs.getString("cif");
                String tipo = GeneralController.whatService(rs.getInt("tipo"));
                //String pagamento = rs.getString("pagamento");
                double precio = rs.getDouble("precio");
                String pagado = GeneralController.withColor(rs.getInt("pagado"));

                dataList.add(new Object[]{numRecibo, numContractacion, numServicio, cif, tipo, precio, pagado});
            }

        } catch (SQLException e)
        {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        return dataList.toArray(new Object[dataList.size()][]);
    }















































    // FALTA ARREGLAR ENCARA LA LÓGICA

    // Método para obtener un nuevo uniqueId
    public static int getNewUniqueId() {
        int maxUniqueId = 0;
        String sql = "SELECT MAX(UNIQUEID) FROM WEB"; // Asegúrate de que la tabla y columna sean correctas

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                maxUniqueId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el nuevo uniqueId: " + e.getMessage());
        }

        return maxUniqueId + 1; // Retorna el siguiente uniqueId
    }

    // Método para insertar una nueva web
    public boolean insertWeb(String nombre, String enlace, double preup, double preum, double preug, int uniqueId) {
        String sql = "INSERT INTO WEB (NOMBRE, ENLACE, PREUP, PREUM, PREUG, UNIQUEID) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, enlace);
            pstmt.setDouble(3, preup);
            pstmt.setDouble(4, preum);
            pstmt.setDouble(5, preug);
            pstmt.setInt(6, uniqueId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Retorna true si se insertó correctamente
        } catch (SQLException e) {
            System.out.println("Error al insertar la web: " + e.getMessage());
            return false; // Retorna false si hubo un error
        }
    }

    // Método para obtener un nuevo uniqueId para flayer
    public static int getNewUniqueIdForFlayer() {
        int maxUniqueId = 0;
        String sql = "SELECT MAX(UNIQUEID) FROM BARRIO";

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                maxUniqueId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el nuevo uniqueId para flayer: " + e.getMessage());
        }

        return maxUniqueId + 1; // Retorna el siguiente uniqueId
    }

    public boolean insertFlayer(String poblacion, String provincia, double precio, int uniqueId) {
        String sql = "INSERT INTO BARRIO (POBLACION, PROVINCIA, PREU, UNIQUEID) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, poblacion);
            pstmt.setString(2, provincia);
            pstmt.setDouble(3, precio);
            pstmt.setInt(4, uniqueId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Retorna true si se insertó correctamente
        } catch (SQLException e) {
            System.out.println("Error al insertar el flayer: " + e.getMessage());
            return false; // Retorna false si hubo un error
        }
    }

    // Método para insertar una nueva pancarta
    public boolean insertPancarta(String descripcion, String coordenadas, double precio) {
        int uniqueId = getNextIdL(); // Obtener el siguiente IDL de la secuencia
        String sql = "INSERT INTO LOCALIZACION (IDL, DESCRIP, COORDENADAS, PRECIO) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, uniqueId); // Establecer el IDL
            pstmt.setString(2, descripcion);
            pstmt.setString(3, coordenadas);
            pstmt.setDouble(4, precio);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Retorna true si se insertó correctamente
        } catch (SQLException e) {
            System.out.println("Error al insertar la pancarta: " + e.getMessage());
            return false; // Retorna false si hubo un error
        }
    }

    // Método para obtener el siguiente IDL de la secuencia
    private int getNextIdL() {
        int nextId = 0;
        String sql = "SELECT seq_idl.NEXTVAL FROM dual"; // Reemplaza 'seq_idl' con el nombre de tu secuencia

        try (PreparedStatement pstmt = con.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                nextId = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el siguiente IDL: " + e.getMessage());
        }

        return nextId; // Retorna el siguiente IDL
    }

    // Insertar usuario
    public boolean insertUser(String usuario, String contrasenya, String rol, String cif, String empresa, String sector, String sede)
    {
        try
        {
            // Iniciar transacción
            con.setAutoCommit(false);

            // Insertar en la tabla USUARIO
            String insertUserSQL = "INSERT INTO USUARIO (USUARIO, CONTRASENYA, ROL) VALUES (?, ?, ?)";

            try (PreparedStatement pstmtUser = con.prepareStatement(insertUserSQL))
            {
                pstmtUser.setString(1, usuario);
                pstmtUser.setString(2, contrasenya);
                pstmtUser.setString(3, rol);
                pstmtUser.executeUpdate();
            }

            // Insertar en la tabla CLIENTE
            String insertClientSQL = "INSERT INTO CLIENTE (CIF, EMPRESA, SECTOR, USUARIO, IDS) VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmtClient = con.prepareStatement(insertClientSQL))
            {
                pstmtClient.setString(1, cif);
                pstmtClient.setString(2, empresa);
                pstmtClient.setString(3, sector);
                pstmtClient.setString(4, usuario);
                pstmtClient.setString(5, sede);
                pstmtClient.executeUpdate();
            }

            con.commit(); // Confirmar transacción
            return true; // Retorna true si se insertó correctamente
        } catch (SQLException e)
        {
            try
            {
                con.rollback(); // Revertir transacción en caso de error
            } catch (SQLException ex)
            {
                System.out.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.out.println("Error al insertar el usuario: " + e.getMessage());

            return false; // Retorna false si hubo un error
        } finally
        {
            try
            {
                con.setAutoCommit(true); // Restaurar auto-commit
            } catch (SQLException e)
            {
                System.out.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    // Saber si esta pagado
    public static boolean verRecibopagado(int i)
    {
        String sql = "SELECT pagado FROM recibo WHERE numr = ?";
        int nexpagad = 0;

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setInt(1, i);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next())
            {
                nexpagad = rs.getInt("pagado");

                if (nexpagad == 1) {
                    return true;
                }
            }

        } catch (SQLException e)
        {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    //Pagar un recibo
    public static boolean pagarRecibo(int i)
    {
        String sql = "UPDATE recibo SET pagado = ? WHERE numr = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql))
        {
            pstmt.setInt(1, 1);
            pstmt.setInt(2, i);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0)
            {
                System.out.println("Recibo pagado correctamente.");
                return true;
            } else
            {
                System.out.println("No se encontró el recibo con el número " + i);
            }
        } catch (SQLException e)
        {
            System.out.println("Error al realizar el pago: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public static boolean eliminarUsuario(String cif) {
        try {
            con.setAutoCommit(false);

            // 1. Obtener los nombres de usuario relacionados con el CIF
            String selectUsuariosSQL = "SELECT usuario FROM cliente WHERE cif = ?";
            List<String> usuarios = new ArrayList<>();
            try (PreparedStatement pstmtSelectUsuarios = con.prepareStatement(selectUsuariosSQL)) {
                pstmtSelectUsuarios.setString(1, cif);
                try (ResultSet rs = pstmtSelectUsuarios.executeQuery()) {
                    while (rs.next()) {
                        usuarios.add(rs.getString("usuario")); // ens guardem el usauri per poder elimnar la 5a
                    }
                }
            }

            // 2. Eliminar registros de la tabla RECIBO relacionados con el CIF
            String deleteReciboSQL = "DELETE FROM recibo WHERE (numc, nums) IN (SELECT s.numc, s.nums FROM servicio s JOIN contractacion c ON s.numc = c.numc WHERE c.cif = ?)";
            try (PreparedStatement pstmtRecibo = con.prepareStatement(deleteReciboSQL)) {
                pstmtRecibo.setString(1, cif);
                pstmtRecibo.executeUpdate();
            }

            // 3. Eliminar registros de la tabla SERVICIO relacionados con el CIF
            String deleteServicioSQL = "DELETE FROM servicio WHERE numc IN (SELECT c.numc FROM contractacion c WHERE c.cif = ?)";
            try (PreparedStatement pstmtServicio = con.prepareStatement(deleteServicioSQL)) {
                pstmtServicio.setString(1, cif);
                pstmtServicio.executeUpdate();
            }

            // 4. Eliminar registros de la tabla CONTRACTACION relacionados con el CIF
            String deleteContractacionSQL = "DELETE FROM contractacion WHERE cif = ?";
            try (PreparedStatement pstmtContractacion = con.prepareStatement(deleteContractacionSQL)) {
                pstmtContractacion.setString(1, cif);
                pstmtContractacion.executeUpdate();
            }

            // 5. Eliminar registros de la tabla CLIENTE relacionados con el CIF
            String deleteClienteSQL = "DELETE FROM cliente WHERE cif = ?";
            try (PreparedStatement pstmtCliente = con.prepareStatement(deleteClienteSQL)) {
                pstmtCliente.setString(1, cif);
                pstmtCliente.executeUpdate();
            }

            // 6. Finalmente, eliminar registros de la tabla USUARIO
            if (!usuarios.isEmpty()) {
                String deleteUsuarioSQL = "DELETE FROM usuario WHERE usuario = ?";
                try (PreparedStatement pstmtUsuario = con.prepareStatement(deleteUsuarioSQL)) {
                    for (String usuario : usuarios) {
                        pstmtUsuario.setString(1, usuario);
                        pstmtUsuario.executeUpdate();
                    }
                }
            }

            // Confirmar la transacción
            con.commit();
            return true;

        } catch (SQLException e) {
            try {
                con.rollback(); // Revertir la transacción en caso de error
                System.out.println("Se ha realizado un rollback debido a: " + e.getMessage());
            } catch (SQLException ex) {
                System.out.println("Error al realizar rollback: " + ex.getMessage());
            }
            return false; // Retornar false si hubo un error
        } finally {
            try {
                con.setAutoCommit(true); // Restaurar auto-commit
            } catch (SQLException e) {
                System.out.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }

    public static boolean eliminarServicio(int nums)
    {
        // Iniciar la transacción
        try {
            con.setAutoCommit(false);

            // 1. Eliminar registros de la tabla RECIBO relacionados con el servicio
            String deleteReciboSQL = "DELETE FROM recibo WHERE nums = ?";
            try (PreparedStatement pstmtRecibo = con.prepareStatement(deleteReciboSQL)) {
                pstmtRecibo.setInt(1, nums);
                pstmtRecibo.executeUpdate();
            }

            // 2. Eliminar registros de la tabla SERVICIO relacionados con el servicio
            String deleteServicioSQL = "DELETE FROM servicio WHERE nums = ?";
            try (PreparedStatement pstmtServicio = con.prepareStatement(deleteServicioSQL)) {
                pstmtServicio.setInt(1, nums);
                pstmtServicio.executeUpdate();
            }

            // Confirmar la transacción
            con.commit();
            return true;

        } catch (SQLException e) {
            try {
                con.rollback(); // Revertir transacción en caso de error
                System.out.println("Se ha realizado un rollback debido a: " + e.getMessage());
            } catch (SQLException ex) {
                System.out.println("Error al realizar rollback: " + ex.getMessage());
            }
            return false; // Retorna false si hubo un error
        } finally {
            try {
                con.setAutoCommit(true); // Restaurar auto-commit
            } catch (SQLException e) {
                System.out.println("Error al restaurar auto-commit: " + e.getMessage());
            }
        }
    }
}
