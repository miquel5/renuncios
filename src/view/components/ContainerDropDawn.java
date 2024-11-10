package view.components;

import resources.Palette;
import resources.Sizes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContainerDropDawn extends JPanel
{
    public JComboBox<String> comboBox;
    private String type;

    public ContainerDropDawn(String placeholder, int width, String[] llista)
    {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(0, 0, 3, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setBackground(Palette.c3);

        // Label
        gbc.gridy = 0;
        JLabel label = new JLabel(placeholder);
        label.setForeground(Palette.c6);
        label.setOpaque(false);
        label.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));

        add(label, gbc);

        // ComboBox
        gbc.gridy = 1;
        comboBox = new JComboBox<>(llista);
        comboBox.setPreferredSize(new Dimension(width, comboBox.getPreferredSize().height));
        comboBox.setFont(new Font("Arial", Font.PLAIN, Sizes.x2));
        comboBox.setBackground(Palette.c4);
        comboBox.setForeground(Palette.c6);
        add(comboBox, gbc);

        // Canviar de color
        comboBox.setRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
            {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (isSelected)
                {
                    label.setBackground(Palette.c4);
                    label.setForeground(Palette.c7);
                } else
                {
                    label.setBackground(Palette.c3);
                    label.setForeground(Palette.c6);
                }

                return label;
            }
        });

        comboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                type = (String) comboBox.getSelectedItem();
            }
        });

        setPreferredSize(new Dimension(width, getPreferredSize().height));
    }

}
