package graphics;

import animals.Animal;
import mobility.Point;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.HashMap;

public class MoveAnimalDialog extends JDialog {
    private JComboBox<Animal> animalsList;
    HashMap<String,Animal> item;
    public MoveAnimalDialog(){
        this.setLayout(new BorderLayout());
        this.add(new AnimalSelection(),BorderLayout.NORTH);
        this.add(new MovingFields(),BorderLayout.CENTER);
        this.setSize(new Dimension(400,400));
        this.setModal(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private class AnimalSelection extends JPanel{
        AnimalSelection(){
            this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
            this.buildAnimalList();
            this.setAlignmentX(200);
            this.add(animalsList);
            this.setVisible(true);
        }
        /**
         * Builds animals JComboBox that contains JLabels with icons of animals
         */
//        private void buildAnimalList() {
//            for (Animal animal : ZooPanel.getAnimalList()) {
//                JLabel animal_label = new JLabel(animal.getAnimalName());
//                animal_label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\photos\\" + animal.getClass().getSimpleName() + "png"));
//                animalsList.addItem(animal_label);
//            }
//        }
        private void buildAnimalList() {
//            item = new HashMap<>();
            animalsList = new JComboBox<>();
//            JLabel label;
            for(Animal animal : ZooPanel.getAnimalList()) {
                animalsList.addItem(animal);
            }
        }

    }
    private class MovingFields extends JPanel{
        private final JLabel currentLocation;
        private final JLabel point;
        private final JLabel newLocation;
        private final JButton move;
        private JTextField x_Location,y_Location;
        private MovingFields(){
            GridBagConstraints gbc = new GridBagConstraints();
            this.setLayout(new GridBagLayout());
            currentLocation = new JLabel();
            point = new JLabel();
            move = new JButton("Move");
            move.setVisible(false);
            move.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Animal animal = (Animal)animalsList.getSelectedItem();
                    int x = -1, y = -1;
                    try{
                        x = Integer.parseInt(x_Location.getText());
                        y = Integer.parseInt(y_Location.getText());
                        Point p = new Point(x,y);
                        if(!Point.checkBoundaries(p)){
                            JOptionPane.showMessageDialog(new JPanel(),"Point is out of bound.","Error message",JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        animal.move(p);
                        animal.setChanges(true);
                        newLocation.setText("New location  ");
                        point.setText(animal.getLocation().toString());
                        ZooPanel.manageZoo();
                    }
                    catch(NumberFormatException exc){
                        JOptionPane.showMessageDialog(new JPanel(),"Point can not be converted.","Error message",JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                }
            });
            newLocation = new JLabel();
            x_Location = new JTextField("X coordinate");
            y_Location = new JTextField("Y coordinate");
            x_Location.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    x_Location.setText("");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(x_Location.getText().equals(""))
                        x_Location.setText("X coordinate");
                }
            });
            y_Location.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    y_Location.setText("");
                }

                @Override
                public void focusLost(FocusEvent e) {
                    if(y_Location.getText().equals(""))
                        y_Location.setText("Y coordinate");
                }
            });
            x_Location.setVisible(false);
            y_Location.setVisible(false);
            animalsList.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Animal animal = (Animal)animalsList.getSelectedItem();
                    assert animal != null;
                    currentLocation.setText("Current location  ");
                    newLocation.setText("New location  ");
                    point.setText(animal.getLocation().toString());
                    setVisible(true);
                    x_Location.setVisible(true);
                    y_Location.setVisible(true);
                    move.setVisible(true);

                }
            });
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = GridBagConstraints.WEST;
            this.add(currentLocation,gbc);
            gbc.gridx = 1;
            this.add(point,gbc);
            gbc.gridy = 1;
            gbc.gridx = 0;
            this.add(newLocation,gbc);
            gbc.gridx = 1;
            this.add(x_Location,gbc);
            gbc.gridx = 2;
            this.add(y_Location,gbc);
            gbc.gridy = 3;
            gbc.gridx = 1;
            this.add(move,gbc);



        }
    }

}
