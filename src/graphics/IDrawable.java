package graphics;

import java.awt.*;

/**IDrawable -
 * @version : 1
 * @author : Oran Bourak, Tomer Burman.
 *
 */
public interface IDrawable {

    public final static String PICTURE_PATH=null; // ask

    public void loadImages(String nm);

    public void drawObject(Graphics g);
    public String getColor();



}
