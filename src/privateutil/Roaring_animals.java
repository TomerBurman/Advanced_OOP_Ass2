package privateutil;
import animals.*;
import mobility.Point;

/** Roaring animals - extends from Animal, represents roaring animals.
 * @version : 1
 * @author : Oran Bourak, Tomer Burman
 *
 *
 */
public abstract class Roaring_animals extends Animal {
    /**
     * ctor, calls Animal ctor.
     * @param name
     * @param location
     */
    public Roaring_animals(String name, Point location){
        super(name,location);
    }


    /**
     * abstract method to roar
     */
    public abstract void  roar();
    /**
     * makes a roaring sound.
     */
    @Override
    public void makeSound() {
        this.roar();
    }

}
