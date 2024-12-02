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
    private final JPanel cardsPanel; // Panel donde se muestran los productos
    private final JScrollPane scrollPane; // Scroll para los productos
    private final JPanel mainPanel; // Panel principal

    public FrameHome()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        conType = new ContainerDropDawn("Tipo de producto", 100, new String[]{"- - -", "Web", "Pancarta", "Flayer"});

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Palette.c8);
        mainPanel.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));
        add(mainPanel, BorderLayout.CENTER);

        // Buscador
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setPreferredSize(new Dimension(0, 75));
        searchPanel.setBackground(Palette.c8);
        searchPanel.add(conType);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

        // Panel de productos
        cardsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Sizes.x2, Sizes.x2));
        cardsPanel.setBackground(Palette.c8);

        // ScrollPane para productos
        scrollPane = new JScrollPane(cardsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Cargar productos iniciales (mostrando todos)
        cargarProductos("- - -");

        // Agregar ActionListener al desplegable
        conType.comboBox.addActionListener(e -> {
            String selectedType = conType.getDropDawn(); // Obtener el tipo seleccionado
            cargarProductos(selectedType); // Actualizar los productos según el tipo
        });
    }

    // Cargar productos según el tipo seleccionado
    private void cargarProductos(String selectedType)
    {
        cardsPanel.removeAll(); // Limpiar el panel de productos

        List<ServiceModel> services = DatabaseQueries.products(); // Obtener todos los servicios

        int type = 2; // Valor predeterminado (Pancarta)
        if ("Web".equals(selectedType))
        {
            type = 1;
        } else if ("Flayer".equals(selectedType))
        {
            type = 3;
        }

        // Filtrar productos según el tipo seleccionado
        for (ServiceModel serviceModel : services)
        {
            if ("- - -".equals(selectedType) || serviceModel.getTipo() == type)
            {
                if (serviceModel.getTipo() == 1 || serviceModel.getTipo() == 2 || serviceModel.getTipo() == 3)
                {
                    JPanel card = createCard(serviceModel);
                    cardsPanel.add(card);
                }
            }
        }

        // Ajustar el tamaño preferido dinámicamente según los productos
        int cardWidth = 220;
        int cardHeight = 150;
        int horizontalSpacing = Sizes.x2;
        int verticalSpacing = Sizes.x2;
        int numCols = 4;

        int numRows = (int) Math.ceil((double) cardsPanel.getComponentCount() / numCols);
        int preferredHeight = (cardHeight + verticalSpacing) * numRows - verticalSpacing;
        int preferredWidth = (cardWidth + horizontalSpacing) * numCols - horizontalSpacing;

        cardsPanel.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }

    // Crear una card
    public JPanel createCard(ServiceModel serviceModel) {
        JPanel panel = new JPanel();
        panel.setBackground(Palette.c9);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(Sizes.borderC5, Sizes.padding));
        panel.setPreferredSize(new Dimension(220, 150)); // Mida predefinida de la card

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.setOpaque(false);

        // Información del producto
        JLabel titleLabel = new JLabel(GeneralController.whatService(serviceModel.getTipo()));
        titleLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        titleLabel.setForeground(Palette.c7);
        infoPanel.add(titleLabel);

        double precio = serviceModel.getPrecio();

        // Lógica per cada tipus de servei
        if (serviceModel.getTipo() == 1) {
            JLabel nameLabel = new JLabel(serviceModel.getWNombre());
            nameLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            nameLabel.setForeground(Palette.c7);
            infoPanel.add(nameLabel);

            JLabel linkLabel = new JLabel(serviceModel.getWEnlace());
            linkLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            linkLabel.setForeground(Palette.c7);
            infoPanel.add(linkLabel);

            JLabel priceLabel = new JLabel(GeneralController.formatPrice(precio) + "€/mes");
            priceLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            priceLabel.setForeground(Palette.c7);
            infoPanel.add(priceLabel);

            JLabel emptyLabel = new JLabel("");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            emptyLabel.setForeground(Palette.c7);
            infoPanel.add(emptyLabel);

        } else if (serviceModel.getTipo() == 3) {
            JLabel cpLabel = new JLabel(String.valueOf(serviceModel.getCp()));
            cpLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            cpLabel.setForeground(Palette.c7);
            infoPanel.add(cpLabel);

            JLabel cityLabel = new JLabel(serviceModel.getFPoblacio());
            cityLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            cityLabel.setForeground(Palette.c7);
            infoPanel.add(cityLabel);

            JLabel provinceLabel = new JLabel(serviceModel.getFProvincia());
            provinceLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            provinceLabel.setForeground(Palette.c7);
            infoPanel.add(provinceLabel);

            JLabel priceLabel = new JLabel(GeneralController.formatPrice(precio) + "€/mes");
            priceLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            priceLabel.setForeground(Palette.c7);
            infoPanel.add(priceLabel);

        } else if (serviceModel.getTipo() == 2)
        {
            JLabel descLabel = new JLabel(serviceModel.getLDescrip());
            descLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            descLabel.setForeground(Palette.c7);
            infoPanel.add(descLabel);

            JLabel coordLabel = new JLabel(serviceModel.getLCordenadas());
            coordLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            coordLabel.setForeground(Palette.c7);
            infoPanel.add(coordLabel);

            JLabel priceLabel = new JLabel(GeneralController.formatPrice(precio) + "€/mes");
            priceLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            priceLabel.setForeground(Palette.c7);
            infoPanel.add(priceLabel);

            JLabel emptyLabel = new JLabel("");
            emptyLabel.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            emptyLabel.setForeground(Palette.c7);
            infoPanel.add(emptyLabel);
        }

        panel.add(infoPanel, BorderLayout.CENTER);

        // Botó per afegir el producte
        InputButton buyButton = new InputButton("Añadir a la cesta", true);
        buyButton.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        buyButton.setForeground(Palette.c7);

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