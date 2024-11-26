package view;

import resources.Palette;
import resources.Sizes;
import view.components.PanelSidebar;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrameD1 extends JPanel {
    public FrameD1() {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Main
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.setBackground(Palette.c3);
        main.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        // Panel contenedor para settingsPanel y searchPanel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        // Settings panel
        JPanel settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setOpaque(false);
        settingsPanel.setPreferredSize(new Dimension(0, 80));
        settingsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        // Crear el JLabel para el texto centrado
        JLabel t1 = new JLabel("USUARIOS");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setBorder(new EmptyBorder(0, 0, 10, 0));
        t1.setFont(new Font("Arial", Font.BOLD, 24));
        t1.setForeground(Color.DARK_GRAY);

        // Crear el icono izquierdo
        ImageIcon leftIcon = new ImageIcon(getClass().getResource("/assets/icons/left.png"));
        Image leftScaledImage = leftIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel leftArrow = new JLabel(new ImageIcon(leftScaledImage));
        leftArrow.setHorizontalAlignment(JLabel.LEFT);
        leftArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        leftArrow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameD1.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameD1());
                frame.revalidate();
                frame.repaint();
            }
        });

        // Crear el icono derecho
        ImageIcon rightIcon = new ImageIcon(getClass().getResource("/assets/icons/right.png"));
        Image rightScaledImage = rightIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel rightArrow = new JLabel(new ImageIcon(rightScaledImage));
        rightArrow.setHorizontalAlignment(JLabel.RIGHT);
        rightArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rightArrow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameD1.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameD1());
                frame.revalidate();
                frame.repaint();
            }
        });

        // Agregar componentes al settingsPanel
        settingsPanel.add(leftArrow, BorderLayout.WEST);
        settingsPanel.add(t1, BorderLayout.CENTER);
        settingsPanel.add(rightArrow, BorderLayout.EAST);

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(0, 100));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Agregar settingsPanel y searchPanel al topPanel
        topPanel.add(settingsPanel);
        topPanel.add(searchPanel);

        // Dashboard
        JPanel crudPanel = new JPanel(new BorderLayout());
        crudPanel.setOpaque(true);
        crudPanel.setBackground(Palette.c3);

        // Dades de les taules
        String[] columnNames = {"ID", "Servicio", "Tipo"};

        Object[][] data = {
                {1, "ASD", 1},
                {2, "WER", 2},
                {3, "YTH", 3}
        };

        // Crear la taula
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setForeground(Palette.c1);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setRowHeight(30);

        // Configurar encabezados
        JTableHeader header = table.getTableHeader();
        table.getTableHeader().setResizingAllowed(false);
        header.setBackground(Palette.c1);
        header.setForeground(Palette.c3);

        // Reordenamiento de columnas (Desactivat)
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        cellRenderer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // No mostrar les linies divisores
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        crudPanel.add(scrollPane, BorderLayout.CENTER);

        // Agregar panels al main
        main.add(topPanel, BorderLayout.NORTH);
        main.add(crudPanel, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }

}
