package view;

import resources.Palette;
import resources.Sizes;
import view.components.ContainerDropDawn;
import view.components.InputButton;
import view.components.PanelSidebar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameCustom extends JPanel implements ActionListener
{
    private final ContainerDropDawn conSize;
    private final InputButton btnFiles;
    private final InputButton btnBuy;

    public FrameCustom(String type)
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        btnFiles = new InputButton("Subir imagen", false);
        btnBuy = new InputButton("Añadir a la cesta", true);
        conSize = new ContainerDropDawn("Tamaño", 300, new String[] {"Pequeño", "Mediano", "Grande"});

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel main = new JPanel();
        main.setLayout(new GridLayout(1, 2)); // Crear dues columnes
        main.setBackground(Palette.c3);
        main.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        // Main left panel
        JPanel mainLeftPanel = new JPanel();
        mainLeftPanel.setOpaque(false);
        mainLeftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Main right Panel
        JPanel mainRightPanel = new JPanel();
        mainRightPanel.setOpaque(false);
        mainRightPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        if (type == "flayer")
        {
            // Esquerra
            JLabel leftLabel = new JLabel("Panel flayer 1");
            mainLeftPanel.add(leftLabel);

            // Dreta
            JLabel rightLabel = new JLabel("Panel 2");
            mainRightPanel.add(rightLabel);

            main.add(mainLeftPanel);
            main.add(mainRightPanel);
        } else if (type == "web")
        {
            // Esquerra
            mainLeftPanel.add(conSize);

            // Dreta
            JLabel rightLabel = new JLabel("Panel 2");
            mainRightPanel.add(rightLabel);

            main.add(mainLeftPanel);
            main.add(mainRightPanel);
        } else if (type == "banner")
        {
            // Esquerra
            JLabel leftLabel = new JLabel("Panel banner 1");
            mainLeftPanel.add(leftLabel);

            // Dreta
            JLabel rightLabel = new JLabel("Panel 2");
            mainRightPanel.add(rightLabel);

            main.add(mainLeftPanel);
            main.add(mainRightPanel);
        } else
        {
            // TODO: Mostrar missatge d'error
        }

        add(main, BorderLayout.CENTER);

        // Aside
        JPanel aside = new JPanel();
        aside.setLayout(new BorderLayout());
        aside.setPreferredSize(new Dimension(300, 0)); // Ocupa 300px a la pantalla
        aside.setBackground(Palette.c4);
        aside.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        // Aside top panel
        JPanel asideTopPanel = new JPanel();
        asideTopPanel.setOpaque(false);

        JLabel t1 = new JLabel("RESUMEN");
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));

        //seeSumary();

        asideTopPanel.add(t1);
        aside.add(asideTopPanel, BorderLayout.NORTH);

        // Aside bottom panel
        JPanel asideBottomPanel = new JPanel();
        asideBottomPanel.setOpaque(false);
        asideBottomPanel.setLayout(new GridBagLayout());

        // Configurar
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(Sizes.x1, 0, 0, 0);

        //

        gbc.gridy = 0;
        asideBottomPanel.add(btnFiles, gbc);

        gbc.gridy = 1;
        btnBuy.addActionListener(this);
        asideBottomPanel.add(btnBuy, gbc);

        aside.add(asideBottomPanel, BorderLayout.SOUTH);
        add(aside, BorderLayout.EAST);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnBuy.getButton())
        {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameCustom.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameSummary());
            frame.revalidate();
            frame.repaint();
        }
    }

    public static void seeSumary()
    {

    }

}
