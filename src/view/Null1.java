package view;

import resources.Palette;
import resources.Sizes;
import view.components.*;
import service.DatabaseQueries;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Null1 extends JPanel implements ActionListener {
    private final ContainerDropDawn conType;
    private final ContainerText conName;
    private final ContainerText conPrice;
    private final CheckBox boxColor;
    private final InputButton btnArchive;
    private final InputButton btnBack;
    private final InputButton btnConfirm;
    private final InputButton btnFlayer;
    private final InputButton btnPancarta;
    
    // Variables para Web
    private ContainerText conNombreWeb;
    private ContainerText conUrlWeb;
    private ContainerText conPrecioPequeno;
    private ContainerText conPrecioMediano;
    private ContainerText conPrecioGrande;
    
    // Variables para Flayer
    private ContainerText conCP;
    private ContainerText conPoblacion;
    private ContainerText conProvincia;
    private ContainerText conPrecioFlayer;
     
    // Variables para Pancarta
    private ContainerText conDescripcion;
    private ContainerText conCoordenadas;
    private ContainerText conPrecioPancarta;
    
    private JPanel mainPanel;
    private File selectedImage = null;
    
    // Variables para los resultados
    private JLabel lblPrecioMensual; // Label para mostrar el precio mensual pequeño
    private JLabel lblPrecioMensualMediano; // Label para mostrar el precio mensual mediano
    private JLabel lblPrecioMensualGrande; // Label para mostrar el precio mensual grande
    
    public Null1() {
        setLayout(new BorderLayout());
        
        // Añadir título
        JLabel titleLabel = new JLabel("Añadir Servicio", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Establecer fuente y tamaño
        add(titleLabel, BorderLayout.NORTH); // Añadir el título al panel
        
        // Asegúrate de que el panel principal se añada después del título
        mainPanel = new JPanel(new GridBagLayout());
        
        // Inicializar componentes
        conType = new ContainerDropDawn("Tipo", 200, new String[]{"Web", "Flayer", "Pancarta"});
        conName = new ContainerText("Nombre", 200, true);
        conPrice = new ContainerText("Precio", 200, true);
        boxColor = new CheckBox("Color", 200);
        btnArchive = new InputButton("Subir imagen", false);
        btnBack = new InputButton("Atrás", false);
        btnConfirm = new InputButton("Crear servicio", true);
        btnFlayer = new InputButton("Guardar flayer", false);
        btnPancarta = new InputButton("Crear pancarta", true);

        // Ajustar el ancho de los botones
        btnBack.getButton().setPreferredSize(new Dimension(200, btnBack.getButton().getPreferredSize().height));
        btnConfirm.getButton().setPreferredSize(new Dimension(200, btnConfirm.getButton().getPreferredSize().height));
        btnFlayer.getButton().setPreferredSize(new Dimension(200, btnFlayer.getButton().getPreferredSize().height));
        btnPancarta.getButton().setPreferredSize(new Dimension(200, btnPancarta.getButton().getPreferredSize().height));

        // Agregar ActionListener al botón "Atrás"
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(Null1.this);
                frame.getContentPane().removeAll();
                frame.add(new FrameD1()); // Regresar a FrameD1
                frame.revalidate();
                frame.repaint();
            }
        });

        // Agregar el botón al panel
        add(btnBack, BorderLayout.NORTH);
        
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

       
        
        // Añadir selector de tipo
        gbc.gridy = 2;
        mainPanel.add(conType, gbc);

        add(mainPanel, BorderLayout.CENTER);

        // Añadir ActionListeners
        btnBack.getButton().addActionListener(this);
        btnArchive.getButton().addActionListener(this);
        btnConfirm.getButton().addActionListener(this);
        btnFlayer.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFlayer(); // Llama al método para guardar el flayer
            }
        });
        btnPancarta.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePancarta(); // Llama al método para guardar la pancarta
            }
        });
        
        // Configurar el listener del tipo
        setupTypeListener();

        // Agregar ActionListener al botón "Crear Servicio"
        btnConfirm.getButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveWeb(); 
                
                // Llama al método para guardar la web
            }
        });
    }

    private void setupTypeListener() {
        conType.comboBox.addActionListener(e -> {
            String tipo = (String) conType.comboBox.getSelectedItem();
            
            // Limpiar todos los componentes existentes
            mainPanel.removeAll();
            
            // Volver a añadir el selector de tipo
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.insets = new Insets(0, 0, Sizes.x1, 0);
            mainPanel.add(conType, gbc);

            if ("Web".equals(tipo)) {
                handleWebType(gbc);
            } else if ("Flayer".equals(tipo)) {
                handleFlayerType(gbc);
            } else if ("Pancarta".equals(tipo)) {
                handlePancartaType(gbc);
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });

        // Trigger inicial
        conType.comboBox.setSelectedItem("Web");
    }	

    private void handleWebType(GridBagConstraints gbc) {
        // Crear campos específicos para Web
        conNombreWeb = new ContainerText("Nombre de la Web", 200, true);
        conUrlWeb = new ContainerText("URL", 200, true);
        conPrecioPequeno = new ContainerText("Precio Pequeño/Diario", 200, true);
        lblPrecioMensual = new JLabel("Precio Mensual: 0"); // Label para precio mensual pequeño
        conPrecioMediano = new ContainerText("Precio Mediano/Diario", 200, true);
        lblPrecioMensualMediano = new JLabel("Precio Mensual: 0"); // Label para precio mensual mediano
        conPrecioGrande = new ContainerText("Precio Grande/Diario", 200, true);
        lblPrecioMensualGrande = new JLabel("Precio Mensual: 0"); // Label para precio mensual grande
        
        // Agregar DocumentListener para multiplicar el precio pequeño por 30
        conPrecioPequeno.getTextField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePrice();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePrice();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePrice();
            }

            private void updatePrice() {
                try {
                    String text = conPrecioPequeno.getText().replace(",", ".");
                    if (!text.isEmpty()) {
                        double precioPequeno = Double.parseDouble(text);
                        double precioMensual = precioPequeno * 30;
                        lblPrecioMensual.setText("Precio Mensual: " + precioMensual); // Actualiza el label
                    } else {
                        lblPrecioMensual.setText("Precio Mensual: 0"); // Limpia el label si está vacío
                    }
                } catch (NumberFormatException e) {
                    lblPrecioMensual.setText("Precio Mensual: 0"); // Limpia el label si hay un error
                }
            }
        });

        // Agregar DocumentListener para multiplicar el precio mediano por 30
        conPrecioMediano.getTextField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePriceMediano();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePriceMediano();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePriceMediano();
            }

            private void updatePriceMediano() {
                try {
                    String text = conPrecioMediano.getText().replace(",", ".");
                    if (!text.isEmpty()) {
                        double precioMediano = Double.parseDouble(text);
                        double precioMensualMediano = precioMediano * 30;
                        lblPrecioMensualMediano.setText("Precio Mensual: " + precioMensualMediano); // Actualiza el label
                    } else {
                        lblPrecioMensualMediano.setText("Precio Mensual: 0"); // Limpia el label si está vacío
                    }
                } catch (NumberFormatException e) {
                    lblPrecioMensualMediano.setText("Precio Mensual: 0"); // Limpia el label si hay un error
                }
            }
        });

        // Agregar DocumentListener para multiplicar el precio grande por 30
        conPrecioGrande.getTextField().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePriceGrande();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePriceGrande();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePriceGrande();
            }

            private void updatePriceGrande() {
                try {
                    String text = conPrecioGrande.getText().replace(",", ".");
                    if (!text.isEmpty()) {
                        double precioGrande = Double.parseDouble(text);
                        double precioMensualGrande = precioGrande * 30;
                        lblPrecioMensualGrande.setText("Precio Mensual: " + precioMensualGrande); // Actualiza el label
                    } else {
                        lblPrecioMensualGrande.setText("Precio Mensual: 0"); // Limpia el label si está vacío
                    }
                } catch (NumberFormatException e) {
                    lblPrecioMensualGrande.setText("Precio Mensual: 0"); // Limpia el label si hay un error
                }
            }
        });
        
        // Añadir componentes
        gbc.gridy = 2;
        mainPanel.add(conNombreWeb, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(conUrlWeb, gbc);
        
        gbc.gridy = 4;
        mainPanel.add(conPrecioPequeno, gbc);
        
        gbc.gridy = 5;
        mainPanel.add(lblPrecioMensual, gbc); // Añadir el label para precio mensual pequeño
        
        gbc.gridy = 6;
        mainPanel.add(conPrecioMediano, gbc);
        
        gbc.gridy = 7;
        mainPanel.add(lblPrecioMensualMediano, gbc); // Añadir el label para precio mensual mediano
        
        gbc.gridy = 8;
        mainPanel.add(conPrecioGrande, gbc);
        
        gbc.gridy = 9;
        mainPanel.add(lblPrecioMensualGrande, gbc); // Añadir el label para precio mensual grande
        
        // Botones
        gbc.gridy = 10;
        mainPanel.add(btnBack, gbc);
        
        gbc.gridy = 11;
        mainPanel.add(btnConfirm, gbc);
    }

    private void handleFlayerType(GridBagConstraints gbc) {
        // Crear campos específicos para Flayer
        conCP = new ContainerText("Codigo Postal", 200, true);
        conPoblacion = new ContainerText("Población", 200, true);
        conProvincia = new ContainerText("Provincia", 200, true);
        conPrecioFlayer = new ContainerText("Precio", 200, true);
        
        // Añadir componentes
        gbc.gridy = 2;
        mainPanel.add(conCP, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(conPoblacion, gbc);
        
        gbc.gridy = 4;
        mainPanel.add(conProvincia, gbc);
        
        gbc.gridy = 5;
        mainPanel.add(conPrecioFlayer, gbc);
        
        // Botones
        gbc.gridy = 6;
        mainPanel.add(btnBack, gbc);
        
        gbc.gridy = 7;
        mainPanel.add(btnConfirm, gbc);
    }

    private void handlePancartaType(GridBagConstraints gbc) {
        // Crear campos específicos para Pancarta
        conDescripcion = new ContainerText("Descripción", 200, true);
        conCoordenadas = new ContainerText("Coordenadas", 200, true);
        conPrecioPancarta = new ContainerText("Precio", 200, true);
        
        // Añadir componentes
        gbc.gridy = 2;
        mainPanel.add(conDescripcion, gbc);
        
        gbc.gridy = 3;
        mainPanel.add(conCoordenadas, gbc);
        
        gbc.gridy = 4;
        mainPanel.add(conPrecioPancarta, gbc);
        
        // Botones
        gbc.gridy = 5;
        mainPanel.add(btnBack, gbc);
        
        gbc.gridy = 6;
        mainPanel.add(btnConfirm, gbc);
    }

    private void handleFileSelection() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".jpg") ||
                       f.getName().toLowerCase().endsWith(".jpeg") ||
                       f.getName().toLowerCase().endsWith(".png") ||
                       f.isDirectory();
            }

            public String getDescription() {
                return "Image files (*.jpg, *.jpeg, *.png)";
            }
        });

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImage = fileChooser.getSelectedFile();
            btnArchive.getButton().setText(selectedImage.getName());
        }
    }

    private void saveWeb() {
        try {
            // Validar que los campos no están vacíos
            if (conNombreWeb.getText().trim().isEmpty() || 
                conUrlWeb.getText().trim().isEmpty() || 
                conPrecioPequeno.getText().trim().isEmpty() || 
                conPrecioMediano.getText().trim().isEmpty() || 
                conPrecioGrande.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Todos los campos son requeridos");
                return;
            }

            // Validar y convertir precios
            double precioPequeno;
            double precioMediano;
            double precioGrande;

            try {
                precioPequeno = Double.parseDouble(conPrecioPequeno.getText().replace(",", "."));
                precioMediano = Double.parseDouble(conPrecioMediano.getText().replace(",", "."));
                precioGrande = Double.parseDouble(conPrecioGrande.getText().replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: Los precios deben ser números válidos");
                return;
            }

            // Obtener un nuevo uniqueId
            int uniqueId = DatabaseQueries.getNewUniqueId(); // Llama al método para obtener el nuevo uniqueId

            // Guardar en la base de datos
            DatabaseQueries db = new DatabaseQueries();
            boolean success = db.insertWeb(conNombreWeb.getText(), conUrlWeb.getText(), 
                                           precioPequeno, precioMediano, precioGrande, uniqueId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Web guardada correctamente");
                // Aquí puedes limpiar los campos o realizar otras acciones
              
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar la web");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al procesar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void saveFlayer() {
        try {
            // Validar que los campos no están vacíos
            if (conCP.getText().trim().isEmpty() || 
                conPoblacion.getText().trim().isEmpty() || 
                conProvincia.getText().trim().isEmpty() || 
                conPrecioFlayer.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Error: Todos los campos son requeridos");
                return;
            }

            // Validar y convertir código postal
            int cp;
            try {
                cp = Integer.parseInt(conCP.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: El código postal debe ser un número válido");
                return;
            }

            // Validar y convertir precio
            double precioFlayer;
            try {
                precioFlayer = Double.parseDouble(conPrecioFlayer.getText().replace(",", "."));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: El precio debe ser un número válido");
                return;
            }

            // Obtener un nuevo uniqueId
            int uniqueId = DatabaseQueries.getNewUniqueIdForFlayer();

            // Guardar en la base de datos
            DatabaseQueries db = new DatabaseQueries();
            boolean success = db.insertFlayer(conPoblacion.getText(), conProvincia.getText(), precioFlayer, uniqueId);

            if (success) {
                JOptionPane.showMessageDialog(this, "Flayer guardado correctamente");
                // Limpiar los campos o realizar otras acciones
                conCP.setText("");
                conPoblacion.setText("");
                conProvincia.setText("");
                conPrecioFlayer.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el flayer");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al procesar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void savePancarta() {
        try {
            // Imprimir los valores de los campos para depuración
            System.out.println("Descripción: " + conDescripcion.getText());
            System.out.println("Coordenadas: " + conCoordenadas.getText());
            System.out.println("Precio: " + conPrecioPancarta.getText());

            // Validar que los campos no están vacíos
            if (conDescripcion.getText().trim().isEmpty() || 
                conCoordenadas.getText().trim().isEmpty() || 
                conPrecioPancarta.getText().trim().isEmpty()) {
                System.out.println("Error: Todos los campos son requeridos");
                JOptionPane.showMessageDialog(this, "Error: Todos los campos son requeridos");
                return; // Salir si hay campos vacíos
            }
            
            // Validar y convertir el precio
            double precio;
            try {
                precio = Double.parseDouble(conPrecioPancarta.getText().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Error: El precio debe ser un número válido");
                JOptionPane.showMessageDialog(this, "Error: El precio debe ser un número válido");
                return; // Salir si el precio no es válido
            }
            
            // Obtener los valores de descripción y coordenadas
            String descripcion = conDescripcion.getText();
            String coordenadas = conCoordenadas.getText();

            // Obtener un nuevo uniqueId
            int uniqueId = DatabaseQueries.getNewUniqueId(); // Llama al método para obtener el nuevo uniqueId

            // Guardar en la base de datos
            DatabaseQueries db = new DatabaseQueries();
            boolean success = db.insertPancarta(descripcion, coordenadas, precio);
            
            if (success) {
                System.out.println("Pancarta guardada correctamente");
                JOptionPane.showMessageDialog(this, "Pancarta guardada correctamente");
                // Aquí puedes limpiar los campos o realizar otras acciones
            } else {
                System.out.println("Error al guardar la pancarta");
                JOptionPane.showMessageDialog(this, "Error al guardar la pancarta");
            }
            
        } catch (Exception e) {
            System.out.println("Error al procesar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnArchive.getButton()) {
            handleFileSelection();
        } else if (e.getSource() == btnConfirm.getButton()) {
            String tipo = (String) conType.comboBox.getSelectedItem();
            switch (tipo) {
                case "Web" -> saveWeb();
                case "Flayer" -> saveFlayer();
                case "Pancarta" -> savePancarta();
            }
        }
    }
}