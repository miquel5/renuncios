package view;

import controller.GeneralController;
import resources.Palette;
import resources.Sizes;
import view.components.*;
import service.DatabaseQueries;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameNewUser extends JPanel implements ActionListener
{
    private final ContainerText conUsuari;
    private final ContainerText conContrasenya;
    private final ContainerDropDawn conRol;
    private final ContainerText conCIF;
    private final ContainerText conEmpresa;
    private final ContainerText conSector;
    private final ContainerDropDawn conSede;
    private final InputButton btnBack;
    private final InputButton btnConfirm;
    private JPanel mainPanel;

    public FrameNewUser()
    {
        setLayout(new BorderLayout());

        // Elements
        conUsuari = new ContainerText("Usuario", 200, true);
        conContrasenya = new ContainerText("Contraseña", 200, false);
        conRol = new ContainerDropDawn("Rol", 200, new String[]{"Usuario", "Administrador"});
        conCIF = new ContainerText("CIF/NIF", 200, true);
        conEmpresa = new ContainerText("Empresa", 200, true);
        conSede = new ContainerDropDawn("Sede", 200, new String[]{"Madrid", "Barcelona", "Chicago", "Sao Paulo", "Rúsia"});
        conSector = new ContainerText("Sector", 200, true);

        btnBack = new InputButton("Atrás", false);
        btnConfirm = new InputButton("Crear usuario", true);

        btnBack.getButton().setPreferredSize(new Dimension(200, btnBack.getButton().getPreferredSize().height));
        btnConfirm.getButton().setPreferredSize(new Dimension(200, btnConfirm.getButton().getPreferredSize().height));

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Palette.c3);
        mainPanel.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, Sizes.x1, 0);

        JLabel t1 = new JLabel("NUEVO USUARIO");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
        t1.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
        t1.setForeground(Palette.c7);
        mainPanel.add(t1, gbc);

        gbc.gridy = 1;
        mainPanel.add(conUsuari, gbc);

        gbc.gridy = 2;
        mainPanel.add(conContrasenya, gbc);

        gbc.gridy = 3;
        mainPanel.add(conRol, gbc);

        gbc.gridy = 4;
        mainPanel.add(conCIF, gbc);

        gbc.gridy = 5;
        mainPanel.add(conEmpresa, gbc);

        gbc.gridy = 6;
        mainPanel.add(conSector, gbc);

        gbc.gridy = 7;
        mainPanel.add(conSede, gbc);

        gbc.gridy = 8;
        mainPanel.add(btnBack, gbc);

        gbc.gridy = 9;
        mainPanel.add(btnConfirm, gbc);

        add(mainPanel, BorderLayout.CENTER);

        btnBack.getButton().addActionListener(this);
        btnConfirm.getButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnBack.getButton())
        {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameNewUser.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameD2()); // Atrás
            frame.revalidate();
            frame.repaint();
        }
        else if (e.getSource() == btnConfirm.getButton())
        {
            saveUser();
        }
    }

    private void saveUser()
    {
        try
        {
            if (conUsuari.getText().trim().isEmpty() ||
                    conContrasenya.getText().trim().isEmpty() ||
                    conRol.getSelectedItem() == null ||
                    conCIF.getText().trim().isEmpty() ||
                    conEmpresa.getText().trim().isEmpty() ||
                    conSector.getText().trim().isEmpty() ||
                    conSede.getDropDawn() == null)
            {
                JOptionPane.showMessageDialog(this, "Error: Todos los campos son requeridos");
                return;
            }

            String usuario = conUsuari.getText();
            String contrasenya = conContrasenya.getText();
            String rol = conRol.getSelectedItem();
            String cif = conCIF.getText();
            String empresa = conEmpresa.getText();
            String sector = conSector.getText();
            String sede = conSede.getDropDawn();

            DatabaseQueries db = new DatabaseQueries();
            boolean userInserted = db.insertUser(usuario, contrasenya, rol, cif, empresa, sector, GeneralController.whatSedeInt(sede));

            if (userInserted)
            {
                JOptionPane.showMessageDialog(this, "Usuario guardado correctamente", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameNewUser.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameD2()); // Atrás
                frame.revalidate();
                frame.repaint();
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Error al guardar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(this, "Error al procesar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}