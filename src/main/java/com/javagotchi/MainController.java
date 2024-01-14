package com.javagotchi;

import javafx.fxml.FXML;

public class MainController {
    /** Database variable*/
    DataBase dataBase = DataBase.getInstance();
    /** Character variable */
    Character character;
    /** Method that is run when the application is starting*/
    @FXML
    public void initialize() {

        if (dataBase.open()) {
            character = dataBase.getLatestCharacterData();
        }
        dataBase.close();
    }
}