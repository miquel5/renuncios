package view;

import controller.CartController;
import model.ServiceModel;
import resources.Palette;
import resources.Sizes;
import view.components.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FrameEditService extends JPanel implements ActionListener
{
    private final ContainerDropDawn conSize;
    private final ContainerText conDescripcio;
    private final ContainerText conUrl;
    private final ContainerText conName;
    private final ContainerText conPrice;
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

        System.out.println("Help: Edit service " + uniqueId);

        // Elements
        conSize = new ContainerDropDawn("Tamaño", 200, new String[]{"Pequeño", "Mediano", "Grande"});
        conDescripcio = new ContainerText("Descripción", 200, true);
        conUrl = new ContainerText("Enlace", 200, true);
        conName = new ContainerText("Nombre", 200, true);
        conPrice = new ContainerText("Precio", 200, true);
        boxColor = new CheckBox("Color", 200);
        conMes= new ContainerDropDawn("Tipo de pago", 200, new String[]{"Único", "Mensual"});
        btnArchive = new InputButton("Subir imagen", false);
        btnBack = new InputButton("Atrás", false);
        btnConfirm = new InputButton("Confirmar", true);

        // Agafar tota la informacó del numS i verificar si existeix
        serviceModel = cartController.findService(uniqueId);

        if (serviceModel == null)
        {
            JOptionPane.showMessageDialog(this, "No se encontró el servicio","Error", JOptionPane.ERROR_MESSAGE);
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

        // Funcions depenent de cada servei
        if (serviceModel.getTipo() == 1)
        {
            // Desplegable mes
            switch (serviceModel.getMes())
            {
                case 1:
                    conMes.setSelectedItem("Único");
                    break;
                case 2:
                    conMes.setSelectedItem("Mensual");
                    break;
            }

            gbc.gridy = 1;
            main.add(conMes, gbc);

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

            /*// Descripció
            gbc.gridy = 3;
            main.add(conDescripcio, gbc);*/

            /*// Url
            gbc.gridy = 4;
            main.add(conUrl, gbc);*/
        } else if (serviceModel.getTipo() == 2)
        {
            // Desplegable mes
            switch (serviceModel.getMes())
            {
                case 1:
                    conMes.setSelectedItem("Único");
                    break;
                case 2:
                    conMes.setSelectedItem("Mensual");
                    break;
            }

            gbc.gridy = 5;
            main.add(conMes, gbc);

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

            gbc.gridy = 6;
            main.add(boxColor, gbc);

            // Preu
            conPrice.setText(String.valueOf(serviceModel.getPrecio()));
        }

        /*// Descripció
        gbc.gridy = 7;
        main.add(conDescripcio, gbc);*/

        /*// Input nombre
        gbc.gridy = 8;
        main.add(conName, gbc);*/

        // Input precio
        gbc.gridy = 9;
        conPrice.setEditable(false); // Desactivar que és pugui modificar
        main.add(conPrice, gbc);

        // Botón archivo
        gbc.gridy = 10;
        btnArchive.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        btnArchive.getButton().addActionListener(this);
        main.add(btnArchive, gbc);

        // Botón atrás
        gbc.gridy = 11;
        btnBack.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        btnBack.getButton().addActionListener(this);
        main.add(btnBack, gbc);

        // Botón confirmar
        gbc.gridy = 12;
        btnConfirm.setPreferredSize(new Dimension(200, btnConfirm.getPreferredSize().height));
        btnConfirm.getButton().addActionListener(this);
        main.add(btnConfirm, gbc);

        add(main, BorderLayout.CENTER);
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

        } else if (e.getSource() == btnBack.getButton())
        {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameEditService.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameSummary());
            frame.revalidate();
            frame.repaint();
        } else if (e.getSource() == btnConfirm.getButton())
        {
            // Enviar dades per tipus de servei
            if (serviceModel.getTipo() == 1)
            {
                sendWeb();
            } else if (serviceModel.getTipo() == 2)
            {
                sendLocation();
            } else if (serviceModel.getTipo() == 3)
            {
                sendBarrio();
            }

            // Carregar frame
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameEditService.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameSummary());
            frame.revalidate();
            frame.repaint();
        }
    }
}