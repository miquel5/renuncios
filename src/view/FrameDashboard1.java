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

public class FrameDashboard1 extends JPanel
{
    public FrameDashboard1()
    {
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

        // Search Panel
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(0, 100));
        searchPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Dashboard
        JPanel crudPanel = new JPanel(new BorderLayout());
        crudPanel.setOpaque(true);
        crudPanel.setBackground(Palette.c3);

        // Dades de les taules
        String[] columnNames = {"ID", "Nombre", "Edad"};

        Object[][] data = {
                {1, "Alice", 25},
                {2, "Bob", 30},
                {3, "Charlie", 22}
        };

        // Crear la taula
        DefaultTableModel tableModel = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setRowHeight(30);

        // Configurar encabezados
        JTableHeader header = table.getTableHeader();
        header.setBackground(Palette.c7);
        header.setForeground(Palette.c3);

        // Reordenamiento de columnas (Desactivat)
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        cellRenderer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        crudPanel.add(scrollPane, BorderLayout.CENTER);

        main.add(searchPanel, BorderLayout.NORTH);
        main.add(crudPanel, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }
}
