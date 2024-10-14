package view;

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
    private final InputText firstName;
    private final InputText lastNames;
    private final InputText email;
    private final InputText username;
    private final InputText password;
    private final InputText repeatPassword;
    private final InputButton btnRegister;
    private final JLabel t2;
    private final JLabel t3;

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
        username = new InputText("Username",20,true);
        firstName = new InputText("First name",20,true);
        lastNames = new InputText("Last names",20,true);
        email = new InputText("email",20,true);
        password = new InputText("Password",20,false);
        repeatPassword = new InputText("Password",20,false);
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
        add(username, gbc);

        // Input first name
        gbc.gridy = 3;
        add(firstName, gbc);

        // Input last names
        gbc.gridy = 4;
        add(lastNames, gbc);

        // Input email
        gbc.gridy = 5;
        add(email, gbc);

        // Input password
        gbc.gridy = 6;
        add(password, gbc);

        // Input repeat password
        gbc.gridy = 7;
        add(repeatPassword, gbc);

        // Button Register
        gbc.gridy = 8;
        btnRegister.addActionListener(this);
        add(btnRegister, gbc);

        // Not a member? Register
        gbc.gridy = 9;
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
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameRegister.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameLogin());
            frame.revalidate();
            frame.repaint();
        }
    }
}
