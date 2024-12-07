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

public class FrameSettings extends JPanel implements ActionListener
{
    private final InputButton btnPerfile;
    private final InputButton btnSettings;

    public FrameSettings()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        btnPerfile = new InputButton("Mi perfil", true);
        btnSettings = new InputButton("Ajustes", true);


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
        JLabel t1 = new JLabel("CONFIGURACIÓN");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
        t1.setBorder(new EmptyBorder(0, 0, Sizes.x1, 0));
        t1.setForeground(Palette.c7);
        main.add(t1, gbc);

        // Botón perfil
        gbc.gridy = 1;
        btnPerfile.setPreferredSize(new Dimension(200, btnPerfile.getPreferredSize().height));
        btnPerfile.addActionListener(this);
        main.add(btnPerfile, gbc);

        // Botón ajustes
        /*gbc.gridy = 2;
        btnSettings.setPreferredSize(new Dimension(200, btnSettings.getPreferredSize().height));
        btnSettings.addActionListener(this);
        main.add(btnSettings, gbc);*/

        add(main, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnPerfile.getButton())
        {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameSettings.this);
            frame.getContentPane().removeAll();
            frame.add(new FramePerfile());
            frame.revalidate();
            frame.repaint();
        } else if (e.getSource() == btnSettings.getButton())
        {
            // TODO: Moure a nova pantalla
        }

    }
}
