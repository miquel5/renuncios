package view;

import resources.Palette;
import resources.Sizes;
import view.components.ContainerDropDawn;
import view.components.ContainerText;
import view.components.PanelSidebar;
import service.DatabaseQueries;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class FrameD3 extends JPanel
{
    private final ContainerDropDawn conTipo;
    private final ContainerDropDawn conTipoPago;
    private final ContainerDropDawn conPagado;
    private DefaultTableModel tableModel;
    private JTable table;
    private ContainerText conRecibo;
    private ContainerText conContratacion;
    private ContainerText conServicio;

    public FrameD3()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        conTipo = new ContainerDropDawn("Tipo de servicio", 100, new String[]{"- - -", "Web", "Flayer", "Pancarta"});
        conTipoPago = new ContainerDropDawn("Tipo de pago", 100, new String[]{"- - -", "Único", "Mensual"});
        conPagado = new ContainerDropDawn("Pagado", 100, new String[]{"- - -", "Sí", "No"});
        conRecibo = new ContainerText("Num. recibo", 150, true);
        conContratacion =  new ContainerText("Num. contractación", 150, true);
        conServicio = new ContainerText("Num. servicio", 150, true);

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
        settingsPanel.setBorder(new EmptyBorder(20, 0, 20, 0));

        JLabel t1 = new JLabel("TICKETS");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setBorder(new EmptyBorder(0, 0, 10, 0));
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
        t1.setForeground(Color.DARK_GRAY);

        ImageIcon leftIcon = new ImageIcon(getClass().getResource("/assets/icons/left.png"));
        Image leftScaledImage = leftIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel leftArrow = new JLabel(new ImageIcon(leftScaledImage));
        leftArrow.setHorizontalAlignment(JLabel.LEFT);
        leftArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        leftArrow.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameD3.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameD2());
                frame.revalidate();
                frame.repaint();
            }
        });

        ImageIcon rightIcon = new ImageIcon(getClass().getResource("/assets/icons/right.png"));
        Image rightScaledImage = rightIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel rightArrow = new JLabel(new ImageIcon(rightScaledImage));
        rightArrow.setHorizontalAlignment(JLabel.RIGHT);
        rightArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        rightArrow.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameD3.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameD1());
                frame.revalidate();
                frame.repaint();
            }
        });

        settingsPanel.add(leftArrow, BorderLayout.WEST);
        settingsPanel.add(t1, BorderLayout.CENTER);
        settingsPanel.add(rightArrow, BorderLayout.EAST);

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(0, 100));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Tipo de servicio
        searchPanel.add(conTipo);

        // Tipo de pago
        //searchPanel.add(conTipoPago); // No és motra ara pqerque vull

        // Pagado
        searchPanel.add(conPagado);

        // Num. recibo
        searchPanel.add(conRecibo);

        // Num. contractación
        searchPanel.add(conContratacion);

        // Num. servicio
        searchPanel.add(conServicio);

        // Agregar ActionListener para filtros
        ActionListener filterListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String numRecibo = conRecibo.getTextField().getText();
                String numContratacion = conContratacion.getTextField().getText();
                String numServicio = conServicio.getTextField().getText();
                String tipo = (String) conTipo.getSelectedItem();
                String tipoPago = (String) conTipoPago.getSelectedItem();
                String pagado = (String) conPagado.getSelectedItem();
                filterTable(numRecibo, numContratacion, numServicio, tipo, tipoPago, pagado);
            }
        };

        // Accions
        conRecibo.getTextField().addActionListener(filterListener);
        conContratacion.getTextField().addActionListener(filterListener);
        conServicio.getTextField().addActionListener(filterListener);
        conTipo.addActionListener(filterListener);
        conTipoPago.addActionListener(filterListener);
        conPagado.addActionListener(filterListener);

        // Agregar panels al topPanel
        topPanel.add(settingsPanel);
        topPanel.add(searchPanel);

        // Dashboard
        JPanel crudPanel = new JPanel(new BorderLayout());
        crudPanel.setOpaque(true);
        crudPanel.setBackground(Palette.c3);

        // Nom de les taules
        String[] columnNames = {"Num. recibo", "Num. contratación", "Num. servicio", "CIF/NIF", "Tipo servicio", "Precio", "Pagado"};

        // Obtener i guardar datos de la consulta
        Object[][] data = DatabaseQueries.selectAllTiquets();

        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setRowHeight(30);

        JTableHeader header = table.getTableHeader();
        table.getTableHeader().setResizingAllowed(false); // Desactivar poder redimensionar
        header.setBackground(Palette.c1);
        header.setForeground(Palette.c3);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        cellRenderer.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        for (int i = 0; i < table.getColumnCount(); i++)
        {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // Accions per cada taula
        /*table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint()); // Fila seleccionada
                int column = table.columnAtPoint(e.getPoint()); // Columna seleccionada

                if (column == 8)
                {
                    System.out.println("Ver");
                }
            }
        });*/

        // Desactivar poder ediatr
        table.setDefaultEditor(Object.class, null);

        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        // Supón que 'table' es tu JTable y 'tableModel' es el modelo de la tabla
        table.setModel(tableModel);

    // Desactivar reordenamiento de las columnas
        table.getTableHeader().setReorderingAllowed(false);

        // Add panels to main panel
        crudPanel.add(scrollPane, BorderLayout.CENTER);

        main.add(topPanel, BorderLayout.NORTH);
        main.add(crudPanel, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);
    }

    private void filterTable(String numRecibo, String numContratacion, String numServicio, String tipo, String tipoPago, String pagado)
    {
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) table.getRowSorter();
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        if (!numRecibo.isEmpty())
        {
            filters.add(RowFilter.regexFilter(numRecibo, 0));
        }

        if (!numContratacion.isEmpty())
        {
            filters.add(RowFilter.regexFilter(numContratacion, 1));
        }

        if (!numServicio.isEmpty())
        {
            filters.add(RowFilter.regexFilter(numServicio, 2));
        }

        if (tipo != null && !tipo.equals("- - -"))
        {
            filters.add(RowFilter.regexFilter(tipo, 4));
        }

        /*if (tipoPago != null && !tipoPago.equals("- - -"))
        {
            filters.add(RowFilter.regexFilter(tipoPago, 5));
        }*/

        if (pagado != null && !pagado.equals("- - -"))
        {
            filters.add(RowFilter.regexFilter(pagado, 6));
        }

        RowFilter<Object, Object> compoundFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(compoundFilter);
    }
}
