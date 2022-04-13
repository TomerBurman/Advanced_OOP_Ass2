package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZooPanel extends JPanel implements Runnable {
    private class ButtonPanel extends JPanel implements ActionListener {
        private JButton add_Animal = new JButton("Add Animal");
        private JButton move_Animal = new JButton("Move Animal");
        private JButton clear = new JButton("Clear");
        private JButton food = new JButton("Food");
        private JButton info = new JButton("Info");
        private JButton exit = new JButton("Exit");

        ButtonPanel(){
            this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
            this.setBackground(new Color(0,0,255));
            this.setAlignmentX(10);


        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }




    @Override
    public void run() {
        //TODO
    }


}
