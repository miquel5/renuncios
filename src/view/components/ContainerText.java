package view.components;

import resources.Palette;
import resources.Sizes;
import javax.swing.*;
import java.awt.*;

public class ContainerText extends JPanel
{
    private JTextField textField;

    public ContainerText(String placeholder, int width, boolean isText)
    {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 3, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setBackground(Palette.c3);

        // Label
        gbc.gridy = 0;
        JLabel label = new JLabel(placeholder);
        label.setForeground(Palette.c6);
        label.setOpaque(false);
        label.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

        add(label, gbc);

        // TextField o PasswordField
        gbc.gridy = 1;
        if (isText)
        {
            textField = new JTextField();
        } else
        {
            textField = new JPasswordField();
        }

        textField.setBackground(Palette.c4);
        textField.setBorder(BorderFactory.createCompoundBorder(Sizes.borderC5, Sizes.padding));
        textField.setForeground(Palette.c6);
        textField.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

        // Aquí controlamos el ancho del JTextField con setPreferredSize
        textField.setPreferredSize(new Dimension(width, textField.getPreferredSize().height));
        add(textField, gbc);

        // Establecemos el tamaño del JPanel para que sea flexible en altura pero con el ancho específico
        setPreferredSize(new Dimension(width, getPreferredSize().height));
    }

    public String getText() {
        return textField.getText();
    }
}
