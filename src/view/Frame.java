package view;

import model.CartModel;
import resources.Palette;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame
{
    FrameLogin lamina1;

    public Frame()
    {
        // Icona del prjecte
        Image isotype = new ImageIcon(getClass().getResource("/assets/Isotype.png")).getImage();

        // Configuració general de la pantalla
        setTitle("Renuncios");
        setSize(1300, 700);
        setMinimumSize(new Dimension(1300, 700));
        setBackground(Palette.c8);
        setIconImage(isotype);

        // Pantalla inicial del programa
        lamina1 = new FrameLogin();
        add(lamina1);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar el programa al executar
        setVisible(true);
    }

}
