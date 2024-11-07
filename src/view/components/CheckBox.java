package view.components;

import resources.Palette;
import resources.Sizes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckBox extends JPanel
{
    private JCheckBox checkBox;

    public CheckBox(String placeholder, int width)
    {
        checkBox = new JCheckBox(placeholder);
        checkBox.setPreferredSize(new java.awt.Dimension(width, 20));
        checkBox.setBackground(Palette.c3);
        checkBox.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        this.setBackground(Palette.c3);
        this.add(checkBox);

        /*checkBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (checkBox.isSelected())
                {
                    System.out.println("si");
                } else
                {
                    System.out.println("no");
                }
            }
        });*/
    }

    // Saber l'estat del checkbox
    public boolean isSelected()
    {
        return checkBox.isSelected();
    }
}
