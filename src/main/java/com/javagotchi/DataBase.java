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
    public static final String TABLE_CHARACTER = "character";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_HAPPINESS = "happiness";
    public static final String COLUMN_CLEANLINESS = "cleanliness";
    public static final String COLUMN_WEIGHT = "weight";
    public static final String COLUMN_ENERGY = "energy";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_AGE = "age";
    public static final String COLUMN_LAST_USAGE_TIME = "last_usage_time";
    public static final int INDEX_CHARACTER_ID = 1;
    public static final int INDEX_CHARACTER_HAPPINESS = 2;
    public static final int INDEX_CHARACTER_CLEANLINESS = 3;
    public static final int INDEX_CHARACTER_WEIGHT = 4;
    public static final int INDEX_CHARACTER_ENERGY = 5;
    public static final int INDEX_CHARACTER_LEVEL = 6;
    public static final int INDEX_CHARACTER_AGE = 7;
    public static final int INDEX_CHARACTER_LAST_USAGE_TIME = 8;

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
    public void close(){
        try {
            if (connection != null) {
                connection.close();
            }
        }catch (SQLException ex){
            System.out.println("Something went wrong while closing the database connection");
        }
    }
}
