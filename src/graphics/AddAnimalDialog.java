package graphics;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import animals.*;
import mobility.Point;

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
    private static AddAnimalDialog dialog;
    private static Animal_fields field;
    private static SelectAnimal select;
    private String name,col; // common

    private double changing_field; // giraffe and elephant
    private int age,size; // turtle
    private Point location; // location
    private JButton create_animal = new JButton("Create");

    /**
     * makeInstance - singleton pattern - checks if theres an instance of the same class.
     *  if there is no instance creates a new instance.
     * @return -returns instance
     */
    public static AddAnimalDialog makeInstance(){
        if(dialog != null)
            return dialog;
        dialog = new AddAnimalDialog();
        return dialog;
    }
    /**
     * AddAnimalDialog ctor, Initiates a dialog, adds 2 Panels and 1 Button.
     */
    private AddAnimalDialog() {
        this.setTitle("Add animal");
        this.setLayout(new BorderLayout());

        create_animal.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                buildFields();
                Animal animalToAdd=null;
                switch (select.animal_choice.getSelectedItem().toString()) {
                    case "Lion" -> animalToAdd = new Lion(name, location, col, size);
                    case "Bear" -> animalToAdd = new Bear(name, location, col, size);
                    case "Giraffe" -> animalToAdd = new Giraffe(name, location, col, size, changing_field);
                    case "Turtle" -> animalToAdd = new Turtle(name, location, col, size, age);
                    case "Elephant" -> animalToAdd = new Elephant(name, location, col, size, changing_field);

                }
                if(animalToAdd != null) {
                    ZooPanel.addAnimal(animalToAdd);
                }
            }
        });
        field = new Animal_fields();
        select = new SelectAnimal();
        this.add(select, BorderLayout.CENTER); // adding selection panel - contains photo.
        this.add(field, BorderLayout.EAST); // adding field panel
        this.add(create_animal,BorderLayout.SOUTH);// adding Create button.
        field.setVisible(false);
        this.setSize(400, 400);
        this.addWindowListener(new WindowAdapter() {
            @Override
            /**
             * releases resources after AddAnimalDialog was closed.
             */
            public void windowClosed(WindowEvent e) {
                dialog = null;
                field = null;
                select = null;

            }
        });
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setModal(true);// focuses on Dialog.
        this.setVisible(true);


    }

    /**
     * buildFields - extracts data from animal fields
     *
     */
    private void buildFields(){
            name = field.name_f.getText();
        try {
            int x = Integer.parseInt(field.location_fx.getText());
            int y = Integer.parseInt(field.location_fy.getText());
            location = new Point(x,y);
        }
        catch(NumberFormatException e){
            System.out.println("Point set to default");
            location = new Point(-1,-1);
        }

            if(field.changing_f.getText().equals("Age"))
                try {
                    changing_field = Integer.parseInt(field.changing_f.getText());
                }
                catch(NumberFormatException e){
                    System.out.println(field.changing_f.getName() +" Set to default");
                    changing_field = -1; // setting age to out of range.
        }
            else
                try {
                    changing_field = Double.parseDouble(field.changing_f.getText());
                }
                catch(NumberFormatException e){
                    System.out.println(field.changing_f.getName() + " Set to default ");
                    changing_field = -1; // setting changing field to out of range
                }
        try {
            size = Integer.parseInt(field.size_f.getText());
        }
        catch(NumberFormatException e){
            System.out.println(field.size.getName() +" Set to default");
            size = -1; // setting age to out of range.
        }
    }

    private static class Animal_fields extends JPanel {
        private final  JLabel name = new JLabel("Name : ");
        private final  JTextField name_f = new JTextField();
        private final  JLabel location_x = new JLabel("X-axis location : ");
        private final  JTextField location_fx = new JTextField();
        private final  JLabel location_y = new JLabel("Y-axis location : ");
        private final  JTextField location_fy = new JTextField();
        private final  JLabel size = new JLabel("Size : ");
        private final  JTextField size_f = new JTextField();
        private final  JLabel vertical_speed = new JLabel("Vertical speed : ");
        private final  JTextField vertical_f = new JTextField();
        private final  JLabel horizontal_speed = new JLabel("Horizontal speed : ");
        private final  JTextField horizontal_f = new JTextField();
        private final  JLabel color = new JLabel("Color");
        private final  JTextField color_f = new JTextField();
        private final  JLabel changing_param = new JLabel(); // field that varies from animal to animal
        private final  JTextField changing_f = new JTextField();
        private final  String[] fields = {"", "", "Neck length", "Age", "Trunk length"};

        public Animal_fields() {
            this.setLayout(new GridLayout(0, 2));
            this.add(name);
            this.add(name_f);
            this.add(location_x);
            this.add(location_fx);
            this.add(location_y);
            this.add(location_fy);
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

        private final String[][] animals_choices = {{"Lion", "Lion.png"}, {"Bear", "Bear.png"}, {"Giraffe", "Giraffe.png"}, {"Turtle", "Turtle.png"}, {"Elephant", "Elephant.png"}};
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
            try {
                img = ImageIO.read((new File(System.getProperty("user.dir") +"\\src\\photos\\" + animal_choice.getSelectedItem() + ".png")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            field.changing_f.setVisible(!field.fields[index].equals("")); // if not lion changing field is visible.
            field.changing_param.setText(field.fields[index]);
            field.setVisible(true);
            this.repaint();

        }


    }
}