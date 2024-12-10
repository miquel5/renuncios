package view;

import controller.GeneralController;
import model.UserModel;
import resources.Palette;
import resources.Sizes;
import service.DatabaseQueries;
import view.components.ContainerDropDawn;
import view.components.ContainerText;
import view.components.InputButton;
import view.components.PanelSidebar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FramePerfile extends JPanel implements ActionListener
{
    private final ContainerText conUsername;
    private final ContainerText conRole;
    private final ContainerText conSector;
    private final ContainerText conCompany;
    private final ContainerText conCIF;
    private final ContainerText conSedeText;
    private final ContainerDropDawn conSedeDrop;
    private final InputButton btnEdit;
    private final InputButton btnBack;
    private final InputButton btnConfirm;
    private boolean editar;
    private final JPanel main;

    public FramePerfile()
    {
        UserModel user = UserModel.getInstance();

        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        main = new JPanel(new GridBagLayout());
        conUsername = new ContainerText("Usuario", 200, true);
        conRole = new ContainerText("Rol", 200, true);
        conSector = new ContainerText("Sector", 200, true);
        conCompany = new ContainerText("Compañia", 200, true);
        conCIF = new ContainerText("CIF", 200, true);
        conSedeText = new ContainerText("Sede", 200, true);
        conSedeDrop = new ContainerDropDawn("Sede", 200, new String[]{"Madrid", "Barcelona", "Sevilla"});
        btnEdit = new InputButton("Editar perfil", false);
        btnBack = new InputButton("Cancelar", false);
        btnConfirm = new InputButton("Confirmar", true);

        editar = false;

        // Listeners per actualitzar el frame main dinàmicament
        btnEdit.getButton().addActionListener(this);
        btnBack.getButton().addActionListener(this);
        btnConfirm.getButton().addActionListener(this);

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        main.setBackground(Palette.c3);
        main.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));
        add(main, BorderLayout.CENTER);

        updateMainPanel();
    }

    private void updateMainPanel()
    {
        main.removeAll();

        UserModel user = UserModel.getInstance(); // Agafar l'usuari únic

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, Sizes.x1, 0);

        // Título
        gbc.gridy = 1;
        JLabel t1 = new JLabel("TUS DATOS");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
        t1.setForeground(Palette.c7);
        main.add(t1, gbc);

        // Usuario
        gbc.gridy = 2;
        conUsername.setText(user.getUsername());
        conUsername.setEditable(false);
        main.add(conUsername, gbc);

        // Rango
        gbc.gridy = 3;
        conRole.setText(user.getRole());
        conRole.setEditable(false);
        main.add(conRole, gbc);

        // Servicio
        gbc.gridy = 4;
        conSector.setText(user.getSector());
        conSector.setEditable(editar);
        main.add(conSector, gbc);

        // Compañia
        gbc.gridy = 5;
        conCompany.setText(user.getCompany());
        conCompany.setEditable(editar);
        main.add(conCompany, gbc);

        // CIF
        gbc.gridy = 6;
        conCIF.setText(user.getCif());
        conCIF.setEditable(false);
        main.add(conCIF, gbc);

        // Sede
        gbc.gridy = 7;
        conSedeText.setText(user.getSede());
        if (editar)
        {
            conSedeDrop.setSelectedItem(user.getSede());
            main.add(conSedeDrop, gbc);
        }
        else
        {
            conSedeText.setText(UserModel.getInstance().getSede());
            conSedeText.setEditable(editar);
            main.add(conSedeText, gbc);
        }

        // Botones
        gbc.gridy = 8;
        if (editar)
        {
            btnBack.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
            main.add(btnBack, gbc);

            gbc.gridy = 9;
            btnConfirm.setPreferredSize(new Dimension(200, btnConfirm.getPreferredSize().height));
            main.add(btnConfirm, gbc);
        }
        else
        {
            btnEdit.setPreferredSize(new Dimension(200, btnEdit.getPreferredSize().height));
            main.add(btnEdit, gbc);
        }

        main.revalidate();
        main.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnBack.getButton())
        {
            editar = false;
        }
        else if (e.getSource() == btnEdit.getButton())
        {
            editar = true;
        }
        else if (e.getSource() == btnConfirm.getButton())
        {
            editar = false;

            // Obtener valores de los campos
            String username = conUsername.getText();
            String company = conCompany.getText();
            String sector = conSector.getText();
            String sede = conSedeDrop.getDropDawn();

            DatabaseQueries.updatePerfile(username, company, sector, GeneralController.whatSedeInt(sede));
        }

        updateMainPanel();
    }
}
