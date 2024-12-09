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

public class FrameD2 extends JPanel
{
    private final ContainerDropDawn conRole;
    private DefaultTableModel tableModel;
    private JTable table;
    private final InputButton btnAddUser;
    private final ContainerText conUser;
    private final ContainerText conSector;
    private final ContainerText conCIF;

    public FrameD2()
    {
        // Configurar la pantalla
        setLayout(new BorderLayout());

        // Elements
        conRole = new ContainerDropDawn("Rol", 150, new String[]{"- - -", "Usuario", "Administrador"});
        conUser = new ContainerText("Usuario", 150, true);
        conSector = new ContainerText("Sector", 150, true);
        conCIF = new ContainerText("CIF", 150, true);
        btnAddUser = new InputButton("Añadir usuario", true);

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
        searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setOpaque(false);
        searchPanel.setPreferredSize(new Dimension(0, 100));
        searchPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Rol
        searchPanel.add(conRole);

        // Usuario
        searchPanel.add(conUser);

        // Sector
        searchPanel.add(conSector);

        // CIF
        searchPanel.add(conCIF);

        // Box botón "Añadir Usuario"
        Box box = Box.createVerticalBox();
        box.add(Box.createRigidArea(new Dimension(0, 13))); // Espacio superior de 10px
        box.add(btnAddUser);

        // Agregar acción al botón
        btnAddUser.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(FrameD2.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameNewUser());
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
                String user = conUser.getTextField().getText();
                String role = conRole.getSelectedItem().toString();
                String sector = conSector.getTextField().getText();
                String cif = conCIF.getTextField().getText();
                filterTable(user, role, sector, cif);
            }
        };

        conUser.getTextField().addActionListener(filterListener);
        conRole.addActionListener(filterListener);
        conSector.getTextField().addActionListener(filterListener);
        conCIF.getTextField().addActionListener(filterListener);

        // Agregar settingsPanel y searchPanel al topPanel
        topPanel.add(settingsPanel);
        topPanel.add(searchPanel);

        // Dashboard
        JPanel crudPanel = new JPanel(new BorderLayout());
        crudPanel.setOpaque(true);
        crudPanel.setBackground(Palette.c3);

        // Nom de les taules
        String[] columnNames = {"Usuari", "Rol", "Sector", "CIF", "Empresa", "Acciónes"};

        // Obtener i guardar datos de la consulta
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

        for (int i = 0; i < table.getColumnCount(); i++)
        {
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

    private void filterTable(String user, String role, String sector, String cif)
    {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        if (!user.isEmpty())
        {
            filters.add(RowFilter.regexFilter("(?i)" + user, 0));
        }

        if (!role.equals("- - -"))
        {
            filters.add(RowFilter.regexFilter(role, 1));
        }

        if (!sector.isEmpty())
        {
            filters.add(RowFilter.regexFilter("(?i)" + sector, 2));
        }

        if (!cif.isEmpty())
        {
            filters.add(RowFilter.regexFilter(cif, 3));
        }

        // Combinar filtros solo si hay al menos uno
        RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);
        sorter.setRowFilter(combinedFilter);
        table.setRowSorter(sorter);
        table.repaint();
    }
}
