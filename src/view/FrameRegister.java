package view;

import controller.RegisterController;
import model.UserModel;
import resources.Palette;
import resources.Sizes;
import view.components.ContainerDropDawn;
import view.components.InputButton;
import view.components.ContainerText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameRegister extends JPanel implements ActionListener
{
    private final ContainerText conCompany;
    private final ContainerText conSector;
    private final ContainerText conUsername;
    private final ContainerText conCIF;
    private final ContainerText conPassword;
    private final ContainerText conRepeatPassword;
    private final InputButton btnRegister;
    private final ContainerDropDawn conSede;
    private RegisterController registerController;

    public FrameRegister()
    {
        registerController = new RegisterController();

        // Configurar la pantalla
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Posició x = 0
        gbc.insets = new Insets(0, 0, Sizes.x1, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setBackground(Palette.c3);

        // Elements
        conUsername = new ContainerText("Usuario",200,true);
        conCompany = new ContainerText("Empresa",200,true);
        conSector = new ContainerText("Sector",200,true);
        conCIF = new ContainerText("CIF/NIF",200,true);
        conSede = new ContainerDropDawn("Sede", 200, new String[]{"- - -", "Madrid", "París", "Los Angeles"});
        conPassword = new ContainerText("Contraseña",200,false);
        conRepeatPassword = new ContainerText("Repetir contraseña",200,false);
        btnRegister = new InputButton("Registrarse", true);

        // Isotip
        /*gbc.gridy = 1;
        ImageIcon isotypeIcon = new ImageIcon(getClass().getResource("/assets/Isotype.png"));
        Image scaledImage = isotypeIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        isotypeIcon = new ImageIcon(scaledImage);
        JLabel isotypeLabel = new JLabel(isotypeIcon);
        isotypeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(isotypeLabel, gbc);*/

        // Títol
        gbc.gridy = 2;
        JLabel t1 = new JLabel("HAZTE MIEMBRO");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
        t1.setForeground(Palette.c7);
        add(t1, gbc);

        // Input username
        gbc.gridy = 3;
        add(conUsername, gbc);

        // Input company
        gbc.gridy = 4;
        add(conCompany, gbc);

        // Input sector
        gbc.gridy = 5;
        add(conSector, gbc);

        // Input CIF
        gbc.gridy = 6;
        add(conCIF, gbc); // TODO: Fer que tingui uns requisits?

        // Input sede
        gbc.gridy = 7;
        add(conSede, gbc);

        // Input password
        gbc.gridy = 8;
        add(conPassword, gbc);

        // Input repeat password
        gbc.gridy = 9;
        add(conRepeatPassword, gbc);

        // Button Register
        gbc.gridy = 10;
        btnRegister.addActionListener(this);
        add(btnRegister, gbc);

        // ¿Eres miembro? Login
        gbc.gridy = 11;
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel1.setBorder(new EmptyBorder(Sizes.x1, 0, 0, 0));
        panel1.setOpaque(false);

        JLabel t2 = new JLabel("¿Eres miembro? ");
        t2.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        t2.setForeground(Palette.c7);
        panel1.add(t2);

        JLabel t3 = new JLabel("Login");
        t3.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        t3.setForeground(Palette.c2);
        t3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        t3.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameRegister.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameLogin());
            frame.revalidate();
            frame.repaint();
            }
        });

        panel1.add(t3);
        add(panel1, gbc);
    }

    // Accións dels botons
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnRegister.getButton())
        {
            String username = conUsername.getText();
            String company = conCompany.getText();
            String sector = conSector.getText();
            String cif = conCIF.getText();
            String password = conPassword.getText();
            String repeatPassword = conRepeatPassword.getText();

            UserModel user = registerController.register(username, company, sector, cif, password, repeatPassword);

            if (user != null)
            {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameRegister.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameLogin());
                frame.revalidate();
                frame.repaint();
            } else
            {
                JOptionPane.showMessageDialog(this, "Register fallido. Verifique las credenciales.");
            }
        }
    }

}
