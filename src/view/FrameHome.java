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
            for (ServiceModel serviceModel : services)
            {
                // Verificar si és un tipo servei válid i generar una card
                if (serviceModel.getTipo() == 1 || serviceModel.getTipo() == 2 || serviceModel.getTipo() == 3)
                {
                    JPanel card = createCard(serviceModel);
                    cardsPanel.add(card);
                }
            }

            // Ajustar el tamaño preferido del cardsPanel dinámicamente
            int cardWidth = 220; // Ancho de una card
            int cardHeight = 150; // Altura de una card
            int horizontalSpacing = Sizes.x2; // Espaciado horizontal entre las cards
            int verticalSpacing = Sizes.x2; // Espaciado vertical entre las cards
            int numCols = 4; // Número de columnas visibles en el layout

            // Calcular el tamaño preferido dinámicamente en función del número de servicios
            int numRows = (int) Math.ceil((double) services.size() / numCols);
            int preferredHeight = (cardHeight + verticalSpacing) * numRows - verticalSpacing;
            int preferredWidth = (cardWidth + horizontalSpacing) * numCols - horizontalSpacing;

            cardsPanel.setPreferredSize(new Dimension(preferredWidth, preferredHeight));

            JScrollPane scrollPane = new JScrollPane(cardsPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(null);
            scrollPane.getVerticalScrollBar().setUnitIncrement(16);

            mainPanel.add(scrollPane, BorderLayout.CENTER);
        }
    }

    // Crear una card
    public JPanel createCard(ServiceModel serviceModel)
    {
        JPanel panel = new JPanel();
        panel.setBackground(Palette.c9);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(Sizes.borderC5, Sizes.padding));
        panel.setPreferredSize(new Dimension(220, 150)); // Mida predefinida de la card

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.setOpaque(false);

        // Información del producto
        infoPanel.add(new JLabel("" + GeneralController.whatService(serviceModel.getTipo())));

        double precio = 0;

        // Lógica per cada tipus de servei
        if (serviceModel.getTipo() == 1)
        {
            precio = serviceModel.getWPreum();

            infoPanel.add(new JLabel("" + serviceModel.getWNombre()));
            infoPanel.add(new JLabel("" + serviceModel.getWEnlace()));
            infoPanel.add(new JLabel("" + precio + "€/mes"));
            infoPanel.add(new JLabel(""));
        } else if (serviceModel.getTipo() == 3)
        {
            precio = serviceModel.getFPreu();

            infoPanel.add(new JLabel("" + serviceModel.getCp()));
            infoPanel.add(new JLabel("" + serviceModel.getFPoblacio()));
            infoPanel.add(new JLabel("" + serviceModel.getFProvincia()));
            infoPanel.add(new JLabel("" + precio + "€/mes"));
        }
        if (serviceModel.getTipo() == 2)
        {
            precio = serviceModel.getLPreu();

            infoPanel.add(new JLabel("" + serviceModel.getLDescrip()));
            infoPanel.add(new JLabel("" + serviceModel.getLCordenadas()));
            infoPanel.add(new JLabel("" + precio + "€/mes"));
            infoPanel.add(new JLabel(""));
        }

        panel.add(infoPanel, BorderLayout.CENTER);

        // Botó per afegir el producte
        InputButton buyButton = new InputButton("Añadir a la cesta", true);

        double sendPrecio = precio;
        buyButton.addActionListener(e -> {
            CartModel cartModel = CartModel.getInstance();
            cartModel.addToList(serviceModel.getUniqueId()); // Afegir el número de producte a la lista
            cartModel.sumTotal(sendPrecio); // Sumar al preu total
        });

        panel.add(buyButton, BorderLayout.SOUTH);

        return panel;
    }
}
