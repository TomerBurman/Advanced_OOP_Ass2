package graphics;
import animals.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AddAnimalDialog extends JDialog {
    public static int num_of_Animals = 0;

    private enum Animals_choices{
        LION("Lion", "Lion.jpg"),
        BEAR("Bear","Bear.jpg"),
        GIRAFFE("Giraffe","Giraffe.jpg"),
        TURTLE("Turtle","Turtle.jpg"),
        ELEPHANT("Elephant","Elephant.jpg");

        private final String text;
        private final ImageIcon img;
        Animals_choices(String name,String imgName){
            this.text = name;
            this.img = new ImageIcon(Objects.requireNonNull(AddAnimalDialog.class.getResource(imgName)));

        }
        public ImageIcon getImage(){return img;}

    }
    AddAnimalDialog(){
        this.setTitle("Add animal");

    }
    private class SelectAnimal extends JPanel implements ActionListener {
        private final JComboBox<Animals_choices> animal_choice; // combo list of enums
        private final JLabel animal_label;
        SelectAnimal() {
            this.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Select animal"), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
            this.setLayout(new BorderLayout());
            animal_choice = new JComboBox<>(Animals_choices.values()); // All enum constants.
            animal_choice.setSelectedIndex(0); // setting Lion to default
            animal_choice.addActionListener(this);
            animal_label = new JLabel();
            animal_label.setHorizontalAlignment(JLabel.CENTER);
            animal_label.setVerticalAlignment(JLabel.CENTER);
            this.add(animal_choice, BorderLayout.CENTER);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            animal_label.setIcon(animal_choice.getItemAt(animal_choice.getSelectedIndex()).getImage());
        }

    }
