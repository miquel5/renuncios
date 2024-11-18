package view;

import controller.LoginController;
import model.UserModel;
import view.components.InputButton;
import view.components.ContainerText;
import resources.Palette;
import resources.Sizes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;

public class FrameLogin extends JPanel implements ActionListener
{
    private final ContainerText conUsername;
    private final ContainerText conPassword;
    private final InputButton btnLogin;
    private LoginController loginController;

    public FrameLogin()
    {
        loginController = new LoginController();

        // Configurar la pantalla
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Posició x = 0
        gbc.insets = new Insets(0, 0, Sizes.x1, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setBackground(Palette.c3);

        // Elements
        conUsername = new ContainerText("Usuario", 200,true);
        conPassword = new ContainerText("Contraseña", 200, false);
        btnLogin = new InputButton("Entrar", true);

        // Isotip
        gbc.gridy = 1;
        ImageIcon isotypeIcon = new ImageIcon(getClass().getResource("/assets/Isotype.png"));
        Image scaledImage = isotypeIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        isotypeIcon = new ImageIcon(scaledImage);
        JLabel isotypeLabel = new JLabel(isotypeIcon);
        isotypeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(isotypeLabel, gbc);

        // Titol
        gbc.gridy = 2;
        JLabel t1 = new JLabel("BIENVENIDO/A");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
        t1.setForeground(Palette.c7);
        add(t1, gbc);

        // Input username
        gbc.gridy = 3;
        add(conUsername, gbc);

        // Input password
        gbc.gridy = 4;
        add(conPassword, gbc);

        /*gbc.gridy = 4;
        JLabel t2 = new JLabel("¿Has olvidado tu contraseña?");
        t2.setHorizontalAlignment(JLabel.RIGHT);
        t2.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        t2.setForeground(Palette.c6);
        t2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        add(t2, gbc);*/

        // Button Login
        gbc.gridy = 5;
        btnLogin.addActionListener(this);
        add(btnLogin, gbc);

        // ¿No eres miembro? Register
        gbc.gridy = 6;
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel1.setBorder(new EmptyBorder(Sizes.x1, 0, 0, 0));
        panel1.setOpaque(false);

        JLabel t3 = new JLabel("¿No eres miembro? ");
        t3.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        t3.setForeground(Palette.c7);
        panel1.add(t3);

        JLabel t4 = new JLabel("Register");
        t4.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        t4.setForeground(Palette.c2);
        t4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        t4.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameLogin.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameRegister());
                frame.revalidate();
                frame.repaint();
            }
        });

        panel1.add(t4);
        add(panel1, gbc);
    }


    // Accions dels botons
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnLogin.getButton())
        {
            String username = conUsername.getText();
            String password = conPassword.getText();

            UserModel user = loginController.login(username, password);

            if (user != null)
            {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameLogin.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameHome());
                frame.revalidate();
                frame.repaint();
            } else
            {
                JOptionPane.showMessageDialog(this, "Login fallido. Verifique las credenciales.");
            }
        }
    }
}
