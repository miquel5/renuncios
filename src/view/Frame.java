package view;

import resources.Palette;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame
{
    FrameLogin lamina1;

    public Frame()
    {
        Image isotype = new ImageIcon(getClass().getResource("/assets/Isotype.png")).getImage();

        setTitle("Renuncios");
        setSize(1300, 700);
        //setMinimumSize(new Dimension(1300, 700));
        setBackground(Palette.c8);
        setIconImage(isotype);

        lamina1 = new FrameLogin();
        add(lamina1);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar el programa al executar
        setVisible(true);
    }
}
