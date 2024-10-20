package view;

import controller.LoginController;
import controller.RegisterController;
import model.UserModel;
import resources.Palette;
import resources.Sizes;
import view.components.InputButton;
import view.components.InputText;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameRegister extends JPanel implements ActionListener
{
    private final JLabel t1;
    private final InputText inpCompany;
    private final InputText inpSector;
    private final InputText inpUsername;
    private final InputText inpPassword;
    private final InputText inpRepeatPassword;
    private final InputButton btnRegister;
    private final JLabel t2;
    private final JLabel t3;
    private registerController registerController;

    public FrameRegister()
    {
        // Configurar la pantalla
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Posició x = 0
        gbc.insets = new Insets(0, 0, Sizes.x1, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setBackground(Palette.c3);

        // Elements
        t1 = new JLabel("BECOME A MEMBER");
        inpUsername = new InputText("Username",20,true);
        inpCompany = new InputText("Company",20,true);
        inpSector = new InputText("Sector",20,true);
        inpPassword = new InputText("Password",20,false);
        inpRepeatPassword = new InputText("Password",20,false);
        btnRegister = new InputButton("Register", true);
        t2 = new JLabel("You are a member? ");
        t3 = new JLabel("Login");

        // BECOME A MEMBER
        gbc.gridy = 1;
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
        t1.setForeground(Palette.c7);
        add(t1, gbc);

        // Input username
        gbc.gridy = 2;
        add(inpUsername, gbc);

        // Input company
        gbc.gridy = 3;
        add(inpCompany, gbc);

        // Input sector
        gbc.gridy = 4;
        add(inpSector, gbc);

        // Input password
        gbc.gridy = 5;
        add(inpPassword, gbc);

        // Input repeat password
        gbc.gridy = 6;
        add(inpRepeatPassword, gbc);

        // Button Register
        gbc.gridy = 7;
        btnRegister.addActionListener(this);
        add(btnRegister, gbc);

        // Not a member? Register
        gbc.gridy = 8;
        JPanel panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel1.setBorder(new EmptyBorder(Sizes.x1, 0, 0, 0));
        panel1.setOpaque(false);

        t2.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        t2.setForeground(Palette.c7);
        panel1.add(t2);

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

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnRegister.getButton())
        {
            String username = inpUsername.getText();
            String password = inpPassword.getText();

            UserModel user = RegisterController.register(username, password);

            if (user != null)
            {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameRegister.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameHome());
                frame.revalidate();
                frame.repaint();
            } else
            {
                JOptionPane.showMessageDialog(this, "Register fallido. Verifique las credenciales.");
            }
        }
    }

}
