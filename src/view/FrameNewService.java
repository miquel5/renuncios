package view;

import resources.Palette;
import resources.Sizes;
import view.components.InputButton;
import view.components.PanelSidebar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameNewService extends JPanel implements ActionListener
{
    private final InputButton btnBack;

    public FrameNewService()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        btnBack = new InputButton("Atrás", false);

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(Palette.c3);
        main.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, Sizes.x1, 0);

        // Título
        JLabel t1 = new JLabel("NUEVO SERVICIO");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
        t1.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
        t1.setForeground(Palette.c7);
        main.add(t1, gbc);

        // Botón Atrás
        gbc.gridy = 1;
        btnBack.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        btnBack.addActionListener(this);
        main.add(btnBack, gbc);

        add(main, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnBack.getButton())
        {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameNewService.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameD1()); // Atrás
            frame.revalidate();
            frame.repaint();
        }
    }
}
