package graphics;

import javax.swing.*;

public class ZooFrame extends JFrame {
    private final ZooMenu menu = new ZooMenu();


    private class ZooMenu extends JMenuBar{
        ZooMenu(){
            JMenu file = new JMenu("File");
            JMenuItem exit = new JMenuItem("Exit");
            file.add(exit);

            JMenu backGround = new JMenu("BackGround");
            JMenuItem image = new JMenuItem("Image");
            JMenuItem green = new JMenuItem("Green");
            JMenuItem none = new JMenuItem("None");
            backGround.add(image);
            backGround.add(green);
            backGround.add(none);
            JMenu help = new JMenu("Help");
            JMenuItem help_item = new JMenuItem("Help");
            help.add(help_item);
        }
    }


}
