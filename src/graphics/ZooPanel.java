package graphics;

import animals.Animal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZooPanel extends JPanel implements Runnable {
    private static int num_of_Animals = 0; // number of animals in zoo
    private final int max_animals = 10;
    private JDialog add_dialog;

    private class ButtonPanel extends JPanel{
        private JButton add_Animal = new JButton("Add Animal"); //getcontent
        private JButton move_Animal = new JButton("Move Animal");
        private JButton clear = new JButton("Clear");
        private JButton food = new JButton("Food");
        private JButton info = new JButton("Info");
        private JButton exit = new JButton("Exit");

        ButtonPanel(){
            this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
            this.setBackground(new Color(0,0,255));
            this.setAlignmentX(10);


        }
        boolean animalButton(){
            this.add_Animal.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                        if(num_of_Animals < max_animals)
                            add_dialog = new AddAnimalDialog();
                        else {
                            add_dialog = new JDialog();
                            add_dialog.setTitle("Error");
                            add_dialog.add(new JLabel("You can not make more than 10 animals."));
                        }




                }
            });
            return true;
        }

    }




    @Override
    public void run() {
        //TODO
    }


}
