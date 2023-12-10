package com.javagotchi;

import javafx.fxml.FXML;

public class MainController {
    /** Database variable*/
    DataBase dataBase = new DataBase();
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
    /** Method that is run when a user want to save the game*/
    @FXML
    public void save() {
        if(dataBase.open()){
            dataBase.insertNewestData(character);
        }
        dataBase.close();
    }
    /** Method that is run when a user exits the game */
    @FXML
    public void exit(){
        save();
    }
}