package com.javagotchi;

import javafx.fxml.FXML;

public class MainController {
    DataBase dataBase = new DataBase();
    Character character;
    @FXML
    public void initialize() {

        if (dataBase.open()) {
            character = dataBase.getLatestCharacterData();
        }
        dataBase.close();
    }

    @FXML
    public void save() {
        if(dataBase.open()){
            dataBase.insertNewestData(character);
        }
        dataBase.close();
    }
}