package view;

import model.CartModel;
import model.ServiceModel;
import resources.Palette;
import resources.Sizes;
import service.DatabaseQueries;
import view.components.InputButton;
import view.components.PanelSidebar;
import view.components.ContainerDropDawn;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class FrameHome extends JPanel
{
    public FrameHome()
    {
        setLayout(new BorderLayout());

        // Elements
        ContainerDropDawn conType = new ContainerDropDawn("Tipo de producto", 200, new String[]{"- - -", "Web", "Flayer", "Pancarta"});

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Palette.c3);
        mainPanel.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));
        add(mainPanel, BorderLayout.CENTER);

        // Main - search
        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(0, 150));
        searchPanel.setBackground(Palette.c3);
        searchPanel.add(conType);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // TODO: Modificar la consulta per al filtre

        // Main - cards
        JPanel cardsPanel = new JPanel(new GridBagLayout());
        cardsPanel.setBackground(Palette.c3);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(Sizes.x2, Sizes.x2, Sizes.x2, Sizes.x2);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        List<ServiceModel> services = DatabaseQueries.products();

        if (services.size() == 0)
        {
            JLabel t1 = new JLabel("Lo sentimos, ahora mismo no hay productos.");
            t1.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            t1.setForeground(Palette.c6);
            cardsPanel.add(t1, gbc);

            mainPanel.add(cardsPanel,BorderLayout.CENTER);
        } else
        {
            for (int i = 0; i < services.size(); i++)
            {
                ServiceModel service = services.get(i);
                JPanel card = createCard(service.getNumS(), service.getTypee(), service.getTxt(), service.getDataI().toString(), service.getDataF().toString(), service.getSizee(), service.getColor(), service.getPrice());
                gbc.gridx = i % 4;
                cardsPanel.add(card, gbc);
            }

            JScrollPane scrollPane = new JScrollPane(cardsPanel);
            scrollPane.setBorder(null);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
        }
    }

    public JPanel createCard(int numS, int type, String text, String datai, String dataf, int size, int color, double price)
    {
        JPanel panel = new JPanel();
        panel.setBackground(Palette.c3);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(200, 180));

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.setOpaque(false);

        // Controlar informació
        infoPanel.add(new JLabel("Tipo: " + type));
        infoPanel.add(new JLabel("Texto: " + text));
        infoPanel.add(new JLabel("Fecha inicio: " + datai));
        infoPanel.add(new JLabel("Fecha fin: " + dataf));
        infoPanel.add(new JLabel("Precio: " + price + " €"));

        if (type == 1 || type == 2)
        {
            infoPanel.add(new JLabel("Tamaño: " + size));
        } else if (type == 3)
        {
            infoPanel.add(new JLabel("Color: " + color));
        }

        panel.add(infoPanel, BorderLayout.CENTER);

        // TODO: Afegir controlador per només és pugui afegir una vegada per cada numS

        InputButton buyButton = new InputButton("Añadir a la cesta", true);

        buyButton.addActionListener(e -> {
            CartModel cartModel = CartModel.getInstance();
            cartModel.addToList(numS); // Afegir el numero de producte a la llista
            cartModel.addTotal(price); // Sumar el preu al total
        });

        panel.add(buyButton, BorderLayout.SOUTH);

        panel.setMaximumSize(new Dimension(200, 180));

        return panel;
    }
}
