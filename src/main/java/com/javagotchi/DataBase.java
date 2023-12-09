package com.javagotchi;

import java.sql.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Class that contains all methods that are needed for a database usage for
 * Tamagotchi project
 */
public class DataBase {
    /** Connection variable*/
    private Connection connection;
    /** Name of the database*/
    public static final String DB_NAME = "database.db";
    /** String for a database connection*/
    public static final String CONNECTION_STRING = "jdbc:sqlite:" + DB_NAME;
    /** Name of the character table*/
    public static final String TABLE_CHARACTER = "character";
    /** Name of the id column*/
    public static final String COLUMN_ID = "id";
    /** Name of the hunger column*/
    public static final String COLUMN_HUNGER = "hunger";
    /** Name of the happiness column*/
    public static final String COLUMN_HAPPINESS = "happiness";
    /** Name of the cleanliness column*/
    public static final String COLUMN_CLEANLINESS = "cleanliness";
    /** Name of the weight column*/
    public static final String COLUMN_WEIGHT = "weight";
    /** Name of the energy column*/
    public static final String COLUMN_ENERGY = "energy";
    /** Name of the health column*/
    public static final String COLUMN_HEALTH = "health";
    /** Name of the level column*/
    public static final String COLUMN_LEVEL = "level";
    /** Name of the experience column*/
    public static final String COLUMN_EXPERIENCE = "experience";
    /** Name of the age column*/
    public static final String COLUMN_AGE = "age";
    /** Name of the last_usage_time column*/
    public static final String COLUMN_LAST_USAGE_TIME = "last_usage_time";
    /** Name of the sleeping column */
    public static final String COLUMN_SLEEPING = "sleeping";
    /** Index of the id column */
    public static final int INDEX_CHARACTER_ID = 1;
    /** Index of the hunger column*/
    public static final int INDEX_CHARACTER_HUNGER = 2;
    /** Index of the cleanliness column*/
    public static final int INDEX_CHARACTER_CLEANLINESS = 3;
    /** Index of the weight column*/
    public static final int INDEX_CHARACTER_WEIGHT = 4;
    /** Index of the energy column*/
    public static final int INDEX_CHARACTER_ENERGY = 5;
    /** Index of the health column*/
    public static final int INDEX_CHARACTER_HEALTH = 6;
    /** Index of the level column*/
    public static final int INDEX_CHARACTER_LEVEL = 7;
    /** Index of the experience column*/
    public static final int INDEX_CHARACTER_EXPERIENCE = 8;
    /** Index of the age column */
    public static final int INDEX_CHARACTER_AGE = 9;

    /** Index of the happiness column*/
    public static final int INDEX_CHARACTER_HAPPINESS = 10;
    /** Index of the sleeping column*/
    public static final int INDEX_CHARACTER_SLEEPING = 11;
    /** Index of the last_usage_time column*/
    public static final int INDEX_CHARACTER_LAST_USAGE_TIME = 12;
    /** Date format*/
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    /**
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
    /**
    * Closes a database connection if the connection was opened before
     */
    public void close(){
        try {
            if (connection != null) {
                connection.close();
            }
        }catch (SQLException ex){
            System.out.println("Something went wrong while closing the database connection");
        }
    }
    /** Method that gets the latest information about the character from a database,
     * creates a character object and sets characters values based on a values from a database
     * If a database has no information about a character then a based character is created and returned
     *
     * @return      Character object containing information saved to the database if this doesn't exist then returns a values for the new character
     * */
    public Character getLatestCharacterData(){
        String query = "SELECT * FROM " + TABLE_CHARACTER + " ORDER BY " + COLUMN_ID + " DESC LIMIT 1";
        Character character = new Character();

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)){

            if(resultSet.next()) { //data about previous game exists
                character.setHunger(resultSet.getInt(INDEX_CHARACTER_HUNGER));
                character.setCleanliness(resultSet.getInt(INDEX_CHARACTER_CLEANLINESS));
                character.setWeight(resultSet.getInt(INDEX_CHARACTER_WEIGHT));
                character.setEnergy(resultSet.getInt(INDEX_CHARACTER_ENERGY));
                character.setHealth(resultSet.getInt(INDEX_CHARACTER_HEALTH));
                character.setLevel(resultSet.getInt(INDEX_CHARACTER_LEVEL));
                character.setExperience(resultSet.getInt(INDEX_CHARACTER_EXPERIENCE));
                character.setAge(resultSet.getInt(INDEX_CHARACTER_AGE));
                character.setHappiness(resultSet.getInt(INDEX_CHARACTER_HAPPINESS));
                character.setSleeping((resultSet.getInt(INDEX_CHARACTER_SLEEPING) == 1 )); //if the character was sleeping the value is 1

                if(character.isSleeping()){
                    String lastUsage = resultSet.getString(INDEX_CHARACTER_LAST_USAGE_TIME);
                    LocalTime sleepTime = LocalTime.parse(lastUsage,formatter);
                    character.wakeUp(sleepTime);
                }
            }
            else { //this is a first time a player is playing the game
                character.setHunger(100);
                character.setCleanliness(100);
                character.setWeight(50);
                character.setEnergy(100);
                character.setHealth(100);
                character.setLevel(1);
                character.setExperience(0);
                character.setAge(0);
                character.setHappiness(100);
                character.setSleeping(false);
            }
        }catch (SQLException e ){
            System.out.println("An error occurred while getting the latest data " + e.getMessage());
        }

        return character;
    }
}
