package resources;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Sizes
{
    // Mides
    public final static int x1 = 6;
    public final static int x2 = 12;
    public final static int x3 = 16;
    public final static int x4 = 32;

    // Espais i bordes
    public final static Border borderC5 = BorderFactory.createLineBorder(Palette.c5, 1);
    public final static Border borderC1 = BorderFactory.createLineBorder(Palette.c1, 1);
    public final static EmptyBorder padding = new EmptyBorder(x1, x1, x1, x1);
}
