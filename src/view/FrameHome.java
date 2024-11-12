package view;

import controller.GeneralController;
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
    private final ContainerDropDawn conType;

    public FrameHome()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        conType = new ContainerDropDawn("Tipo de producto", 200, new String[]{"- - -", "Web", "Flayer", "Pancarta"});


        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Palette.c3);
        mainPanel.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));
        add(mainPanel, BorderLayout.CENTER);

        // Buscador
        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(0, 75));
        searchPanel.setBackground(Palette.c3);
        searchPanel.add(conType);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Llistar tots els productes disponibles
        JPanel cardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Sizes.x2, Sizes.x2));
        cardsPanel.setBackground(Palette.c3);
        cardsPanel.setPreferredSize(new Dimension(4 * 200 + 3 * Sizes.x2, 0)); // Calcular espai per cada card

        List<ServiceModel> services = DatabaseQueries.products(); // Carregar tots els productes

        if (services.size() == 0)
        {
            JLabel t1 = new JLabel("Lo sentimos, ahora mismo no hay productos.");
            t1.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            t1.setForeground(Palette.c6);
            cardsPanel.add(t1);

            mainPanel.add(cardsPanel, BorderLayout.CENTER);
        } else
        {
            for (ServiceModel service : services)
            {
                // Generar una card
                JPanel card = createCard(service.getNumS(), service.getTypee(), service.getTxt(), service.getDataI().toString(), service.getDataF().toString(), service.getSizee(), service.getColor(), service.getPrice());
                cardsPanel.add(card);
            }

            // TODO: No funciona el scroll
            JScrollPane scrollPane = new JScrollPane(cardsPanel);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);
            mainPanel.add(scrollPane, BorderLayout.CENTER);
        }
    }

    // Crear una card
    public JPanel createCard(int numS, int type, String text, String datai, String dataf, int size, int color, double price)
    {
        JPanel panel = new JPanel();
        panel.setBackground(Palette.c3);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(220, 200)); // Mida predefinida de la card

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.setOpaque(false);

        // Información del producto
        infoPanel.add(new JLabel("Tipo: " + GeneralController.whatService(type))); // Canviar de int a string
        infoPanel.add(new JLabel("Texto: " + text));
        infoPanel.add(new JLabel("Fecha inicio: " + datai));
        infoPanel.add(new JLabel("Fecha fin: " + dataf));
        infoPanel.add(new JLabel("Precio: " + price + " €"));

        // Lógica per cada tipus de producte
        if (type == 1 || type == 2)
        {
            infoPanel.add(new JLabel("Tamaño: " + size));
        } else if (type == 3)
        {
            infoPanel.add(new JLabel("Color: " + color));
        }

        panel.add(infoPanel, BorderLayout.CENTER);

        // Botó per afegir el producte
        InputButton buyButton = new InputButton("Añadir a la cesta", true);

        buyButton.addActionListener(e -> {
            CartModel cartModel = CartModel.getInstance();
            cartModel.addToList(numS); // Añadir el número de producto a la lista
            cartModel.addTotal(price); // Sumar el precio al total
        });

        panel.add(buyButton, BorderLayout.SOUTH);

        return panel;
    }
}
