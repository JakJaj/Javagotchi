package com.javagotchi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
public class Character {

    private int hunger;
    private int cleanliness;
    private int weight;
    private int energy;
    private int level;
    private int age;
    private int happiness;
    private boolean sleeping;

    public void eat(){

    }
    public void clean(){
    }
    /*
    * Getting a character to sleep.
    * Setting sleeping field to false and saving a time when a character went to sleep
    * @return       LocalTime of a moment when a character went to sleep
     */
    public LocalTime sleep(){
        this.sleeping = true;
        return LocalTime.now();
    }
    public void play(){

    }
    public void levelUp(){

    }
}
