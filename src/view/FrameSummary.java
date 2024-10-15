package view;

import resources.Palette;
import resources.Sizes;
import view.components.InputButton;
import view.components.PanelSidebar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;


public class FrameSummary extends JPanel
{
    private final InputButton btnFiles;
    private final InputButton btnBuy;

    public FrameSummary()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        btnFiles = new InputButton("Files", false);
        btnBuy = new InputButton("Buy", true);

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel main = new JPanel();
        main.setLayout(new GridLayout());
        main.setBackground(Palette.c3);
        main.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        JLabel t2 = new JLabel("Sumary");
        main.add(t2);

        add(main, BorderLayout.CENTER);

        // Aside
        JPanel aside = new JPanel();
        aside.setLayout(new BorderLayout());
        aside.setPreferredSize(new Dimension(300, 0)); // Ocupa 300px a la pantalla
        aside.setBackground(Palette.c4);
        aside.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        // Aside top panel
        JPanel asideTopPanel = new JPanel();
        asideTopPanel.setOpaque(false);

        JLabel t1 = new JLabel("SUMMARY");
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));

        //seeSumary();

        asideTopPanel.add(t1);
        aside.add(asideTopPanel, BorderLayout.NORTH);

        // Aside bottom panel
        JPanel asideBottomPanel = new JPanel();
        asideBottomPanel.setOpaque(false);
        asideBottomPanel.setLayout(new GridBagLayout());

        // Configurar
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(Sizes.x1, 0, 0, 0);

        //countSumary();

        gbc.gridy = 0;
        asideBottomPanel.add(btnFiles, gbc);

        gbc.gridy = 1;
        asideBottomPanel.add(btnBuy, gbc);

        aside.add(asideBottomPanel, BorderLayout.SOUTH);
        add(aside, BorderLayout.EAST);
    }

    public static void seeSumary()
    {

    }

    public static void countSumary()
    {

    }

}
