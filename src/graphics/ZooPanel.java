package graphics;

import animals.Animal;
import food.EFoodType;
import food.IEdible;
import mobility.Point;

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
    private static ImageIcon backGround = null;
    private static IEdible food=null;


    public static void setFoodType(IEdible f){ food = f; }



    public ButtonPanel getButtonPanel(){return button_panel;}



    public ZooPanel(){
        draw_panel = new drawPanel();
        this.setLayout(new BorderLayout());
        button_panel = new ButtonPanel();
        this.add(button_panel,BorderLayout.SOUTH); // buttonpanel
        this.add(draw_panel,BorderLayout.CENTER); // painting panel

    }


    public static void manageZoo(){
        if(animal_list.size() != num_of_Animals ){ // in cases: 1) Clear animal button is pressed , 2)one of the animals has been eaten
            num_of_Animals = animal_list.size();
            draw_panel.repaint();
        }
       if(isChanged()) { // in case of movement
           draw_panel.repaint();
       }

       int i =0;
       Animal animal1 ,animal2;
        while (i < num_of_Animals){
          animal1 = animal_list.get(i);
          if(food != null && animal1.calcDistance(new Point(draw_panel.getWidth()/2,draw_panel.getHeight()/2)) <= animal1.getEAT_DISTANCE()){
              if(animal1.eat(food))
                  food = null;
          }
           for(int j =0; j < num_of_Animals;j++){
               animal2 = animal_list.get(j);
               if(animal1.getWeight() > animal2.getWeight()*2 && animal1.calcDistance(animal2.getLocation()) <= animal1.getEAT_DISTANCE())
                   if(animal1.eat(animal2)) {
                       animal_list.remove(j);
                       draw_panel.repaint();
                       i = 0;
                       System.out.println(animal1.getAnimalName() +" eat "+ animal2.getAnimalName());
                       break;
                   }
           }
           if(animal_list.size() != num_of_Animals)
               num_of_Animals--;
           else
               i++;
      }
        if(food != null)
            draw_panel.repaint();
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
        backGround = null;
        draw_panel.setBackground(c);
    }

    public static void setBack(String file){
        backGround = new ImageIcon(file);
        draw_panel.repaint();
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
            this.info.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(animal_list.size() != 0) {
                        infoTable info = new infoTable();
                        JTable infoTable = new JTable(info.getAnimalsInfo(), info.getCols());
                        JOptionPane.showMessageDialog(button_panel, new JScrollPane(infoTable));
                    }
                    else{
                        JOptionPane.showMessageDialog(button_panel,"No animals to show.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            this.clear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(animal_list.size() > 0) {
                        animal_list.clear();
                        manageZoo();
                    }
                    else
                        JOptionPane.showMessageDialog(button_panel,"Animals do not exist.","Error",JOptionPane.ERROR_MESSAGE);

                }
            });
            this.food.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    FoodDialog food = new FoodDialog();
                    manageZoo();
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
            if(backGround != null)
                g.drawImage(backGround.getImage(),0,0,draw_panel.getWidth(),draw_panel.getHeight(),null);
            if(food != null)
                ((IDrawable)food).drawObject(g);

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
            manageZoo();
        }
        else{
            JOptionPane.showMessageDialog(dialog,"You can not make more then 10 animals at once. ","Error message",JOptionPane.WARNING_MESSAGE
            );
        }
    }


}
