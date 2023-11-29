package com.javagotchi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that contains all methods that are needed for a database usage for
 * Tamagotchi project
 */
public class DataBase {

    private Connection connection;
    public static final String DB_NAME = "database.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;

    /*
    * Opens a database connection
    *
    * @return true if the connection process is successful, otherwise false
     */
    public boolean open(){
        try{
            connection = DriverManager.getConnection(CONNECTION_STRING);

            return true;
        }catch (SQLException e){
            System.out.println("Database connection failed");
            return false;
        }
    }
}
