package graphics;

import animals.Animal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ZooPanel extends JPanel implements Runnable {
    private static int num_of_Animals = 0; // number of animals in zoo
    private static final int max_animals = 10;
    private static JDialog dialog;
    private static ArrayList<Animal> animal_list = new ArrayList<>();
    private static drawPanel draw_panel;
    private ButtonPanel button_panel;


    public ZooPanel(){
        draw_panel = new drawPanel();
        this.setLayout(new BorderLayout());
        button_panel = new ButtonPanel();
        this.add(button_panel,BorderLayout.SOUTH); // buttonpanel
        this.add(draw_panel,BorderLayout.CENTER); // painting panel

    }
    public static void manageZoo(){
       if(isChanged()) {
           draw_panel.repaint();
       }
    }

    public static boolean isChanged(){
        for(Animal animal : animal_list)
            if(animal.getChanges()) {
                animal.setChanges(false);
                return true;
            }
        return false;
    }


    public static drawPanel getDraw_panel(){
        return draw_panel;
    }

    public static void setBack(Color c){
        draw_panel.setBackground(c);
    }
   public static ArrayList<Animal> getAnimalList(){return animal_list;}

    private class ButtonPanel extends JPanel{
        private JButton add_Animal = new JButton("Add Animal"); //getcontent
        private JButton move_Animal = new JButton("Move Animal");
        private JButton clear = new JButton("Clear");
        private JButton food = new JButton("Food");
        private JButton info = new JButton("Info");
        private JButton exit = new JButton("Exit");

        ButtonPanel(){
            this.setBackground(new Color(0,0,255));
            animalButton();
            GridLayout lay = new GridLayout(1,0);
            this.setLayout(lay);
            lay.setHgap(100);
            this.add(add_Animal);
            this.add(move_Animal);
            this.add(clear);
            this.add(food);
            this.add(info);
            this.add(exit);


        }
        boolean animalButton(){
            this.add_Animal.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                        if(num_of_Animals < max_animals)
                            dialog = AddAnimalDialog.makeInstance();
                        else {
                            JOptionPane.showMessageDialog(button_panel,"Can not add more then 10 animals. ","Error message",JOptionPane.WARNING_MESSAGE
                            );

                        }
                }
            });
            this.move_Animal.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(num_of_Animals > 0 ){
                        dialog = new MoveAnimalDialog();
                    }
                    else
                        JOptionPane.showMessageDialog(dialog,"No animals available","Error message",JOptionPane.WARNING_MESSAGE);

                }
            });
            this.exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(new JFrame(), "Goodbye");
                    System.exit(0);
                }
            });
            return true;
        }


    }
    private static class drawPanel extends JPanel {

        public drawPanel(){
            this.setPreferredSize(new Dimension(800,600));

        }
        /**
         * paintComponent
         * @param g
         */
        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            for(Animal animal : animal_list)
                animal.drawObject(g);
        }

    }


    @Override
    public void run() {
        //TODO
    }



    public static void addAnimal(Animal animal){
        if(num_of_Animals < max_animals) {
            animal_list.add(animal);
            draw_panel.repaint();
            num_of_Animals++;
        }
        else{
            JOptionPane.showMessageDialog(dialog,"You can not make more then 10 animals at once. ","Error message",JOptionPane.WARNING_MESSAGE
            );
        }
    }


}
