package view;

import resources.Palette;
import resources.Sizes;
import view.components.PanelSidebar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrameHome extends JPanel
{
    public FrameHome()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements


        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel main = new JPanel();
        main.setLayout(new GridLayout());
        main.setBackground(Palette.c3);
        main.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        

        JLabel t1 = new JLabel("Home");
        main.add(t1);

        add(main, BorderLayout.CENTER);
    }
}
