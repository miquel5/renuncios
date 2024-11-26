package view;

import model.UserModel;
import resources.Palette;
import resources.Sizes;
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
    private final ContainerText conSede;
    private final InputButton btnBack;

    public FramePerfile()
    {
        UserModel user = UserModel.getInstance();

        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        conUsername = new ContainerText("Usuario", 200, true);
        conRole = new ContainerText("Rol", 200, true);
        conSector = new ContainerText("Sector", 200, true);
        conCompany = new ContainerText("Compañia", 200, true);
        conCIF = new ContainerText("CIF", 200, true);
        conSede = new ContainerText("Sede", 200, true);
        btnBack = new InputButton("Atrás", false);


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

        // Perfile
        /*gbc.gridy = 1;
        ImageIcon perfile = new ImageIcon(getClass().getResource("/assets/perfile.png"));
        Image scaledImage = perfile.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        perfile = new ImageIcon(scaledImage);
        JLabel perfileLabel = new JLabel(perfile);
        perfileLabel.setHorizontalAlignment(JLabel.CENTER);
        perfileLabel.setBorder(new EmptyBorder(0, 0, Sizes.x3, 0));
        main.add(perfileLabel, gbc);*/

        // Títol
        gbc.gridy = 1;
        JLabel t1 = new JLabel("TUS DATOS");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
        t1.setForeground(Palette.c7);
        main.add(t1, gbc);

        // Usuario
        gbc.gridy = 2;
        conUsername.setEditable(false);
        conUsername.setText(user.getUsername());
        main.add(conUsername, gbc);

        // Rango
        gbc.gridy = 3;
        conRole.setEditable(false);
        conRole.setText(user.getRole());
        main.add(conRole, gbc);

        // Servicio
        gbc.gridy = 4;
        conSector.setEditable(false);
        conSector.setText(user.getSector());
        main.add(conSector, gbc);

        // Compañia
        gbc.gridy = 5;
        conCompany.setEditable(false);
        conCompany.setText(user.getCompany());
        main.add(conCompany, gbc);

        // CIF
        gbc.gridy = 6;
        conCIF.setEditable(false);
        conCIF.setText(user.getCif());
        main.add(conCIF, gbc);

        // Sede
        /*gbc.gridy = 7;
        conSede.setEditable(false);
        conSede.setText(user.getSede());
        main.add(conSede, gbc);*/

        // Botón atrás
        /*gbc.gridy = 8;
        btnBack.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        btnBack.addActionListener(this);
        main.add(btnBack, gbc);*/

        add(main, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnBack.getButton())
        {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FramePerfile.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameSettings());
            frame.revalidate();
            frame.repaint();
        }
    }
}
