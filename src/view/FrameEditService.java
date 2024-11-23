package view;

import controller.CartController;
import model.ServiceModel;
import resources.Palette;
import resources.Sizes;
import view.components.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
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
    private final ContainerDropDawn conMes;
    private final InputButton btnArchive;
    private final InputButton btnBack;
    private final InputButton btnConfirm;
    private final File selectedImage = null;

    private ServiceModel serviceModel;

    public FrameEditService(int uniqueId)
    {
        CartController cartController = new CartController();

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
        conMes = new ContainerDropDawn("Tipo de pago", 200, new String[]{"Único", "Mensual"});
        btnArchive = new InputButton("Subir imagen", false);
        btnBack = new InputButton("Atrás", false);
        btnConfirm = new InputButton("Confirmar", true);

        // Agafar tota la informacó del numS i verificar si existeix
        serviceModel = cartController.findService(uniqueId);

        if (serviceModel == null)
        {
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

        // Funciones depenent de cada servei
        if (serviceModel.getTipo() == 1)
        {
            // Desplegable tamaño
            switch (serviceModel.getMida())
            {
                case 1:
                    conSize.setSelectedItem("Pequeño");
                    conPrice.setText(String.valueOf(serviceModel.getWPreup())); // Asignar preu petit
                    break;
                case 2:
                    conSize.setSelectedItem("Mediano");
                    conPrice.setText(String.valueOf(serviceModel.getWPreum())); // Asignar preu mitja
                    break;
                case 3:
                    conSize.setSelectedItem("Grande");
                    conPrice.setText(String.valueOf(serviceModel.getWPreug())); // Asignar preu gran
                    break;
            }

            gbc.gridy = 2;
            main.add(conSize, gbc);
        } else if (serviceModel.getTipo() == 2)
        {
            // Preu
            conPrice.setText(String.valueOf(serviceModel.getPrecio()));
        } else if (serviceModel.getTipo() == 3)
        {
            // Checkbox de color
            switch (serviceModel.getColor())
            {
                case 1:
                    boxColor.getCheckBox().setSelected(true);
                    break;
                case 2:
                    boxColor.getCheckBox().setSelected(false);
                    break;
            }

            gbc.gridy = 5;
            main.add(boxColor, gbc);

            // Preu
            conPrice.setText(String.valueOf(serviceModel.getPrecio()));
        }

        // En cas de ser mensual
        if (conMes.getDropDawn().equals("Mensual"))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Puedes cambiar el formato según necesites

            // Fecha de inicio
            gbc.gridy = 6;
            if (serviceModel.getDataF() != null)
            {
                String fechaInicio = sdf.format(serviceModel.getDataI());
                conDataI.setText(fechaInicio);
            }

            main.add(conDataI, gbc);

            // Fecha final
            gbc.gridy = 7;
            if (serviceModel.getDataF() != null)
            {
                String fechaFinal = sdf.format(serviceModel.getDataF());
                conDataF.setText(fechaFinal);
            }

            main.add(conDataF, gbc);

            // Calcular el temps * preu
            ActionListener dateChangeListener = e -> calculatePriceBasedOnDates();
            conDataI.getTextField().addActionListener(dateChangeListener);
            conDataF.getTextField().addActionListener(dateChangeListener);
        }

        // Input precio
        gbc.gridy = 8;
        conPrice.setEditable(false); // Desactivar que és pugui modificar
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

        // Obtenir de forma dinàmica el preu
        conSize.comboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String selectedSize = (String) conSize.getDropDawn();

                if (selectedSize.equals("Pequeño"))
                {
                    conPrice.setText(String.valueOf(serviceModel.getWPreup()));
                } else if (selectedSize.equals("Mediano"))
                {
                    conPrice.setText(String.valueOf(serviceModel.getWPreum()));
                } else if (selectedSize.equals("Grande"))
                {
                    conPrice.setText(String.valueOf(serviceModel.getWPreug()));
                }
            }
        });

    }

    private void calculatePriceBasedOnDates()
    {
        try
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(conDataI.getText(), formatter);
            LocalDate endDate = LocalDate.parse(conDataF.getText(), formatter);

            if (endDate.isBefore(startDate))
            {
                JOptionPane.showMessageDialog(this, "La fecha final no puede ser anterior a la fecha inicial.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            long days = ChronoUnit.DAYS.between(startDate, endDate);
            double basePrice = serviceModel.getPrecio();
            conPrice.setText(String.valueOf(basePrice * days));
        }
        catch (Exception e)
        {
            conPrice.setText("Error");
            System.out.println("Error al calcular el precio: " + e.getMessage());
        }
    }

    private void sendWeb()
    {
        // Mida
        if (conSize.getDropDawn().equals("Pequeño"))
        {
            serviceModel.setPrecio(serviceModel.getWPreup()); // Assignar preu petit
            serviceModel.setMida(1);
        } else if (conSize.getDropDawn().equals("Mediano"))
        {
            serviceModel.setPrecio(serviceModel.getWPreum()); // Assignar preu mitjá
            serviceModel.setMida(2);
        } else if (conSize.getDropDawn().equals("Grande"))
        {
            serviceModel.setPrecio(serviceModel.getWPreug()); // Assignar preu gran
            serviceModel.setMida(3);
        }

        //Mes
        if (conMes.getDropDawn().equals("Único"))
        {
            serviceModel.setMes(1);
        } else if (conMes.getDropDawn().equals("Mensual"))
        {
            serviceModel.setMes(2);
        }

        // Preu

    }

    private void sendLocation()
    {
        // Mes
        if (conMes.getDropDawn().equals("Único"))
        {
            serviceModel.setMes(1);
        } else if (conMes.getDropDawn().equals("Mensual"))
        {
            serviceModel.setMes(2);
        }

        // Preu
    }

    private void sendBarrio()
    {
        // Color
        serviceModel.setColor(boxColor.isSelected() ? 1 : 2);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnArchive.getButton())
        {
            // TODO: Agregar imagen
        } else if (e.getSource() == btnBack.getButton())
        {
            // Acción de "Atrás"
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameEditService.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameSummary());
            frame.revalidate();
            frame.repaint();
        } else if (e.getSource() == btnConfirm.getButton())
        {
            // Enviar datos según el tipo de servicio
            if (serviceModel.getTipo() == 1) {
                sendWeb();
            } else if (serviceModel.getTipo() == 2) {
                sendLocation();
            } else if (serviceModel.getTipo() == 3) {
                sendBarrio();
            }

            // Actualizar la pantalla
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameEditService.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameSummary());
            frame.revalidate();
            frame.repaint();
        }
    }
}
