import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;

abstract class Animal {
    protected String name;
    protected String sound;
    protected String imageFile;

    public Animal(String name, String sound, String imageFile) {
        this.name = name;
        this.sound = sound;
        this.imageFile = imageFile;
    }

    public abstract String getInfo();

    public String getImageFile() {
        return imageFile;
    }
}
class Dog extends Animal {
    public Dog() {
        super("Dog", "🔊 Woof! Woof!", "dog.png");
    }

    @Override
    public String getInfo() {
        return "🐶 DOG\n\nFUN FACT: Dogs are loyal and friendly animals. They love playing fetch and wagging their tails 🐾.\n\nSOUND: " + sound.toUpperCase() + "!!!";
    }
}

class Cat extends Animal {
    public Cat() {
        super("Cat", "🔊 Meow~ Meow~", "cat.png");
    }

    @Override
    public String getInfo() {
        return "🐱 CAT\n\nFUN FACT: Cats are graceful and curious animals. They enjoy napping and chasing yarn 🧶.\n\nSOUND: " + sound.toUpperCase() + "!!!";
    }
}

class Cow extends Animal {
    public Cow() {
        super("Cow", "🔊 Moo~ Moo~", "cow.png");
    }

    @Override
    public String getInfo() {
        return "🐄 COW\n\nFUN FACT: Cows are gentle farm animals that give us milk 🥛. They love grazing on grass 🌾.\n\nSOUND: " + sound.toUpperCase() + "!!!";
    }
}

public class ReadTheirSounds extends JFrame {
    private JLabel imageLabel;
    private JTextArea infoArea;

    public ReadTheirSounds() {
        setTitle("Read Their Sound App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        UIManager.put("Label.font", new Font("Segoe UI Emoji", Font.BOLD, 20));
        UIManager.put("Button.font", new Font("Segoe UI Emoji", Font.BOLD, 20));
        UIManager.put("TextArea.font", new Font("Segoe UI Emoji", Font.PLAIN, 18));

        JLabel titleLabel = new JLabel("🐾 Read Their Sound 🔊", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI Emoji", Font.BOLD, 28));
        titleLabel.setBorder(new CompoundBorder(
            new LineBorder(Color.GRAY, 4, true),     
            new EmptyBorder(15, 10, 15, 10)               
        ));

        imageLabel = new JLabel("🐾 Select an animal!", JLabel.CENTER);
        imageLabel.setBorder(new LineBorder(Color.GRAY, 4, true));
        imageLabel.setBackground(Color.WHITE);
        imageLabel.setOpaque(true);

        infoArea = new JTextArea("Click an animal button to view its picture and sound!");
        infoArea.setEditable(false);
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);
        infoArea.setBorder(new LineBorder(new Color(200, 200, 200), 3, true));
        infoArea.setBackground(new Color(255, 250, 240));
        infoArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(infoArea);
        scrollPane.setPreferredSize(new Dimension(0, 180));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 20, 10));
        buttonPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        buttonPanel.setBackground(new Color(255, 245, 230));

        JButton dogButton = new JButton("🐶 Dog");
        JButton catButton = new JButton("🐱 Cat");
        JButton cowButton = new JButton("🐄 Cow");

        styleButton(dogButton);
        styleButton(catButton);
        styleButton(cowButton);

        Animal dog = new Dog();
        Animal cat = new Cat();
        Animal cow = new Cow();

        dogButton.addActionListener(_ -> showAnimal(dog));
        catButton.addActionListener(_ -> showAnimal(cat));
        cowButton.addActionListener(_ -> showAnimal(cow));

        buttonPanel.add(dogButton);
        buttonPanel.add(catButton);
        buttonPanel.add(cowButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(titleLabel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(new Color(255, 230, 200));
        button.setBorder(new LineBorder(new Color(150, 100, 50), 3, true));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(255, 210, 170));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 230, 200));
            }
        });
    }

    private void showAnimal(Animal animal) {
        infoArea.setText(animal.getInfo());
        ImageIcon icon = loadImage(animal.getImageFile());

        if (icon == null) {
            imageLabel.setIcon(null);
            imageLabel.setText("Image not found: " + animal.getImageFile());
        } else {
            Image scaled = icon.getImage().getScaledInstance(
                    imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
            imageLabel.setText("");
        }
    }

    private ImageIcon loadImage(String fileName) {
        File localFile = new File(fileName);
        if (localFile.exists()) return new ImageIcon(localFile.getAbsolutePath());

        File[] tryPaths = {
            new File("icons/" + fileName),
            new File("src/icons/" + fileName)
        };
        for (File f : tryPaths) {
            if (f.exists()) return new ImageIcon(f.getAbsolutePath());
        }

        URL imageURL = getClass().getResource("/" + fileName);
        if (imageURL != null) return new ImageIcon(imageURL);

        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ReadTheirSounds::new);
    }
}
