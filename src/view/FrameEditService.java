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
            // Desplegable tamaño
            switch (serviceModel.getMida())
            {
                case 1:
                    conSize.setSelectedItem("Pequeño");
                    break;
                case 2:
                    conSize.setSelectedItem("Mediano");
                    break;
                case 3:
                    conSize.setSelectedItem("Grande");
                    break;
            }


            gbc.gridy = 2;
            main.add(conSize, gbc);
        } else if (serviceModel.getTipo() == 2)
        {

        } else if (serviceModel.getTipo() == 3)
        {
            // Checkbox de color
            gbc.gridy = 3;
            main.add(boxColor, gbc);
        }

        // Input nombre
        gbc.gridy = 4;
        main.add(conName, gbc);

        // Input precio
        gbc.gridy = 5;
        main.add(conPrice, gbc);

        // Desplegable mes
        gbc.gridy = 7;
        main.add(conMes, gbc);

        // Botón archivo
        gbc.gridy = 8;
        btnArchive.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        btnArchive.getButton().addActionListener(this);
        main.add(btnArchive, gbc);

        // Botón atrás
        gbc.gridy = 9;
        btnBack.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        btnBack.getButton().addActionListener(this);
        main.add(btnBack, gbc);

        // Botón confirmar
        gbc.gridy = 10;
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
            serviceModel.setMida(1);
        } else if (conSize.getDropDawn().equals("Mediano"))
        {
            serviceModel.setMida(2);
        } else if (conSize.getDropDawn().equals("Grande"))
        {
            serviceModel.setMida(3);
        }

        //

    }

    private void sendLocation()
    {

    }

    private void sendBarrio()
    {

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

            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameEditService.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameSummary());
            frame.revalidate();
            frame.repaint();
        }
    }
}