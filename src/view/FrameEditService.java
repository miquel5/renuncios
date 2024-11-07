package view;

import resources.Palette;
import resources.Sizes;
import view.components.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameEditService extends JPanel implements ActionListener
{
    private final ContainerDropDawn conType;
    private final ContainerDropDawn conSize;
    private final ContainerText conName;
    private final ContainerText conPrice;
    private final CheckBox boxColor;
    private final InputButton btnArchive;
    private final InputButton btnBack;
    private final InputButton btnConfirm;

    public FrameEditService()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        conType = new ContainerDropDawn("Tipo", 200, new String[]{"Web", "Flayer", "Pancarta"});
        conSize = new ContainerDropDawn("Tamaño", 200, new String[]{"Pequeño", "Mediano", "Grande"});
        conName = new ContainerText("Nombre", 200, true);
        conPrice = new ContainerText("Precio", 200, true);
        boxColor = new CheckBox("Color", 200);
        btnArchive = new InputButton("Subir imagen", false);
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

        // Desplegable tipo
        gbc.gridy = 1;
        main.add(conType, gbc);

        // Desplegable tamaño
        gbc.gridy = 2;
        main.add(conSize, gbc);

        // Input nom
        gbc.gridy = 3;
        main.add(conName, gbc);

        // Input precio
        gbc.gridy = 4;
        main.add(conPrice, gbc);

        //  CheckBox color
        gbc.gridy = 5;
        // boolean estadoColor = boxColor.isSelected();
        main.add(boxColor, gbc);

        // Botón archivo
        gbc.gridy = 6;
        btnArchive.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        main.add(btnArchive, gbc);

        // Botón perfil
        gbc.gridy = 7;
        btnBack.setPreferredSize(new Dimension(200, btnBack.getPreferredSize().height));
        main.add(btnBack, gbc);

        // Botón ajustes
        gbc.gridy = 8;
        btnConfirm.setPreferredSize(new Dimension(200, btnConfirm.getPreferredSize().height));
        main.add(btnConfirm, gbc);

        add(main, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == btnArchive.getButton())
        {
            // TODO: Moure a nova pantalla
        } else if (e.getSource() == btnBack.getButton())
        {
            // TODO: Moure a la pantalla anterior
        } else if(e.getSource() == btnConfirm.getButton())
        {
            // TODO: Afegir lògica
        }
    }
}
