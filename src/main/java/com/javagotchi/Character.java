package com.javagotchi;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;

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

    /*
    * Making a character eat some food
    * If the character overeats making it sadder
    * If character was hungry eating makes it happier
    * There is also a 50/50 chance that a character gets a little dirty while eating
     */
    public void eat(){
        Random rng = new Random();
        boolean gettingDirty = rng.nextBoolean();
        if(gettingDirty) this.cleanliness = this.cleanliness - 10;

        if(hunger + 25 > 100){
            this.weight++;
            this.happiness = this.happiness - 10;
        }
        else{
            this.hunger = this.hunger + 25;
            this.happiness = this.happiness + 5;
        }
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
    /*
    * Waking up a character from a sleep, and making character's go up according to the amount of minutes that elapsed from character going to sleep.
    * Setting a sleeping field to false
    * @params       LocalTime of a moment when a character went to sleep
     */
    public void wakeUp(LocalTime sleepTime) {
        LocalTime now = LocalTime.now();
        Duration timeElapsed = Duration.between(sleepTime, now);
        long sleepingTime = timeElapsed.toMinutes();
        if (this.energy + sleepingTime > 100) {
            this.energy = 100;
        }
        else {
            this.energy = this.energy + (int) sleepingTime;
        }
        sleeping = false;
    }
    public void play(){

    }
    public void levelUp(){

    }
}
