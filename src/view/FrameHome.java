package view;

import resources.Palette;
import resources.Sizes;
import view.components.PanelSidebar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameHome extends JPanel
{
    public FrameHome()
    {
        setLayout(new BorderLayout());

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(1, 3));
        main.setBackground(Palette.c3);
        main.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        // Adding custom elements in transparent containers
        main.add(createContainer("flayer"));
        main.add(createContainer("web"));
        main.add(createContainer("banner"));

        add(main, BorderLayout.CENTER);
    }

    private JPanel createContainer(String type)
    {
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());
        container.setOpaque(false);

        JLabel label = new JLabel(type);
        label.setOpaque(true);
        label.setBackground(Palette.c8);
        label.setPreferredSize(new Dimension(200, 200));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameHome.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameCustom(type));
                frame.revalidate();
                frame.repaint();
            }
        });

        container.add(label);
        return container;
    }
}
