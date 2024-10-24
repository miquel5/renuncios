package view;

import resources.Palette;
import resources.Sizes;
import view.components.InputButton;
import view.components.PanelSidebar;

import view.components.ContainerDropDawn;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrameHome extends JPanel
{
    private final ContainerDropDawn conType;

    public FrameHome()
    {
        setLayout(new BorderLayout());

        // Elements
        conType = new ContainerDropDawn("Producto", 200, new String[] {"- - -", "Web", "Flayer", "Pancarta"});

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Palette.c3);
        mainPanel.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));
        add(mainPanel, BorderLayout.CENTER);

        // Search
        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(0, 150));
        searchPanel.setBackground(Palette.c3);

        searchPanel.add(conType);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Cards
        JPanel cardsPanel = new JPanel();
        cardsPanel.setLayout(new GridLayout(0, 4, Sizes.x2, Sizes.x2)); // Ajusta el espaciado horizontal y vertical si es necesario
        cardsPanel.setBackground(Palette.c3);
        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        for (int i = 0; i < 5; i++)
        {
            JPanel card = createCard("1", "web", "text", "01/01/0001", "01/01/0001", "mediano", true, 20.0);
            cardsPanel.add(card);
        }
    }

    public JPanel createCard(String id, String type, String text, String datai, String dataf, String size, boolean color, double price)
    {
        // Configuració
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, Sizes.x1, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setOpaque(true);
        setBackground(Color.GRAY);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new Dimension(0, 100)); // 100px d'altura
        panel.setBackground(Palette.c3);

        JPanel list = new JPanel(new GridBagLayout());
        list.setBackground(Palette.c3);

        // 1: Tipo
        if (type != null && !type.isEmpty())
        {
            gbc.gridy = 1;
            JLabel t1 = new JLabel("Tipo: " + type);
            list.add(t1, gbc);
        }

        // 2: Descripción
        if (text != null && !text.isEmpty())
        {
            gbc.gridy = 2;
            JLabel t2 = new JLabel("Descripción: " + text);
            list.add(t2, gbc);
        }

        // 3: Fecha de inicio
        if (datai != null && !datai.isEmpty())
        {
            gbc.gridy = 3;
            JLabel t3 = new JLabel("Fecha inicio: " + datai);
            list.add(t3, gbc);
        }

        // 4: Fecha final
        if (dataf != null && !dataf.isEmpty())
        {
            gbc.gridy = 4;
            JLabel t4 = new JLabel("Fecha final: " + dataf);
            list.add(t4, gbc);
        }

        // 5: Tamaño
        if (size != null && !size.isEmpty())
        {
            gbc.gridy = 5;
            JLabel t5 = new JLabel("Tamaño: " + size);
            list.add(t5, gbc);
        }

        // 6: Color
        gbc.gridy = 6;
        JLabel t6 = new JLabel("Color: " + (color ? "Sí" : "No")); // Sí = true / No = false
        list.add(t6, gbc);

        // 7: Precio
        gbc.gridy = 7;
        JLabel t7 = new JLabel("Precio: " + price + "€");
        list.add(t7, gbc);

        gbc.gridy = 8;
        InputButton buy = new InputButton("Añadir a la cesta", true);
        list.add(buy, gbc);

        panel.add(list);
        return panel;
    }

}