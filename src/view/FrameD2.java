package view;

import resources.Palette;
import resources.Sizes;
import view.components.ContainerDropDawn;
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

public class FrameD2 extends JPanel
{
    private final ContainerDropDawn conPay;
    private final ContainerDropDawn conRole;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField sectorField;
    private JTextField cifField;
    private JTextField userField;

    public FrameD2()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        conPay = new ContainerDropDawn("Pagado", 200, new String[]{"- - -", "SÍ", "No"});
        conRole = new ContainerDropDawn("Rol", 200, new String[]{"- - -", "Usuario", "Admin"});

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
        JLabel t1 = new JLabel("USUARIOS");
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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameD2.this);
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
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameD2.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameD3());
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
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 0)); // Mantener el diseño horizontal
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(0, 100));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Filtros
        userField = new JTextField(10);
        sectorField = new JTextField(10);
        cifField = new JTextField(10);

        // Establecer el tamaño preferido para los JTextField
        Dimension textFieldSize = new Dimension(200, 30);
        userField.setPreferredSize(textFieldSize);
        sectorField.setPreferredSize(textFieldSize);
        cifField.setPreferredSize(textFieldSize);

        // Crear paneles para cada campo
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
        userPanel.setOpaque(false);
        JLabel userLabel = new JLabel("Usuario:");
        userField = new JTextField(15);
        userField.setPreferredSize(textFieldSize);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        userField.setAlignmentX(Component.LEFT_ALIGNMENT);
        userPanel.add(userLabel);
        userPanel.add(userField);

        JPanel sectorPanel = new JPanel();
        sectorPanel.setLayout(new BoxLayout(sectorPanel, BoxLayout.Y_AXIS));
        sectorPanel.setOpaque(false);
        JLabel sectorLabel = new JLabel("Sector:");
        sectorField = new JTextField(15);
        sectorField.setPreferredSize(textFieldSize);
        sectorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectorField.setAlignmentX(Component.LEFT_ALIGNMENT);
        sectorPanel.add(sectorLabel);
        sectorPanel.add(sectorField);

        JPanel cifPanel = new JPanel();
        cifPanel.setLayout(new BoxLayout(cifPanel, BoxLayout.Y_AXIS));
        cifPanel.setOpaque(false);
        JLabel cifLabel = new JLabel("CIF:");
        cifField = new JTextField(15);
        cifField.setPreferredSize(textFieldSize);
        cifLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cifField.setAlignmentX(Component.LEFT_ALIGNMENT);
        cifPanel.add(cifLabel);
        cifPanel.add(cifField);

        // Agregar componentes al searchPanel
        searchPanel.add(userPanel);
        searchPanel.add(conRole);
        searchPanel.add(sectorPanel);
        searchPanel.add(cifPanel);

        // Botón "Añadir Usuario"
        JButton btnAddUser = new JButton("Añadir Usuario");
        btnAddUser.setPreferredSize(conPay.getPreferredSize()); // Igualar tamaño al botón "Pagado"
        btnAddUser.setBackground(Palette.c1); // Establecer el color de fondo
        btnAddUser.setForeground(Color.WHITE); // Establecer el color del texto
        btnAddUser.setBorder(BorderFactory.createEmptyBorder()); // Sin borde

        // Agregar acción al botón
        btnAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar a FrameNewUser
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameD2.this);
                frame.getContentPane().removeAll();
                //frame.add(new FrameNewUser()); // Asegúrate de que FrameNewUser esté importado //todo
                frame.revalidate();
                frame.repaint();
            }
        });

        searchPanel.add(btnAddUser); // Agregar el botón "Añadir Usuario" al searchPanel

        // Agregar ActionListener para los filtros
        ActionListener filterListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String role = (String) conRole.getSelectedItem();
                String sector = sectorField.getText();
                String cif = cifField.getText();
                filterTable(user, role, sector, cif);
            }
        };

        userField.addActionListener(filterListener);
        conRole.addActionListener(filterListener);
        sectorField.addActionListener(filterListener);
        cifField.addActionListener(filterListener);

        // Agregar settingsPanel y searchPanel al topPanel
        topPanel.add(settingsPanel);
        topPanel.add(searchPanel);

        // Dashboard
        JPanel crudPanel = new JPanel(new BorderLayout());
        crudPanel.setOpaque(true);
        crudPanel.setBackground(Palette.c3);

        // Dades de les taules
        String[] columnNames = {"Usuari", "Rol", "Sector", "CIF", "Empresa"};

        // Obtenir dades
        Object[][] data = DatabaseQueries.selectAllUsuarios();

        // Crear la taula
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

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }

        // No mostrar las líneas divisorias
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

    private void filterTable(String user, String role, String sector, String cif) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        // Filtrar por Usuario
        if (!user.isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + user, 0)); // Columna 0 es Usuario
        }

        // Filtrar por Rol
        if (!role.equals("- - -")) {
            filters.add(RowFilter.regexFilter(role, 1)); // Columna 1 es Rol
        }

        // Filtrar por Sector
        if (!sector.isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + sector, 2)); // Columna 2 es Sector
        }

        // Filtrar por CIF
        if (!cif.isEmpty()) {
            filters.add(RowFilter.regexFilter(cif, 3)); // Columna 3 es CIF
        }

        // Combinar filtros solo si hay al menos uno
        RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(combinedFilter);
        table.setRowSorter(sorter);
        table.repaint();
    }
}
