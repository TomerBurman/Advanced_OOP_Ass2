/**
 * Animal - abstract class that defines general characteristics for animals.
 * Extends Mobile
 * Implements IEdible
 * @version : 1
 * @author : Tomer Burman, Oran Bourak
 */
package animals;
import diet.IDiet;
import food.EFoodType;
import food.IEdible;
import graphics.IAnimalBehavior;
import graphics.IDrawable;
import graphics.ZooFrame;
import graphics.ZooPanel;
import mobility.*;
import mobility.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public abstract class Animal extends Mobile implements IEdible, IDrawable, IAnimalBehavior,Runnable {
    /**
     * attributes :
     * name - name of the animal
     * weight - weight of the animal
     * diet - what the animal may eat
     * EAT_DISTANCE - maximum distance in pixels of animal from food that allows animal to eat food.
     * size - size of animals photo in pixels.
     * default_size - default size of animals photo in pixels.
     * min_size, max_size - ranges allowed for size.
     * color - color of the animal.
     * horSpeed - horizontal speed
     * verSpeed - vertical speed
     * cordChanged - indicator if the animals coordinates changed
     * x_dir,y_dir - direction that animals go in. if x_dir is 1 animal moves right, if x_dir is -1 animal moves left.
     * eatCount - meals the animal had so far
     * pan - the Panel the animal is on
     * img1 - represents movement to right. depends on x_dir
     * img2 - represents movement to left. depends on x_dir
     */
    protected Thread thread;
    protected boolean threadSuspended;
    private String name;
    private double weight;
    private IDiet diet; // Eating appropriate food.
    private final int EAT_DISTANCE = 10;
    private int size; // ranges are 50-300
    protected final static int default_size = 100;
    private final static int min_size = 50;
    private final static int max_size = 300;
    private String color;
    private int horSpeed = 1; // ranges are 1-10
    private int verSpeed = 1; // ranges are 1-10
    private boolean coordChanged;
    private int x_dir = 1;
    private int y_dir = 1;
    private int eatCount;
    private ZooPanel pan;
    private BufferedImage img1, img2;
    private boolean exit = false; // use to stop animal thread
    protected static HashMap<String, String> color_choice = new HashMap<>() {
        {
            put("Natural", "n");
            put("Red", "r");
            put("Blue", "b");
        }
    };
    protected final static String default_color = "Natural";



    /**
     * Animal constructor
     *
     * @param name     Animal name
     * @param location coordinate in (x,y) grid.
     * @param col      - color of the animal
     * @param size     - size of the animal photo in pixels
     */
    public Animal(String name, Point location, String col, int size) {
        super(location); // base class ctor
        this.thread = new Thread(this);
        this.setName(name);
        this.setSize(size);
        color = (col == null) ? "Natural" : col;
        pan = ZooFrame.getPanel();
    }


    /**
     * setSize - sets size of the animal photo in pixels. size X size
     *
     * @param size - size wanted
     * @return true if size is in range [50,300], else returns false and sets size to default_size.
     */
    public boolean setSize(int size) {
        if (size <= max_size && size >= min_size) {
            this.size = size;
            return true;
        }
        this.size = default_size;
        return false;
    }

    /**
     * setting weight of the animal.
     *
     * @param weight - weight to set.
     * @return true if weight is higher than zero
     */
    protected boolean setWeight(double weight) {
        if (weight > 0) {
            this.weight = weight;
            return true;
        }
        System.out.println("I got here ");
        return false;
    }

    /**
     * check about colors
     *
     * @param col color recieved
     * @return true if color is set, false otherwise.
     */
    public boolean setColor(String col) {
        color = col;
        return true;
    }

    /**
     * getWeight - returns weight of the animal, uses MessageUtility.
     *
     * @return double type - weight.
     */
    public double getWeight() {
        return this.weight;
    }


    /**
     * setName - recieves String type, if string is not empty it replaces
     * and returns true, else returns false.
     *
     * @param name - name to change to.
     * @return true if changed, else false.
     */
    private boolean setName(String name) {
        if (name.compareTo("") != 0) { //if name recieved is not empty it changes.
            this.name = name;
            return true;
        }
        return false;
    }


    // IAnimalBehavior interface -

    /**
     * getName - uses MessageUtility.
     *
     * @return String type - name.
     */
    public String getAnimalName() {
        return this.name;
    }

    /**
     * eatInc - increases animals eat count by 1
     */
    public void eatInc() {
        eatCount += 1;
    }

    /**
     * returns size of the animal.
     *
     * @return int type
     */
    public int getSize() {
        return size;
    }

    /**
     * getEatCount - returns how many times animal has eatten
     *
     * @return int type
     */
    public int getEatCount() {
        return eatCount;
    }

    /**
     * getChanges - indicates if animal moved.
     *
     * @return animal coordChanged
     */
    public boolean getChanges() {
        return coordChanged;
    }

    /**
     * setChanges - sets coordChanges to received state
     *
     * @param state
     */
    public synchronized void setChanges(boolean state) {
        coordChanged = state;
    }


    /**
     * abstract method - makes sound.
     */
    public abstract void makeSound();


    /**
     * Return animal type as food to eat.
     *
     * @return EFoodType
     */
    @Override
    public EFoodType getFoodType() {
        return EFoodType.MEAT;
    }


    /**
     * setDiet - setting Diet to a certain animal.
     *
     * @param diet - animal diet type , Carnivore/Herbivore/Omnivore
     * @return true.
     */
    public boolean setDiet(IDiet diet) {
        this.diet = diet;
        return true;
    }


    /**
     * getDiet - returns diet
     *
     * @return IDiet
     */
    public IDiet getDiet() {
        return diet;
    }


    /**
     * eat -
     *
     * @param food - food to feed the animal.
     * @return true if the animal ate, false otherwise
     */
    public boolean eat(IEdible food) {
        double weight_gained = diet.eat(this, food);
        if (weight_gained != 0) {
            this.eatCount++;
            this.setWeight(this.getWeight() + weight_gained);
            this.makeSound();
            return true;
        }
        return false;
    }

    /**
     * toString - prints in the form of :
     *
     * @return String in the form of :
     * e.g [Lion] : Shimon  NEW
     */
    @Override
    public String toString() {
        return "[" + this.getClass().getSimpleName() + "]" + this.name;
    }

    /**
     * Move method
     * use Mobile move method , update the animal weight with the following formula:
     * Weight- (distance*weight*0.00025).
     *
     * @param other received Point to travel to
     * @return distance that the animal traveled from her last location to new location(POINT)
     */
    @Override
    public double move(Point other) {
        double distance = super.move(other);
        double w = this.getWeight();
        this.setWeight(w - w * distance * 0.00025);
        return distance;
    }

    //IDrawable interface implementation

    /**
     * getColor - returns animal color
     *
     * @return String type represents animal color
     */
    public String getColor() {
        return this.color;
    }

    ;

    /**
     * loadImages - loads right facing image to img1 and left facing image to img2
     *
     * @param nm - type of the animal e.g : "bear".
     */
    public void loadImages(String nm) {
        try {
            img1 = ImageIO.read(new File(PICTURE_PATH + "\\src\\photos\\" + nm + "1.png"));
            img2 = ImageIO.read(new File(PICTURE_PATH + "\\src\\photos\\" + nm + "2.png"));
        } catch (IOException e) {
            System.out.println("Cannot load image");
        }
    }

    /**
     * drawObject - graphics drawImage to draw animal to draw_panel in ZooPanel.
     *
     * @param g - graphics.
     */
    public void drawObject(Graphics g) {
        Graphics2D gr = (Graphics2D) g;
        int x_location = (this.getLocation().getX() + size > 800) ? (800 - size) : Math.abs(this.getLocation().getX() - size / 2);
        int y_location = (this.getLocation().getY() + size > 600) ? (600 - size) : Math.abs(this.getLocation().getY() - size / 10);
        if (x_dir == 1)
            gr.drawImage(img1, x_location, y_location, size / 2, size, pan);
        else
            gr.drawImage(img2, x_location, y_location, size / 2, size, pan);


    }

    /**
     * getHorSpeed - returns horizontal speed of the animal.
     *
     * @return int
     */
    public Integer getHorSpeed() {
        return this.horSpeed;
    }

    /**
     * getVerSpeed - returns vertical speed of the animal
     *
     * @return int
     */
    public Integer getVerSpeed() {
        return this.verSpeed;
    }

    /**
     * getEAT_DISTANCE - returns distance required to eat any type of appropriate food
     *
     * @return int.
     */
    public int getEAT_DISTANCE() {
        return this.EAT_DISTANCE;
    }

    /**
     * GetThread
     * @return reference to animal thread
     */
    public Thread getThread(){
        return this.thread;
    }

    /**
     * Set Thread Exit
     * @param b - false - the thread is running , true- stop the thread
     */
    public void setThreadExit(boolean b) {
        this.exit = b;
    }

    @Override
    public void run() {
        this.threadSuspended = false;
        while (!exit) {
            if(threadSuspended == true) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int x = getLocation().getX();
            int y =getLocation().getY();

            if (x + horSpeed * x_dir > 800)
                this.x_dir = -1;
            else if (x + horSpeed * x_dir < 0)
                this.x_dir = 1;

            if (y + verSpeed * y_dir > 600)
                this.y_dir = -1;
            else if (y + verSpeed * y_dir < 0)
                this.y_dir = 1;

            synchronized (this) {
                this.setLocation(new Point(x + horSpeed * x_dir, y + verSpeed * y_dir));
                coordChanged = true;
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}





