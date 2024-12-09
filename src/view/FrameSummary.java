package view;

import controller.CartController;
import controller.GeneralController;
import model.CartModel;
import model.ServiceModel;
import resources.Palette;
import resources.Sizes;
import view.components.InputButton;
import view.components.PanelSidebar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FrameSummary extends JPanel implements ActionListener
{
    private final InputButton btnPay;
    private CartModel cartModel;
    private CartController cartController;

    public FrameSummary()
    {
        // Configuración de la pantalla
        setLayout(new BorderLayout());

        // Elementos
        btnPay = new InputButton("Tramitar pedido", true);

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Palette.c3);
        main.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        cartModel = CartModel.getInstance();
        cartController = new CartController();

        ArrayList<Integer> list = cartModel.getList();

        System.out.println("Help: List of services " + cartModel.getList());
        System.out.println("Help: Total " + cartModel.getTotal());

        // Mirar si hi ha un preu o un producte (El preu pot ser 0.0001)
        if ((cartModel.getTotal() > 0.0001) || !cartModel.getList().isEmpty() || cartModel.getList() == null)
        {
            for (Integer serviceId : list)
            {
                ServiceModel serviceModel = cartController.findService(serviceId); // Buscar el mismo id

                if (serviceModel != null)
                {
                    main.add(createCard(serviceModel, cartModel));
                    main.add(Box.createRigidArea(new Dimension(0, Sizes.x2))); // Espacio
                } else
                {
                    System.out.println("No se ha encontrado el servicio: " + serviceId);
                }
            }

            GridBagConstraints gbcAside = new GridBagConstraints();
            gbcAside.gridx = 0;
            gbcAside.weightx = 1.0;
            gbcAside.fill = GridBagConstraints.HORIZONTAL;
            gbcAside.insets = new Insets(Sizes.x1, 0, 0, 0);

            // Aside
            JPanel aside = new JPanel();
            aside.setLayout(new BorderLayout());
            aside.setPreferredSize(new Dimension(350, 0)); // Ocupa solo 350px de la pantalla
            aside.setBackground(Palette.c4);
            aside.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

            // Aside top
            JPanel asideTopPanel = new JPanel();
            asideTopPanel.setOpaque(false);

            JLabel t2 = new JLabel("RESUMEN");
            t2.setFont(new Font("Arial", Font.BOLD, Sizes.x3));

            asideTopPanel.add(t2);
            aside.add(asideTopPanel, BorderLayout.NORTH);

            // Aside bottom
            JPanel asideBottomPanel = new JPanel();
            asideBottomPanel.setOpaque(false);
            asideBottomPanel.setLayout(new GridBagLayout());

            for (Integer serviceId : list)
            {
                ServiceModel serviceModel = cartController.findService(serviceId); // Buscar mismo id
                asideBottomPanel.add(createSumary(serviceModel.getTipo(), serviceModel.getPrecio()), gbcAside);
            }

            JPanel total = new JPanel(new BorderLayout());
            total.setBorder(new EmptyBorder(Sizes.x1, 0, Sizes.x1, 0));
            total.setOpaque(false);

            if (!cartModel.getList().isEmpty())
            {
                // label total
                JLabel totalLeft = new JLabel("Total");
                totalLeft.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

                JLabel totalRight = new JLabel(GeneralController.formatPrice(cartModel.getTotal()) + "€"); // Formatear a 2 decimales
                totalRight.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

                System.out.println("Help: Total " + cartModel.getTotal());

                total.add(totalLeft, BorderLayout.WEST);
                total.add(totalRight, BorderLayout.EAST);

                asideBottomPanel.add(total, gbcAside);
                btnPay.addActionListener(this);

                asideBottomPanel.add(btnPay, gbcAside);
            }

            aside.add(asideBottomPanel, BorderLayout.SOUTH);
            add(aside, BorderLayout.EAST);
        } else
        {
            // Label
            JLabel t1 = new JLabel("TU CARRITO ESTÁ VACÍO");
            t1.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
            t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
            t1.setForeground(Palette.c7);
            main.add(t1);

            // Label
            JLabel t2 = new JLabel("Cuando hayas añadido algo al carrito, aparecerá aquí.");
            t2.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            t2.setForeground(Palette.c7);
            main.add(t2);
        }

        // Crear JScrollPane
        JScrollPane mainScrollPane = new JScrollPane(main);
        mainScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainScrollPane.setBorder(null);

        add(mainScrollPane, BorderLayout.CENTER);
    }

    public JPanel createCard(ServiceModel serviceModel, CartModel cartModel) {
        // Calcular número de filas según el tipo de servicio
        int numberOfRows = 0;

        if (serviceModel.getTipo() == 1) {
            numberOfRows = 8;
        } else if (serviceModel.getTipo() == 2) {
            numberOfRows = 7;
        } else if (serviceModel.getTipo() == 3) {
            numberOfRows = 7;
        }

        // Altura dinámica
        int rowHeight = 20;
        int panelHeight = rowHeight * numberOfRows;

        // Panel principal
        JPanel panel = new JPanel();
        panel.setBackground(Palette.c3);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(0, panelHeight));

        // Panel izquierdo (imagen con acción para eliminar)
        JPanel panelLeft = new JPanel();
        panelLeft.setOpaque(false);
        panelLeft.setPreferredSize(new Dimension(50, panelHeight));

        // Icono cancelar
        String routeCancel = "/assets/icons/close.png";
        ImageIcon iconCa = new ImageIcon(getClass().getResource(routeCancel));
        Image scaledImageCa = iconCa.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        JLabel iconCancel = new JLabel(new ImageIcon(scaledImageCa));
        iconCancel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Icono configuración
        String routeConfiguration = "/assets/icons/configuration.png";
        ImageIcon iconCo = new ImageIcon(getClass().getResource(routeConfiguration));
        Image scaledImageCo = iconCo.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        JLabel iconConfiguration = new JLabel(new ImageIcon(scaledImageCo));
        iconConfiguration.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Panel derecho (información del servicio)
        JPanel panelRight = new JPanel(new GridLayout(0, 1));
        panelRight.setOpaque(false);
        panelRight.add(new JLabel("Tipo: " + GeneralController.whatService(serviceModel.getTipo())));

        // Lógica para cada tipo de servicio
        if (serviceModel.getTipo() == 1)
        {
            panelRight.add(new JLabel("Nombre: " + serviceModel.getWNombre()));
            panelRight.add(new JLabel("Url: " + serviceModel.getWEnlace()));
            panelRight.add(new JLabel("Tamaño: " + GeneralController.whatSize(serviceModel.getMida())));
            panelRight.add(new JLabel("Pago: " + GeneralController.whatPayment(serviceModel.getMes())));
            panelRight.add(new JLabel("Fecha inicio: " + serviceModel.getDataI()));
            panelRight.add(new JLabel("Fecha final: " + serviceModel.getDataF()));
            panelRight.add(new JLabel("Precio: " + GeneralController.formatPrice(serviceModel.getPrecio()) + "€/mes"));
        } else if (serviceModel.getTipo() == 2)
        {
            panelRight.add(new JLabel("Descripción: " + serviceModel.getLDescrip()));
            panelRight.add(new JLabel("Coordenadas: " + serviceModel.getLCordenadas()));
            panelRight.add(new JLabel("Pago: " + GeneralController.whatPayment(serviceModel.getMes())));
            panelRight.add(new JLabel("Fecha inicio: " + serviceModel.getDataI()));
            panelRight.add(new JLabel("Fecha final: " + serviceModel.getDataF()));
            panelRight.add(new JLabel("Precio: " + GeneralController.formatPrice(serviceModel.getPrecio()) + "€/mes"));
        } else if (serviceModel.getTipo() == 3)
        {
            panelRight.add(new JLabel("Codigo postal: " + serviceModel.getCp()));
            panelRight.add(new JLabel("Población: " + serviceModel.getFPoblacio()));
            panelRight.add(new JLabel("Provincia: " + serviceModel.getFProvincia()));
            panelRight.add(new JLabel("Color: " + GeneralController.withColor(serviceModel.getColor())));
            panelRight.add(new JLabel("Pago: " + GeneralController.whatPayment(serviceModel.getMes())));
            panelRight.add(new JLabel("Precio: " + GeneralController.formatPrice(serviceModel.getPrecio()) + "€/mes"));
        }

        System.out.println("Help: Price card " + serviceModel.getPrecio());

        // Al presionar cancelar
        iconCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cartModel.subtractTotal(serviceModel.getPrecio()); // Restar el precio del total
                cartModel.getList().remove(Integer.valueOf(serviceModel.getUniqueId())); // Eliminar servicio

                // Actualizar toda la pantalla
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameSummary.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameSummary()); // Recargar la pantalla
                frame.revalidate();
                frame.repaint();
            }
        });

        // Al presionar configuración
        iconConfiguration.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameSummary.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameEditService(serviceModel.getUniqueId())); // Mostrar la pantalla de edición del servicio
                frame.revalidate();
                frame.repaint();
            }
        });

        panelLeft.add(iconCancel);
        panelLeft.add(iconConfiguration);

        // Añadir paneles izquierdo y derecho al panel principal
        panel.add(panelLeft, BorderLayout.WEST);
        panel.add(panelRight, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, panelHeight)); // Ajustar el tamaño

        return panel;
    }

    // Crear una línea del resumen
    public JPanel createSumary(int left, double right)
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel labelLeft = new JLabel(GeneralController.whatService(left));
        labelLeft.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        labelLeft.setForeground(Palette.c6);

        JLabel labelRight = new JLabel(GeneralController.formatPrice(right) + "€"); // Formatejar a 2 decimals
        labelRight.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        labelRight.setForeground(Palette.c6);

        panel.add(labelLeft, BorderLayout.WEST);
        panel.add(labelRight, BorderLayout.EAST);

        return panel;
    }

    // Acciones de los botones
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnPay.getButton())
        {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameSummary.this);
            frame.getContentPane().removeAll();
            frame.add(new FramePayment());
            frame.revalidate();
            frame.repaint();
        }
    }
}
