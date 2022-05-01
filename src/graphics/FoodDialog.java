package graphics;

import javax.swing.*;
import javax.swing.plaf.OptionPaneUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import food.EFoodType;
import food.Meat;
import plants.*;

import static javax.swing.JOptionPane.showOptionDialog;

public class FoodDialog implements ActionListener {
    private JButton cabbage,lettuce,meat;
    public FoodDialog(){
        cabbage = new JButton("Cabbage");
        lettuce = new JButton("Lettuce");
        meat = new JButton("Meat");

        Object[] options = {cabbage,lettuce,meat};
        cabbage.addActionListener(this);
        lettuce.addActionListener(this);
        meat.addActionListener(this);
        showOptionDialog(ZooPanel.getDraw_panel(),"Choose one","Food",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,new ImageIcon(System.getProperty("user.dir") + "\\src\\photos\\" + "Turtle.png"),options,options[2]);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(cabbage))
            ZooPanel.setFoodType(new Cabbage());
        if(e.getSource().equals(lettuce))
            ZooPanel.setFoodType(new Lettuce());
        if(e.getSource().equals(meat))
            ZooPanel.setFoodType(new Meat());


    }
}
