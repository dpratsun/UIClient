package com.kator.weightguard.ui.uielement;

import javafx.scene.control.Button;

/**
 * Created by prats on 4/21/18.
 */
public class ShelfAlarmResetButton extends Button {
    public String shelfId;

    public void setShelfId(String shelfId){
        this.shelfId = shelfId;
    }

    public String getShelfId(){
        return shelfId;
    }
}
