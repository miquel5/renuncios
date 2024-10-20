package view.components;

import resources.Palette;
import resources.Sizes;
import javax.swing.*;
import java.awt.*;

public class InputText extends JPanel
{
    private JTextField textField;

    public InputText(String placeholder, int columns, boolean isText)
    {
        setLayout(new BorderLayout());

        if (isText == true)
        {
            textField = new JTextField(columns);
        } else
        {
            textField = new JPasswordField(columns);
        }

        textField.setText(placeholder);
        textField.setBackground(Palette.c4);
        textField.setBorder(BorderFactory.createCompoundBorder(Sizes.borderC5, Sizes.padding));
        textField.setForeground(Palette.c6);
        textField.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        add(textField, BorderLayout.CENTER);
    }

    public String getText() {
        return textField.getText();
    }
}
