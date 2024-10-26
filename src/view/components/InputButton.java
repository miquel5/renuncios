package view.components;

import resources.Palette;
import resources.Sizes;
import view.FrameHome;
import view.FrameLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InputButton extends JPanel
{
    private final JButton button;

    public InputButton(String placeholder, boolean opaque)
    {
        setLayout(new BorderLayout());
        button = new JButton(placeholder);

        if (opaque)
        {
            button.setBackground(Palette.c1);
            button.setForeground(Palette.c3);
        } else
        {
            button.setBackground(Palette.c3);
            button.setForeground(Palette.c1);
        }

        button.setBorder(BorderFactory.createCompoundBorder(Sizes.borderC1, Sizes.padding));
        button.setFocusPainted(false); // Desactivar enfocament
        button.setOpaque(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        add(button, BorderLayout.CENTER);
    }

    public void addActionListener(ActionListener listener) {button.addActionListener(listener);}

    public JButton getButton() {return button;} // Utilitzar diferents vegades
}
