package food;

import graphics.IDrawable;
import graphics.ZooPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Meat implements IDrawable, IEdible {
    private BufferedImage img;

    public Meat(){
        this.loadImages("meat");
    }
    @Override
    public void loadImages(String nm) {
        try {
            img = ImageIO.read(new File(PICTURE_PATH + "\\src\\photos\\" + nm + ".gif"));
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(ZooPanel.getDraw_panel(),"Cannot load image","Error",JOptionPane.ERROR_MESSAGE);
            }
    }

    @Override
    public void drawObject(Graphics g) {
        JPanel pan = ZooPanel.getDraw_panel();
        g.drawImage(img,pan.getWidth()/2-5,pan.getHeight()/2-5, 25, 25, pan);
    }

    @Override
    public String getColor() {
        return "Red";
    }

    @Override
    public EFoodType getFoodType() {
        return EFoodType.MEAT;
    }
}
