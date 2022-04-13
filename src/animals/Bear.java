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
import utilities.MessageUtility;
import static utilities.MessageUtility.logGetter;
import static utilities.MessageUtility.logSetter;



public class Bear extends Roaring_animals {
    private final static double defaultWeight = 308.2;
    private final static String defaultFurColor = "GRAY";
    private String furColor;
    private final String[] colors_array = {"BLACK","WHITE","GRAY"}; // color choices.

    /**
     * Bear Ctor
     * @param name - bear name
     * @param location - bear location
     * @param furColor - bear color
     */
    public Bear(String name,Point location, String furColor){
        super(name,location);
        MessageUtility.logConstractor(this.getClass().getSimpleName(),this.getName());
        this.setWeight(defaultWeight);
        this.setDiet(new Omnivore()); // meat eater
        if(!setFurColor(furColor))
            setFurColor(defaultFurColor);


    }

    /**
     * Bear Ctor that receives name and location.
     * @param name - bear name
     * @param location - bear location
     */
    public Bear(String name, Point location) {
        this(name, location,defaultFurColor);
    }

    /**
     * Ctor that receives only String for name initiates base first.
     *set Fur color to default.
     * @param name - bear name
     */
    public Bear(String name) {
        this(name, new Point(100,5),defaultFurColor);
    }

    /**
     * setFurColor method.
     * @param furColor - bear fur color
     * @return true if the fur color is in the fur color list else false.
     */
    public boolean setFurColor(String furColor){
        for (String s : colors_array)
            if (s.equals(furColor)) {
                this.furColor = furColor;
                logSetter(this.getName(), "setFurColor", furColor, true);
                return true;
            }
        logSetter(this.getName(), "setFurColor", furColor, false);
        return false;
    }

    /**
     * getFutColor
     * @return String - represent the bear fur color
     */
    public String getFurColor(){
        logGetter(this.getName(),"getFurColor",this.furColor);
        return this.furColor;
    }

    @Override
    public String toString(){
        return super.toString() ;
    }

    /**
     * Roar methode
     * Using after the animal eats.
     * uses messageUtility.
     */
    @Override
    public void roar() {
        MessageUtility.logSound(this.getName(),"Stands on its hind legs, roars and scratches its belly");
    }

}
