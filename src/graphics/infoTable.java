package graphics;

import animals.Animal;

import javax.swing.*;
import java.util.ArrayList;

public class infoTable{
    private static Object[] colsParams = {"Animal","Name","Color","Weight","Hor. speed","Ver. speed","Eat counter"};
    private Object[][] animalsInfo;

    public infoTable(){
        animalsInfo = new Object[ZooPanel.getAnimalList().size()+1][colsParams.length];
        int i = 0;
        int total = 0;
        for(Animal animal:ZooPanel.getAnimalList()){
           animalsInfo[i][0] = animal.getClass().getSimpleName();
           animalsInfo[i][1] = animal.getAnimalName();
           animalsInfo[i][2] = animal.getColor();
           animalsInfo[i][3] = animal.getWeight();
           animalsInfo[i][4] = animal.getHorSpeed();
           animalsInfo[i][5] = animal.getVerSpeed();
           animalsInfo[i][6] = animal.getEatCount();
           i++;
           total += animal.getEatCount();
        }
        animalsInfo[i][0] = "Total";
        animalsInfo[i][6] = total;

    }
    public Object[] getCols(){
        return colsParams;
    }
    public Object[][] getAnimalsInfo(){
        return animalsInfo;
    }

}
