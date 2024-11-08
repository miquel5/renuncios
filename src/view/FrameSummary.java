package view;

import controller.CartController;
import model.CartModel;
import model.ServiceModel;
import resources.Palette;
import resources.Sizes;
import view.components.InputButton;
import view.components.PanelSidebar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class FrameSummary extends JPanel
{
    private final InputButton btnPay;

    public FrameSummary()
    {
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

        if (cartModel.getTotal() == 0)
        {
            JLabel t1 = new JLabel("No tienes productos en la cesta");
            t1.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
            t1.setForeground(Palette.c6);
            main.add(t1);
        } else
        {
            for (int i = 0; i < list.size(); i++)
            {
                ServiceModel service = cartController.findService(list.get(i)); // Buscar el mateix servei que el numS

                if (service != null)
                {
                    main.add(createCard(service));
                    main.add(Box.createRigidArea(new Dimension(0, Sizes.x2))); // Espai
                } else
                {
                    System.out.println("No se ha encontrado el servicio: " + list.get(i));
                }
            }
        }

        add(main, BorderLayout.CENTER);

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

        // Aside - top
        JPanel asideTopPanel = new JPanel();
        asideTopPanel.setOpaque(false);

        JLabel t2 = new JLabel("RESUMEN");
        t2.setFont(new Font("Arial", Font.BOLD, Sizes.x3));

        asideTopPanel.add(t2);
        aside.add(asideTopPanel, BorderLayout.NORTH);

        // Aside - bottom
        JPanel asideBottomPanel = new JPanel();
        asideBottomPanel.setOpaque(false);
        asideBottomPanel.setLayout(new GridBagLayout());

        for (int i = 0; i < list.size(); i++)
        {
            ServiceModel service = cartController.findService(list.get(i));
            asideBottomPanel.add(createSumary(service.getTypee(), String.valueOf(service.getPrice())), gbcAside);
        }

        JPanel total = new JPanel(new BorderLayout());
        total.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
        total.setOpaque(false);

        JPanel discount = new JPanel(new BorderLayout());
        discount.setBorder(new EmptyBorder(Sizes.x1, 0, 0, 0));
        discount.setOpaque(false);

        if (cartModel.getTotal() != 0)
        {
            // label descompte
            JLabel discountLeft = new JLabel("Descuento");
            discountLeft.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

            JLabel discountRight = new JLabel(String.valueOf(cartModel.getDiscount()) + "%");
            discountRight.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

            discount.add(discountLeft, BorderLayout.WEST);
            discount.add(discountRight, BorderLayout.EAST);

            // label total
            JLabel totalLeft = new JLabel("Total");
            totalLeft.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

            JLabel totalRight = new JLabel(String.valueOf(cartModel.getTotal()) + "€");
            totalRight.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

            total.add(totalLeft, BorderLayout.WEST);
            total.add(totalRight, BorderLayout.EAST);

            asideBottomPanel.add(discount, gbcAside);
            asideBottomPanel.add(total, gbcAside);

            asideBottomPanel.add(btnPay, gbcAside);
        }

        // TODO: Afegir un límit de 6 objectes o una barra per baixar

        aside.add(asideBottomPanel, BorderLayout.SOUTH);
        add(aside, BorderLayout.EAST);
    }

    public JPanel createCard(ServiceModel service)
    {
        JPanel panel = new JPanel();
        panel.setBackground(Palette.c3);
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(0, 100));

        JPanel infoPanel = new JPanel(new GridLayout(0, 1));
        infoPanel.setOpaque(false);

        infoPanel.add(new JLabel("Tipo: " + service.getTypee()));
        infoPanel.add(new JLabel("Texto: " + service.getTxt()));
        infoPanel.add(new JLabel("Fecha inicio: " + service.getDataI().toString()));
        infoPanel.add(new JLabel("Fecha fin: " + service.getDataF().toString()));
        infoPanel.add(new JLabel("Precio total: " + service.getPrice() + "€"));

        panel.add(infoPanel, BorderLayout.WEST);
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 100)); // Ocupa només 100px
        return panel;
    }

    public JPanel createSumary(int left, String right)
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel labelLeft = new JLabel(""); // TODO: Afegir tipo
        labelLeft.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        labelLeft.setForeground(Palette.c6);

        JLabel labelRight = new JLabel(right + "€");
        labelRight.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        labelRight.setForeground(Palette.c6);

        panel.add(labelLeft, BorderLayout.WEST);
        panel.add(labelRight, BorderLayout.EAST);

        return panel;
    }
}
