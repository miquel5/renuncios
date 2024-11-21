package view.components;

import model.CartModel;
import model.UserModel;
import resources.Palette;
import resources.Sizes;
import view.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelSidebar
{
    private final JPanel sidebar;

    public PanelSidebar()
    {
        sidebar = new JPanel();

        sidebar.setPreferredSize(new Dimension(57, 0));
        sidebar.setBackground(Palette.c3);
        sidebar.setBorder(new EmptyBorder(Sizes.x1, Sizes.x3, Sizes.x1, Sizes.x3));
        sidebar.setLayout(new GridBagLayout());

        // Rutes
        String[] routes = { "/assets/icons/search.png",
                            "/assets/icons/cart.png",
                            "/assets/icons/dashboard.png",
                            "/assets/icons/wallet.png",
                            "/assets/icons/settings.png",
                            "/assets/icons/goout.png"
        };

        UserModel user = UserModel.getInstance();

        // Afegir les icones
        for (int i = 0; i < routes.length; i++)
        {
            // El cas 2 només és mostra si és admin
            if (i == 2)
            {
                if (user.getRole().equalsIgnoreCase("admin"))
                {
                    addIcon(routes[i], i, user);
                }
            } else
            {
                addIcon(routes[i], i, user);
            }
        }

    }

    private void addIcon(String route, int i, UserModel user)
    {
        ImageIcon icon = new ImageIcon(getClass().getResource(route));
        Image scaledIcon = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);

        JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));
        iconLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Afegir lògica per entrar a cada pantalla
        iconLabel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(sidebar);
            frame.getContentPane().removeAll();

            switch (i)
            {
                case 0:
                    frame.add(new FrameHome());
                    break;
                case 1:
                    frame.add(new FrameSummary());
                    break;
                case 2:
                    frame.add(new FrameDashboard1());
                    break;
                case 3:
                    frame.add(new FrameTicket());
                    break;
                case 4:
                    frame.add(new FrameSettings());
                    break;
                case 5:
                    frame.add(new FrameLogin());
                    CartModel cartModel = CartModel.getInstance();
                    cartModel.setTotal(0); // Reiniciar total
                    cartModel.subtractList(); // Eliminar elements de la llista
                    break;
                default:
                    System.out.println("Error: Swich() - PanelSlidebar.java");
            }

            frame.revalidate();
            frame.repaint();
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = i;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(Sizes.x3, 0, 0, 0);
        gbc.anchor = GridBagConstraints.CENTER;

        sidebar.add(iconLabel, gbc);
    }

    public JPanel getPanel()
    {
        return sidebar;
    }
}
