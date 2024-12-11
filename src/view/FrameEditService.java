package view;

import controller.CartController;
import controller.GeneralController;
import model.CartModel;
import model.ServiceModel;
import resources.Palette;
import resources.Sizes;
import view.components.*;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class FrameEditService extends JPanel implements ActionListener
{
    private final ContainerDropDawn conSize;
    private final ContainerText conDescripcio;
    private final ContainerText conName;
    private final ContainerText conPrice;
    private final ContainerText conDataI;
    private final ContainerText conDataF;
    private final CheckBox boxColor;
    private final InputButton btnArchive;
    private final InputButton btnBack;
    private final InputButton btnConfirm;
    private File selectedImage;
    private double priceTotal;
    private ServiceModel serviceModel;

    public FrameEditService(int uniqueId) {
        CartController cartController = new CartController();
        priceTotal = 0;

        // Configurar la pantalla
        setLayout(new BorderLayout());

        System.out.println("Help: Id " + uniqueId);

        // Elements
        conSize = new ContainerDropDawn("Tamaño", 200, new String[]{"Pequeño", "Mediano", "Grande"});
        conDescripcio = new ContainerText("Descripción", 200, true);
        conName = new ContainerText("Nombre", 200, true);
        conPrice = new ContainerText("Precio", 200, true);
        conDataI = new ContainerText("Data inicio", 200, true);
        conDataF = new ContainerText("Data final", 200, true);
        boxColor = new CheckBox("Color", 200);
        btnArchive = new InputButton("Subir imagen", false);
        btnBack = new InputButton("Atrás", false);
        btnConfirm = new InputButton("Confirmar", true);

        // Agafar tota la información del numS i verificar si existeix
        serviceModel = cartController.findService(uniqueId);

        if (serviceModel == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el servicio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(Palette.c3);
        main.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, Sizes.x1, 0);

        // Funciones dependiendo de cada servicio
        if (serviceModel.getTipo() == 1)
        {
            // Desplegable tamaño
            switch (serviceModel.getMida()) {
                case 1:
                    conSize.setSelectedItem("Pequeño");
                    priceTotal = serviceModel.getWPreup(); // Preu petit
                    break;
                case 2:
                    conSize.setSelectedItem("Mediano");
                    priceTotal = serviceModel.getWPreum(); // Preu mitjà
                    break;
                case 3:
                    conSize.setSelectedItem("Grande");
                    priceTotal = serviceModel.getWPreug(); // Preu gran
                    break;
            }

            conPrice.setText(GeneralController.formatPrice(priceTotal * 30)); // Asignar el precio
            gbc.gridy = 2;
            main.add(conSize, gbc);
        } else if (serviceModel.getTipo() == 2)
        {
            // Precio fijo
            priceTotal = serviceModel.getPrecio();
            conPrice.setText(GeneralController.formatPrice(priceTotal));
        } else if (serviceModel.getTipo() == 3)
        {
            // Checkbox de color
            switch (serviceModel.getColor()) {
                case 1:
                    boxColor.getCheckBox().setSelected(true);
                    break;
                case 2:
                    boxColor.getCheckBox().setSelected(false);
                    break;
            }
            gbc.gridy = 5;
            main.add(boxColor, gbc);

            // Precio
            priceTotal = serviceModel.getPrecio();
            conPrice.setText(GeneralController.formatPrice(serviceModel.getPrecio()));
        }

        // En caso de ser mensual
        if (serviceModel.getTipo() == 1 || serviceModel.getTipo() == 2)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // Fecha de inicio
            gbc.gridy = 6;
            if (serviceModel.getDataF() != null) {
                String fechaInicio = sdf.format(serviceModel.getDataI());
                conDataI.setText(fechaInicio);
            }

            main.add(conDataI, gbc);

            // Fecha final
            gbc.gridy = 7;
            if (serviceModel.getDataF() != null) {
                String fechaFinal = sdf.format(serviceModel.getDataF());
                conDataF.setText(fechaFinal);
            }

            main.add(conDataF, gbc);

            // Calcular el tiempo * precio
            ActionListener dateChangeListener = e -> updatePriceBasedOnSizeAndDates();
            conDataI.getTextField().addActionListener(dateChangeListener);
            conDataF.getTextField().addActionListener(dateChangeListener);
        }

        // Input precio
        gbc.gridy = 8;
        conPrice.setEditable(false); // Desactivar que se pueda modificar
        main.add(conPrice, gbc);

        // Botón archivo
        gbc.gridy = 9;
        btnArchive.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        btnArchive.getButton().addActionListener(this);
        main.add(btnArchive, gbc);

        // Botón atrás
        gbc.gridy = 10;
        btnBack.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        btnBack.getButton().addActionListener(this);
        main.add(btnBack, gbc);

        // Botón confirmar
        gbc.gridy = 11;
        btnConfirm.setPreferredSize(new Dimension(200, btnConfirm.getPreferredSize().height));
        btnConfirm.getButton().addActionListener(this);
        main.add(btnConfirm, gbc);

        add(main, BorderLayout.CENTER);

        // Actualización dinámica del precio según el tamaño
        conSize.comboBox.addActionListener(e -> updatePriceBasedOnSizeAndDates());
    }

    private void updatePriceBasedOnSizeAndDates() {
        // Actualizar el precio según el tamaño seleccionado
        String selectedSize = (String) conSize.getDropDawn();

        if (selectedSize.equals("Pequeño"))
        {
            priceTotal = serviceModel.getWPreup();
        } else if (selectedSize.equals("Mediano"))
        {
            priceTotal = serviceModel.getWPreum();
        } else if (selectedSize.equals("Grande"))
        {
            priceTotal = serviceModel.getWPreug();
        }

        // Ahora calcular el precio basado en las fechas
        calculatePriceBasedOnDates();
    }

    private void calculatePriceBasedOnDates()
    {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(conDataI.getText(), formatter);
            LocalDate endDate = LocalDate.parse(conDataF.getText(), formatter);

            if (endDate.isBefore(startDate))
            {
                JOptionPane.showMessageDialog(this, "La fecha final no puede ser anterior a la fecha inicial.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long days = ChronoUnit.DAYS.between(startDate, endDate); // Contar los días

            // Precio proporcional a los días
            double calculatedPrice = priceTotal * days; // Multiplicamos por los días

            // Guardamos el precio calculado en el modelo
            serviceModel.setPrecio(calculatedPrice); // Actualizamos el precio del servicio
            conPrice.setText(GeneralController.formatPrice(calculatedPrice)); // Actualizamos la visualización del precio

        } catch (Exception e)
        {
            conPrice.setText("Error");
            System.out.println("Error al calcular el precio: " + e.getMessage());
        }
    }

    private void handleFileSelection() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg") ||
                        f.getName().toLowerCase().endsWith(".jpeg") ||
                        f.getName().toLowerCase().endsWith(".png") ||
                        f.isDirectory();
            }

            public String getDescription() {
                return "Image files (*.jpg, *.jpeg, *.png)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImage = fileChooser.getSelectedFile();
            btnArchive.getButton().setText(selectedImage.getName());

            // Convertir la imagen seleccionada en un Blob y asignarla al modelo
            try {
                byte[] fileContent = java.nio.file.Files.readAllBytes(selectedImage.toPath());
                java.sql.Blob blob = new javax.sql.rowset.serial.SerialBlob(fileContent);
                serviceModel.setImatge(blob);
                System.out.println("Imagen asignada al modelo correctamente.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack.getButton()) {
            // Volver atrás
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameEditService.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameSummary());
            frame.revalidate();
            frame.repaint();
        } else if (e.getSource() == btnConfirm.getButton()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            if (serviceModel.getTipo() == 1 || serviceModel.getTipo() == 2)
            {
                // Convertir LocalDate a java.util.Date
                LocalDate startDate = LocalDate.parse(conDataI.getText(), formatter);
                LocalDate endDate = LocalDate.parse(conDataF.getText(), formatter);

                // Convertir LocalDate a java.sql.Date
                java.sql.Date startDateSql = java.sql.Date.valueOf(startDate);
                java.sql.Date endDateSql = java.sql.Date.valueOf(endDate);

                // Enviar datos generales
                serviceModel.setDataI(startDateSql);
                serviceModel.setDataF(endDateSql);
            }

            // Enviar datos según el tipo de servicio
            if (serviceModel.getTipo() == 1) {
                sendWeb();
            } else if (serviceModel.getTipo() == 2) {
                sendLocation();
            } else if (serviceModel.getTipo() == 3) {
                sendBarrio();
            }

            // Obtener el precio actual antes de actualizar
            double pantic = Double.parseDouble(conPrice.getText().replace(",", ".")); // Precio anterior

            // Actualizar el precio con el nuevo valor
            serviceModel.setPrecio(pantic); // Actualizar precio del servicio con el nuevo valor

            // Obtener el modelo del carrito
            CartModel cartModel = CartModel.getInstance();

            // calcular la diferéncia
            double nuevoTotal = cartModel.getTotal() - serviceModel.getTotal() + pantic;

            // Establecer el nuevo total
            cartModel.setTotal(nuevoTotal);

            // Redibujar la interfaz con los nuevos datos
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameEditService.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameSummary());
            frame.revalidate();
            frame.repaint();
        } else if (e.getSource() == btnArchive.getButton()) {
            handleFileSelection(); // Activar para todos los tipos
        }
    }

    private void sendWeb() {
        // Lógica para enviar datos Web
        if (conSize.getDropDawn().equals("Pequeño")) {
            serviceModel.setMida(1);
        } else if (conSize.getDropDawn().equals("Mediano")) {
            serviceModel.setMida(2);
        } else if (conSize.getDropDawn().equals("Grande")) {
            serviceModel.setMida(3);
        }
    }

    private void sendLocation() {
        // Lógica para enviar datos Location
    }

    private void sendBarrio() {
        // Enviar color
        serviceModel.setColor(boxColor.isSelected() ? 1 : 2);
    }
}
