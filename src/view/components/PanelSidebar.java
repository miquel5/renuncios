package view.components;

import resources.Palette;
import resources.Sizes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PanelSidebar
{
    private final JPanel navbar;

    public PanelSidebar()
    {
        navbar = new JPanel();

        navbar.setPreferredSize(new Dimension(50, 0));
        navbar.setBackground(Palette.c3);
        navbar.setBorder(new EmptyBorder(Sizes.x1, Sizes.x3, Sizes.x1, Sizes.x3));
    }

    public JPanel getPanel()
    {
        return navbar;
    } // Utilitzar diferents vegades
}
