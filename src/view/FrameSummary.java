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
        btnPay = new InputButton("Pagar", true);

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

        for (int i = 0; i < list.size(); i++)
        {
            ServiceModel service = cartController.findService(list.get(i));

            if (service != null)
            {
                main.add(createCard(service));
                main.add(Box.createRigidArea(new Dimension(0, Sizes.x2))); // Espai
            } else
            {
                System.out.println("No encontrado el servicio: " + list.get(i));
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

        JLabel t1 = new JLabel("RESUMEN");
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));

        asideTopPanel.add(t1);
        aside.add(asideTopPanel, BorderLayout.NORTH);

        // Aside - bottom
        JPanel asideBottomPanel = new JPanel();
        asideBottomPanel.setOpaque(false);
        asideBottomPanel.setLayout(new GridBagLayout());

        gbcAside.gridy = 0;
        asideBottomPanel.add(btnPay, gbcAside);

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
        infoPanel.add(new JLabel("Precio total: " + service.getPrice()));

        panel.add(infoPanel, BorderLayout.WEST);
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 100)); // Ocupa només 100px
        return panel;
    }
}
