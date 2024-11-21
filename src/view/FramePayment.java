package view;

import controller.CartController;
import controller.GeneralController;
import model.CartModel;
import model.ServiceModel;
import resources.Palette;
import resources.Sizes;
import service.DatabaseQueries;
import view.components.InputButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FramePayment extends JPanel implements ActionListener
{
    private final JLabel t1;
    private final InputButton btnConfirm;
    private final InputButton btnBack;
    private CartModel cartModel;
    private CartController cartController;

    public FramePayment()
    {
        DecimalFormat df = new DecimalFormat("#.00");
        cartModel = CartModel.getInstance();
        cartController = new CartController();

        // Configurar la pantalla
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, Sizes.x1, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setBackground(Palette.c3);

        // Elements
        btnBack = new InputButton("Atrás", false);
        btnConfirm = new InputButton("Confirmar", true);


        // Titol
        gbc.gridy = 0;
        gbc.gridx = 1;
        t1 = new JLabel("RESUMEN");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
        t1.setForeground(Palette.c7);
        add(t1, gbc);

        // Llista de productes
        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        productPanel.setBackground(Palette.c3);

        ArrayList<Integer> productList = cartModel.getList();

        for (Integer productId : productList)
        {
            ServiceModel service = cartController.findService(productId);

            if (service != null)
            {
                productPanel.add(createSumary(GeneralController.whatService(service.getTipo()), String.valueOf(service.getPrecio())));
            }
        }

        gbc.gridy = 2;
        add(productPanel, gbc);

        JPanel panelTotal = new JPanel(new BorderLayout());
        panelTotal.setOpaque(false);

        JLabel labelLeft = new JLabel("Precio total");
        labelLeft.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        labelLeft.setForeground(Palette.c7);
        labelLeft.setBorder(new EmptyBorder(Sizes.x2, 0, Sizes.x1, 0));
        JLabel labelRight = new JLabel(df.format(cartModel.getTotal()) + "€"); // Formatejar a 2 dijits
        labelRight.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        labelRight.setForeground(Palette.c7);

        panelTotal.add(labelLeft, BorderLayout.WEST);
        panelTotal.add(labelRight, BorderLayout.EAST);
        productPanel.add(panelTotal, gbc);

        // button atrás
        gbc.gridy = 4;
        btnBack.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        btnBack.addActionListener(this);
        add(btnBack, gbc);

        // button confirm
        gbc.gridy = 5;
        btnConfirm.setPreferredSize(new Dimension(200, btnConfirm.getPreferredSize().height));
        btnConfirm.addActionListener(this);
        add(btnConfirm, gbc);
    }

    // Crear una línea del tíquet
    public JPanel createSumary(String left, String right)
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel labelLeft = new JLabel(left);
        labelLeft.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        labelLeft.setForeground(Palette.c6);

        JLabel labelRight = new JLabel(right + "€");
        labelRight.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        labelRight.setForeground(Palette.c6);

        panel.add(labelLeft, BorderLayout.WEST);
        panel.add(labelRight, BorderLayout.EAST);

        return panel;
    }

    // Accions dels botons
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JFrame frame = null;

        if (e.getSource() == btnBack.getButton())
        {
            frame = (JFrame) SwingUtilities.getWindowAncestor(FramePayment.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameSummary());
        } else if (e.getSource() == btnConfirm.getButton())
        {
            DatabaseQueries.generateTicket();

            cartModel.setTotal(0); // Reiniciar el total
            cartModel.subtractList(); // Eliminar elements de la llista

            frame = (JFrame) SwingUtilities.getWindowAncestor(FramePayment.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameHome());
        }

        frame.revalidate();
        frame.repaint();
    }
}
