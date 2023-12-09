package com.javagotchi;

import javafx.fxml.FXML;

public class MainController {
    @FXML
    public void initialize(){
        DataBase dataBase = new DataBase();
        if (dataBase.open()){
            Character character = dataBase.getLatestCharacterData();
        }
        dataBase.close();
    }
}