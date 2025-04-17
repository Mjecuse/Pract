import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameGUI extends JFrame {
    CardLayout cardLayout;
    JPanel mainPanel;

    public GameGUI() {
        setTitle("My RPG Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createTitleScreen(), "Title");
        mainPanel.add(createCharacterCreationScreen(), "CharacterCreation");

        add(mainPanel);
        cardLayout.show(mainPanel, "Title");
        setVisible(true);
    }

    private JPanel createTitleScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Final Quest", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.CENTER);

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> cardLayout.show(mainPanel, "CharacterCreation"));
        panel.add(startButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createCharacterCreationScreen() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nameField = new JTextField();
        String[] classes = {"Knight", "Mage", "Archer"};
        JComboBox<String> classBox = new JComboBox<>(classes);

        panel.add(new JLabel("Character Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Class:"));
        panel.add(classBox);

        panel.add(new JLabel()); // empty placeholder
        JButton createButton = new JButton("Create Character");

        createButton.addActionListener(e -> {
            String name = nameField.getText();
            String chosenClass = (String) classBox.getSelectedItem();
            JOptionPane.showMessageDialog(this, "Character Created!\nName: " + name + "\nClass: " + chosenClass);
        });

        panel.add(createButton);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameGUI::new);
    }
}