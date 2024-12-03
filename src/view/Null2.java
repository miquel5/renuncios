package view;

import resources.Palette;
import resources.Sizes;
import view.components.*;
import service.DatabaseQueries;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Null2 extends JPanel implements ActionListener {
    private final ContainerText conUsuari;
    private final ContainerText conContrasenya;
    private final ContainerDropDawn conRol;
    private final ContainerText conCIF;
    private final ContainerText conEmpresa;
    private final ContainerText conSector;
    private final InputButton btnBack;
    private final InputButton btnConfirm;
    
    private JFrame parentFrame;
    private JPanel mainPanel;
    
    public Null2() {
        setLayout(new BorderLayout());

        // Inicializar componentes
        conUsuari = new ContainerText("Usuari", 200, true);
        conContrasenya = new ContainerText("Contrasenya", 200, false);
        conRol = new ContainerDropDawn("Rol", 200, new  String[]{ "Administrador", "Usuario" });
        conCIF = new ContainerText("CIF/NIF", 200, true);
        conEmpresa = new ContainerText("Empresa", 200, true);
        conSector = new ContainerText("Sector", 200, true);

        // Inicializar botones
        btnBack = new InputButton("Atrás", false);
        btnConfirm = new InputButton("Crear usuario", true);

        // Ajustar el ancho de los botones
        btnBack.getButton().setPreferredSize(new Dimension(200, btnBack.getButton().getPreferredSize().height));
        btnConfirm.getButton().setPreferredSize(new Dimension(200, btnConfirm.getButton().getPreferredSize().height));

        // Sidebar
        PanelSidebar sidebar = new PanelSidebar();
        add(sidebar.getPanel(), BorderLayout.WEST);

        // Panel principal
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Palette.c3);
        mainPanel.setBorder(new EmptyBorder(Sizes.x4, Sizes.x3, Sizes.x4, Sizes.x3));

        // Configurar layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, Sizes.x1, 0);

        // Aadir componentes
        gbc.gridy = 1;
        mainPanel.add(conUsuari, gbc);
        
        gbc.gridy = 2;
        mainPanel.add(conContrasenya, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(conRol, gbc);
        
        gbc.gridy = 4;
        mainPanel.add(conCIF, gbc);
        
        gbc.gridy = 5;
        mainPanel.add(conEmpresa, gbc);
        
        gbc.gridy = 6;
        mainPanel.add(conSector, gbc);
        
        gbc.gridy = 7;
        mainPanel.add(btnBack, gbc);
        
        gbc.gridy = 8;
        mainPanel.add(btnConfirm, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Aadir ActionListeners
        btnBack.getButton().addActionListener(this);
        btnConfirm.getButton().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack.getButton()) {
            // Cambiar a FrameD1
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Null2.this);
            frame.getContentPane().removeAll();
            frame.add(new FrameD2()); // Regresar a FrameD2
            frame.revalidate();
            frame.repaint();
        } else if (e.getSource() == btnConfirm.getButton()) {
            // Aqu puedes agregar la lgica para guardar el usuario
            saveUser();
        }
    }

    private void saveUser() {
        try {
            // Imprimir los valores de los campos para depuracin
            System.out.println("Usuario: " + conUsuari.getText());
            System.out.println("Contrasea: " + conContrasenya.getText());
            System.out.println("Rol: " + conRol.getSelectedItem());
            System.out.println("CIF: " + conCIF.getText());
            System.out.println("Empresa: " + conEmpresa.getText());
            System.out.println("Sector: " + conSector.getText());

            // Validar que los campos no estn vacos
            if (conUsuari.getText().trim().isEmpty() || 
                conContrasenya.getText().trim().isEmpty() || 
                conRol.getSelectedItem() == null || 
                conCIF.getText().trim().isEmpty() || 
                conEmpresa.getText().trim().isEmpty() || 
                conSector.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Todos los campos son requeridos");
                return; // Salir si hay campos vacos
            }

            // Obtener los valores de los campos
            String usuario = conUsuari.getText();
            String contrasenya = conContrasenya.getText();
            String rol = (String) conRol.getSelectedItem();
            String cif = conCIF.getText();
            String empresa = conEmpresa.getText();
            String sector = conSector.getText();

            // Guardar en la base de datos
            DatabaseQueries db = new DatabaseQueries();
            boolean userInserted = db.insertUser(usuario, contrasenya, rol, cif, empresa, sector);

            if (userInserted) {
                JOptionPane.showMessageDialog(this, "Usuario guardado correctamente");
                // Aqu puedes limpiar los campos o realizar otras acciones
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el usuario");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al procesar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}