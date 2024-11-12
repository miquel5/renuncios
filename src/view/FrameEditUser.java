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

public class FrameEditUser extends JPanel implements ActionListener
{
    private final InputButton btnBack;
    private final InputButton btnConfirm;

    public FrameEditUser()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        btnBack = new InputButton("Atrás", false);
        btnConfirm = new InputButton("Confirmar", true);


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

        // Botón perfil
        gbc.gridy = 1;
        btnBack.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        main.add(btnBack, gbc);

        // Botón ajustes
        gbc.gridy = 2;
        btnConfirm.setPreferredSize(new Dimension(200, btnConfirm.getPreferredSize().height));
        main.add(btnConfirm, gbc);

        add(main, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnBack.getButton())
        {
            // TODO: Moure a nova pantalla
        } else if (e.getSource() == btnConfirm.getButton())
        {
            // TODO: Afegir lògica
        }
    }
}
