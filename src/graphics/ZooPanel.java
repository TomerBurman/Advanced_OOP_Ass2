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

/**
 * ZooPanel - extends JPanel implements Runnable.
 * built of 2 panels -> Button Panel at SOUTH and draw_panel at CENTER.
 * @author : Oran Bourak, Tomer Burman
 * @version :1
 */
public class ZooPanel extends JPanel implements Runnable {
    /**
     * num_of_Animals - number of animals in the zoo
     * max_animals - max animals allowed.
     * dialog - JDialog incase of AddAnimalDialog or MoveAnimalDialog
     * animal_list - array list of animals.
     * draw_panel - drawing panel at the Center of main panel
     * button_panel - button panel at the Bottom of main panel
     * backGround - incase of Image background.
     * food - food instance.
     *
     */
    private static int num_of_Animals = 0; // number of animals in zoo
    private static final int max_animals = 10;
    private static JDialog dialog;
    private static ArrayList<Animal> animal_list = new ArrayList<>();
    private static drawPanel draw_panel;
    private ButtonPanel button_panel;
    private static ImageIcon backGround = null;
    private static IEdible food=null;

    /**
     * setFoodType - sets the food to received food.
     * @param f - food received. (Lettuce,Cabbage or Meat)
     */
    public static void setFoodType(IEdible f){ food = f; }


    /**
     * ZooPanel ctor - sets layout to BorderLayout, initiates draw_panel and button_panel.
     * adds button panel to south and draw panel to center.
     */
    public ZooPanel(){
        draw_panel = new drawPanel();
        this.setLayout(new BorderLayout());
        button_panel = new ButtonPanel();
        this.add(button_panel,BorderLayout.SOUTH); // buttonpanel
        this.add(draw_panel,BorderLayout.CENTER); // painting panel

    }

    /**
     * manageZoo - keeps track on if there are  any changes in our zoo
     * if there are, handles the changes and calls draw_panel repaint.
     */
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
        while (i < num_of_Animals){// checks all animals with all animals, if one can eat the other.
          animal1 = animal_list.get(i);
          if(food != null && animal1.calcDistance(new Point(draw_panel.getWidth()/2,draw_panel.getHeight()/2)) <= animal1.getEAT_DISTANCE()){
              //in case of close food.
              if(animal1.eat(food))
                  //in case the animal is able to eat the food.
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
           if(animal_list.size() != num_of_Animals)// in case animal has been eaten.
               num_of_Animals--;
           else
               i++;
      }

        if(food != null)//if theres food
            draw_panel.repaint();
    }


    /**
     * isChanged - checks if any animal in animal list moved, if so returns true else returns false.
     * @return boolean
     */
    public static boolean isChanged(){
        for(Animal animal : animal_list)
            if(animal.getChanges()) {
                animal.setChanges(false);
                return true;
            }
        return false;
    }

    /**
     * getDraw_panel - returns draw_panel
     * @return drawPanel
     */
    public static drawPanel getDraw_panel(){
        return draw_panel;
    }

    /**
     * setBack - sets backGround to null, and then sets draw_panel backGround with received color
     * @param c background color wanted.
     */
    public static void setBack(Color c){
        backGround = null;
        draw_panel.setBackground(c);
    }

    /**
     * setBack - in case user wants to upload an image to the background, creating a new ImageIcon and calling draw_panel
     * repaint
     * @param file - file name
     */
    public static void setBack(String file){
        backGround = new ImageIcon(file);
        draw_panel.repaint();
    }

    /**
     * getAnimalList - returns animal_list
     * @return ArrayList<Animal>
     */
   public static ArrayList<Animal> getAnimalList(){return animal_list;}

    /**
     * ButtonPanel - extends JPanel, represents a Button panel that has
     * buttons with action listeners. - what the user can do with our system.
     * @author : Tomer Burman , Oran Bourak
     * @version : 1
     */
    private class ButtonPanel extends JPanel{
        /**
         * add_Animal - Add animal button
         * move_Animal - Move animal button
         * clear - Clear button.
         * food - Food button
         * info - Info button
         * exit - Exit button
         */
        private JButton add_Animal = new JButton("Add Animal"); //getcontent
        private JButton move_Animal = new JButton("Move Animal");
        private JButton clear = new JButton("Clear");
        private JButton food = new JButton("Food");
        private JButton info = new JButton("Info");
        private JButton exit = new JButton("Exit");


        /**
         * ButtonPanel ctor - sets Layout with Gridlayout that has 1 row, sets backGround to blue
         * buttons are seperated between themselves.
         */
        ButtonPanel(){
            this.setPreferredSize(new Dimension(800,30));
            this.setBackground(new Color(0,0,255));
            animalButton();
            GridLayout lay = new GridLayout(1,0);
            this.setLayout(lay);
            lay.setHgap(10);
            this.add(add_Animal);
            this.add(move_Animal);
            this.add(clear);
            this.add(food);
            this.add(info);
            this.add(exit);


        }

        /**
         * implements ActionListeners to all buttons.
         * add_Animal - opens AddAnimalDialog if number of animals is less than maximum animals allowed.
         * move_Animal - opens MoveAnimalDialog.
         * info - opens JOptionPane message including JTable with all animals info.
         * clear - deletes animals from the list and uses ManageZoo to call repaint.
         * exit - Exits the system.
         *
         * @return boolean
         */
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
                /**
                 * if info button was pressed and animal list is greater then 1, JOptionPane with JTable will pop.
                 * @param e - info pressed
                 */
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
                /**
                 * if clear button was pressed, all animals are deleted. Must have at-least 1 animal present.
                 * if not, shows JOptionPane error message
                 * @param e
                 */
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
                /**
                 * if food was pressed, FoodDialog is created and manageZoo is called.
                 * @param e
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    FoodDialog food = new FoodDialog();
                    manageZoo();
                }
            });

            this.exit.addActionListener(new ActionListener() {
                /**
                 * exit button was pressed - JOptionPane message will pop up and exit the system.
                 * @param e
                 */
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(new JFrame(), "Goodbye");
                    System.exit(0);
                }
            });
            return true;
        }


    }

    /**
     * drawPanel - extends JPanel. represents drawing panel - all instances in our system are drawn
     * by drawPanel
     * @author : Oran Bourak, Tomer Burman
     * @version : 1
     */
    private static class drawPanel extends JPanel {

        /**
         * sets PreferredSize to 800 on X axis and 600 on Y axis.
         */
        public drawPanel(){
            this.setPreferredSize(new Dimension(800,600));

        }

        /**
         * paintComponent - draws all animals, if food is not null draws current food, if backGround is not null
         * draws back ground chosen.
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


    /**
     * addAnimal - adds animal to list if num_of_Animals is smaller then max animals allowed, and calls manageZoo
     * if  num_of_Animals is not smaller JOPtionPane error message will pop up.
     * @param animal
     */
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
