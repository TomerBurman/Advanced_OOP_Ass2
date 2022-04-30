package graphics;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZooFrame extends JFrame {
    private final ZooMenu menu = new ZooMenu();
    private static ZooPanel panel;


    private static class ZooMenu extends JMenuBar{
        ZooMenu(){
            JMenu file = new JMenu("File");
            JMenuItem exit = new JMenuItem("Exit");
            exit.addActionListener(e -> {
                JOptionPane.showMessageDialog(new JFrame(),"Goodbye");
                System.exit(0);
            });

            file.add(exit);

            JMenu backGround = new JMenu("BackGround");
            JMenuItem image = new JMenuItem("Image");
            JMenuItem green = new JMenuItem("Green");
            JMenuItem none = new JMenuItem("None");
            none.addActionListener(e -> ZooPanel.setBack(Color.WHITE));
            green.addActionListener(e -> ZooPanel.setBack(Color.GREEN));
            backGround.add(image);
            backGround.add(green);
            backGround.add(none);
            JMenu help = new JMenu("Help");
            JMenuItem help_item = new JMenuItem("Help");
            help_item.addActionListener(e -> JOptionPane.showMessageDialog(new JFrame(),"Homework-2 GUI","Help",JOptionPane.INFORMATION_MESSAGE));
            help.add(help_item);
            this.add(file);
            this.add(backGround);
            this.add(help);
        }


    }
    public ZooFrame(){
        this.setLayout(new BorderLayout());
        panel = new ZooPanel();
        this.add(panel, BorderLayout.CENTER);
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    public static ZooPanel getPanel(){
        return panel;
    }

    public static void main(String[] args){
        ZooFrame zoo = new ZooFrame();
        zoo.setJMenuBar(new ZooMenu());
    }

}
