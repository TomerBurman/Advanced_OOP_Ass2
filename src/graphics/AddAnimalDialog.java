package graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**AddAnimalDialog - JDialog window, creates a dialog window to create an Animal
 * num_of_Animals - static indicator field to know how many animals are created. (10 animals maximum.)
 * contains 2 panels : Animal_fields and SelectAnimal.
 * contains JButton for creation.
 *
 * @version : 1
 * @author : Tomer Burman, Oran Bourak
 */
public class AddAnimalDialog extends JDialog {
    /**
     * num_of_Animals - number of animals in the system.
     * SelectAnimal - Select panel for animals, displays animal selection with combo box and displays picture.
     * Animal_fields - Fields panel for animal fields, contains 6 JLabels and 6 JTexts.
     * create_animal - Button that creates an animal
     */
    private final static Animal_fields field = new Animal_fields();
    private final static SelectAnimal select = new SelectAnimal();
    private JButton create_animal = new JButton("Create");

    /**
     * AddAnimalDialog ctor, Initiates a dialog, adds 2 Panels and 1 Button.
     */
    public AddAnimalDialog() {
        this.setTitle("Add animal");
        this.setLayout(new BorderLayout());
        create_animal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.add(select, BorderLayout.CENTER); // adding selection panel - contains photo.
        this.add(field, BorderLayout.EAST); // adding field panel
        this.add(create_animal,BorderLayout.SOUTH);// adding Create button.
        field.setVisible(false);
        this.setVisible(true);
        this.setSize(400, 400);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);


    }


    private static class Animal_fields extends JPanel {
        private final static JLabel name = new JLabel("Name : ");
        private final static JTextField name_f = new JTextField();
        private final static JLabel location = new JLabel("Location : ");
        private final static JTextField location_f = new JTextField();
        private final static JLabel size = new JLabel("Size : ");
        private final static JTextField size_f = new JTextField();
        private final static JLabel vertical_speed = new JLabel("Vertical speed : ");
        private final static JTextField vertical_f = new JTextField();
        private final static JLabel horizontal_speed = new JLabel("Horizontal speed : ");
        private final static JTextField horizontal_f = new JTextField();
        private final static JLabel color = new JLabel("Color");
        private final static JTextField color_f = new JTextField();
        private final static JLabel changing_param = new JLabel(); // field that varies from animal to animal
        private final static JTextField changing_f = new JTextField();
        private final static String[] fields = {"", "", "Neck length", "Age", "Trunk length"};

        public Animal_fields() {
            this.setLayout(new GridLayout(0, 2));
            this.add(name);
            this.add(name_f);
            this.add(location);
            this.add(location_f);
            this.add(color);
            this.add(color_f);
            this.add(size);
            this.add(size_f);
            this.add(vertical_speed);
            this.add(vertical_f);
            this.add(horizontal_speed);
            this.add(horizontal_f);
            this.add(changing_param);
            this.add(changing_f);
            vertical_f.setEnabled(false);
            horizontal_f.setEnabled(false);


        }
    }


    private static class SelectAnimal extends JPanel implements ActionListener {
        private final JComboBox<String> animal_choice;

        private String[][] animals_choices = {{"Lion", "Lion.png"}, {"Bear", "Bear.png"}, {"Giraffe", "Giraffe.png"}, {"Turtle", "Turtle.png"}, {"Elephant", "Elephant.png"}};
        private Image img;

        SelectAnimal() {
            this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Select animal"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            this.setLayout(new BorderLayout());
            animal_choice = new JComboBox<String>(); // All enum constants.
            for (String[] name : animals_choices) // taking animal names.
                animal_choice.addItem(name[0]);
            animal_choice.addActionListener(this);
            this.add(animal_choice, BorderLayout.NORTH);
        }


        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D gr = (Graphics2D) g;
            gr.drawImage(img, 20, 50, this.getWidth() - 30, this.getHeight() - 60, null);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = animal_choice.getSelectedIndex();
            img = new ImageIcon(Objects.requireNonNull(getClass().getResource("photos\\" +animals_choices[index][1]))).getImage(); // getting ImageIcon
            Animal_fields.changing_f.setVisible(!Animal_fields.fields[index].equals("")); // if not lion changing field is visible.
            Animal_fields.changing_param.setText(Animal_fields.fields[index]);
            field.setVisible(true);
            this.repaint();

        }


    }
}