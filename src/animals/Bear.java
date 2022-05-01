/**
 * Bear - class that represent Bear obj.
 * Extends Roaring_animals
 * @version : 1
 * @author : Tomer Burman, Oran Bourak
 */

package animals;
import diet.Omnivore;
import mobility.Point;
import privateutil.Roaring_animals;


public class Bear extends Roaring_animals {
    private final double weightFactor = 1.5;

    /**
     * Bear Ctor
     * @param name - bear name
     * @param location - bear location
     */
    public Bear(String name,Point location,String col,int size){
        super(name,location,col,size);
        this.setWeight(getSize() * weightFactor);
        this.setDiet(new Omnivore()); // meat eater
        this.loadImages("bea_" + color_choice.get(col) + "_");
    }

    @Override
    public void roar() {
        //TODO may need to delete
    }

    /**
     * Bear Ctor that receives name and location.
     * @param name - bear name
     * @param location - bear location
     */
    public Bear(String name, Point location) {
        this(name, location,default_color,default_size);
    }

    /**
     * Ctor that receives only String for name initiates base first.
     *set Fur color to default.
     * @param name - bear name
     */
    public Bear(String name) {
        this(name, new Point(100,5),default_color,default_size);
    }


    @Override
    public String toString(){
        return super.toString() ;
    }


    /**
     * getDefaultLocation - returns default location
     * @return point
     */
    public Point getDefaultLocation(){
        return new Point(100,5);
    }

}
