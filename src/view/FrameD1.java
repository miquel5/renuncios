package view;

import resources.Palette;
import resources.Sizes;
import view.components.ContainerDropDawn;
import view.components.ContainerText;
import view.components.InputButton;
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

public class FrameD1 extends JPanel
{
    private final ContainerDropDawn conPay;
    private DefaultTableModel tableModel;
    private JTable table;
    private final InputButton btnAddService;
    private final ContainerDropDawn conTipo;
    private final ContainerDropDawn conColor;
    private final ContainerDropDawn conPago;

    public FrameD1()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        conPay = new ContainerDropDawn("Pagado", 100, new String[]{"- - -", "Si", "No"});
        conTipo = new ContainerDropDawn("Tipo de servicio", 150, new String[]{"- - -", "Web", "Flayer", "Pancarta"});
        conColor = new ContainerDropDawn("Color", 100, new String[]{"- - -", "Sí", "No"});
        conPago = new ContainerDropDawn("Tipo de pago", 100, new String[]{"- - -", "Único", "Mensual"});
        btnAddService = new InputButton("Añadir servicio", true);

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

        // Crear el JLabel para el texto centrado
        JLabel t1 = new JLabel("SERVICIO");
        t1.setHorizontalAlignment(JLabel.CENTER);
        t1.setBorder(new EmptyBorder(0, 0, 10, 0));
        t1.setFont(new Font("Arial", Font.BOLD, Sizes.x3));
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
            frame.add(new FrameD3());
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
            frame.add(new FrameD2());
            frame.revalidate();
            frame.repaint();
            }
        });

        // Agregar componentes al settingsPanel
        settingsPanel.add(leftArrow, BorderLayout.WEST);
        settingsPanel.add(t1, BorderLayout.CENTER);
        settingsPanel.add(rightArrow, BorderLayout.EAST);

        // Search panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(0, 100));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Tipo
        searchPanel.add(conTipo);

        // Color
        searchPanel.add(conColor);

        // Pago
        searchPanel.add(conPago);

        // Botón "Añadir Servicio
        Box box = Box.createVerticalBox();
        box.add(Box.createRigidArea(new Dimension(0, 13))); // Espacio superior de 10px
        box.add(btnAddService);

        // Agregar acción al botón
        btnAddService.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // Cambiar a FrameNewService
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameD1.this);
                frame.getContentPane().removeAll();
                frame.add(new Null1()); //TODO: Que funcioni framenewservice
                frame.revalidate();
                frame.repaint();
            }
        });

        // Agregar el botón al panel de búsqueda
        searchPanel.add(box);

        // Agregar ActionListener para los filtros
        ActionListener filterListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String tipo = (String) conTipo.getSelectedItem();
                String color = (String) conColor.getSelectedItem();
                String pago = (String) conPago.getSelectedItem();
                filterTable(tipo, color, pago);
            }
        };

        conTipo.addActionListener(filterListener);
        conColor.addActionListener(filterListener);
        conPago.addActionListener(filterListener);

        // Agregar settingsPanel y searchPanel al topPanel
        topPanel.add(settingsPanel);
        topPanel.add(searchPanel);

        // Dashboard
        JPanel crudPanel = new JPanel(new BorderLayout());
        crudPanel.setOpaque(true);
        crudPanel.setBackground(Palette.c3);

        // nom de les taules
        String[] columnNames = {"Num. servicio", "Imagen", "Tipo de servicio", "Fecha inicio", "Fecha fin", "Color", "Tipo pago", "-"};

        // Obtener i guardar datos de la consulta
        Object[][] data = DatabaseQueries.selectAllServicios();

        // Crear la tabla
        tableModel = new DefaultTableModel(data, columnNames);
        table = new JTable(tableModel);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setGridColor(Color.LIGHT_GRAY);
        table.setRowHeight(30);

        // Configurar encabezados
        JTableHeader header = table.getTableHeader();
        table.getTableHeader().setResizingAllowed(false); // Desactivar poder redimensionar
        header.setBackground(Palette.c1);
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

        // Accions per cada taula
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint()); // Fila seleccionada
                int column = table.columnAtPoint(e.getPoint()); // Columna seleccionada

                if (column == 7) { // Verifica si se hace clic en la columna "Eliminar"
                    if (column == table.getColumnCount() - 1) {
                        int numserv = (int) table.getValueAt(row, 0); // Obtener "Num. Recibo"
                        int confirm = JOptionPane.showConfirmDialog(null, "Quieres eliminar el servicio?");
                        boolean success = false;

                        if (confirm == JOptionPane.YES_OPTION) {
                            success = DatabaseQueries.eliminarServicio(numserv); // Llamar a la consulta
                        }

                        if (success) {
                            // Eliminar la fila del modelo
                            tableModel.removeRow(row);
                            JOptionPane.showMessageDialog(null, "Servicio eliminado con éxito.");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al eliminar el servicio.");
                        }
                    }
                }
            }
        });

        // Renderizar la columna de imágenes
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof ImageIcon) {
                    JLabel label = new JLabel((ImageIcon) value);
                    label.setHorizontalAlignment(JLabel.CENTER); // Centrar la imagen
                    return label;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        // Desactivar poder ediatr
        table.setDefaultEditor(Object.class, null);

        // No mostrar las lineas divisoras
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

    private void filterTable(String tipo, String color, String pago)
    {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        if (!tipo.equals("- - -"))
        {
            filters.add(RowFilter.regexFilter(tipo, 2));
        }

        if (!color.equals("- - -"))
        {
            String colorValue = color.equalsIgnoreCase("Sí") ? "Sí" : "No";
            filters.add(RowFilter.regexFilter(colorValue, 5));
        }

        if (!pago.equals("- - -"))
        {
            String pagoValue = pago.equalsIgnoreCase("Único") ? "Único" : "Mensual";
            filters.add(RowFilter.regexFilter(pagoValue, 6));
        }

        // Combinar filtros solo si hay al menos uno
        RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(combinedFilter);
        table.setRowSorter(sorter);
        table.repaint();
    }
}
