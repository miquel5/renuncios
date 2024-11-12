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
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FrameSummary extends JPanel implements ActionListener
{
    private final InputButton btnPay;

    public FrameSummary()
    {
        DecimalFormat df = new DecimalFormat("#.00");

        // Configuració de la pantalla
        setLayout(new BorderLayout());

        // Elements
        btnPay = new InputButton("Tramitar pedido", true);


        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Palette.c3);
        main.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        CartModel cartModel = CartModel.getInstance();
        ArrayList<Integer> list = cartModel.getList();
        CartController cartController = new CartController();

        if (cartModel.getTotal() > 0 || cartModel.getList() == null)
        {
            for (int i = 0; i < list.size(); i++)
            {
                ServiceModel service = cartController.findService(list.get(i)); // Buscar el mateix servei que el numS

                if (service != null)
                {
                    main.add(createCard(service, cartModel));
                    main.add(Box.createRigidArea(new Dimension(0, Sizes.x2))); // Espai
                } else
                {
                    System.out.println("No se ha encontrado el servicio: " + list.get(i));
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
            aside.setPreferredSize(new Dimension(300, 0)); // Ocupa només 300px de la pantalla
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

            for (int i = 0; i < list.size(); i++)
            {
                ServiceModel service = cartController.findService(list.get(i));
                asideBottomPanel.add(createSumary(service.getTypee(), String.valueOf(service.getPrice())), gbcAside);
            }

            JPanel total = new JPanel(new BorderLayout());
            total.setBorder(new EmptyBorder(Sizes.x1, 0, Sizes.x1, 0));
            total.setOpaque(false);

            if (cartModel.getTotal() != 0)
            {
                // label total
                JLabel totalLeft = new JLabel("Total");
                totalLeft.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

                JLabel totalRight = new JLabel(df.format(cartModel.getTotal()) + "€"); // Formatejar a 2 dijits
                totalRight.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

                total.add(totalLeft, BorderLayout.WEST);
                total.add(totalRight, BorderLayout.EAST);

                asideBottomPanel.add(total, gbcAside);
                btnPay.addActionListener(this);

                asideBottomPanel.add(btnPay, gbcAside);
            }

            // TODO: Afegir un límit de 6 objectes o una barra per baixar

            aside.add(asideBottomPanel, BorderLayout.SOUTH);
            add(aside, BorderLayout.EAST);
        } else
        {
            // Label
            JLabel t1 = new JLabel("TU CARRITO ESTÁ VACIO");
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

        add(main, BorderLayout.CENTER);
    }

    public JPanel createCard(ServiceModel service, CartModel cartModel)
    {
        JPanel panel = new JPanel();
        panel.setBackground(Palette.c3);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(0, 100));

        // Panel derecha (información del servicio)
        JPanel panelRight = new JPanel(new GridLayout(0, 1));
        panelRight.setOpaque(false);
        panelRight.add(new JLabel("Texto: " + service.getTxt()));
        panelRight.add(new JLabel("Tipo: " + GeneralController.whatService(service.getTypee())));
        panelRight.add(new JLabel("Fecha inicio: " + service.getDataI().toString()));
        panelRight.add(new JLabel("Fecha fin: " + service.getDataF().toString()));
        panelRight.add(new JLabel("Precio total: " + service.getPrice() + "€"));

        // TODO: Afegir lógica, depenent de cada tipus apareix una cosa o altra

        // Panel izquierda (imagen con acción para eliminar)
        JPanel panelLeft = new JPanel();
        panelLeft.setOpaque(false);
        panelLeft.setPreferredSize(new Dimension(50, 100));

        // Cargar y escalar el icono para el JLabel
        String iconPath = "/assets/icons/close.png";
        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image scaledImage = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
        iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Al presionar la icona
        iconLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                cartModel.subtractTotal(service.getPrice()); // Restar el preu del producte del total
                cartModel.getList().remove(Integer.valueOf(service.getNumS())); // Treure el numS a la llista

                // Actualitzar tota la pantalla
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameSummary.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameSummary());
                frame.revalidate();
                frame.repaint();
            }
        });

        panelLeft.add(iconLabel);

        // Añadir paneles izquierdo y derecho al panel principal
        panel.add(panelLeft, BorderLayout.WEST);
        panel.add(panelRight, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 100)); // Ocupa solo 100px de alto

        return panel;
    }

    // Crear una línea del tíquet de sumary
    public JPanel createSumary(int left, String right)
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel labelLeft = new JLabel(GeneralController.whatService(left)); // Canviar de int a string
        labelLeft.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        labelLeft.setForeground(Palette.c6);

        JLabel labelRight = new JLabel(right + "€");
        labelRight.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        labelRight.setForeground(Palette.c6);

        panel.add(labelLeft, BorderLayout.WEST);
        panel.add(labelRight, BorderLayout.EAST);

        return panel;
    }

    // Accións dels botons
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
