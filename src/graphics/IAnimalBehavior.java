package graphics;

/** IAnimalBehavior -
 * @version : 1
 * @author : Oran Bourak, Tomer Burman
 *
 */
public interface IAnimalBehavior {
    public String getAnimalName();
    public int getSize();
    public void eatInc();
    public int getEatCount();
    public boolean getChanges();
    public void setChanges(boolean state);

}
