package com.javagotchi;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;

@Getter
@Setter
public class Character {

    /** Character's Hunger level*/
    private int hunger;
    /** Character's Cleanliness level*/
    private int cleanliness;
    /** Character's weight*/
    private int weight;
    /** Character's energy level*/
    private int energy;
    /** Character's health level*/
    private int health;
    /** Character's level*/
    private int level;
    /** Character's experience level*/
    private int experience;
    /** Character's age*/
    private int age;
    /** Character's happiness level*/
    private int happiness;
    /** Character's sleeping flag*/
    private boolean sleeping;
    /** The time when character went to sleep*/
    private LocalTime bedTime;
    /** Static and only instance of a Character class*/
    public static Character character;
    //** Character's being alive flag */
    public boolean alive;
    /**
     * Private constructor so that its impossible to create an instance of this class using the "new" keyword
     * Use getInstance method to get an instance
     */
    private Character() {}
    /**
     * The only one method to get an instance of a Character instance.
     *
     * @return If the instance already exists then the code returns an existing one
     * otherwise this method returns a newly created instance of a character
     */
    public static Character getInstance(){
        if(character == null){
            character = new Character();
        }

        return character;
    }
    /**
    * Making a character eat some food
    * If the character overeats making it sadder
    * If character was hungry eating makes it happier
    * There is also a 50/50 chance that a character gets a little dirty while eating
     */
    public void eat(){
        Random rng = new Random();
        boolean gettingDirty = rng.nextBoolean();
        if(gettingDirty) this.cleanliness = Math.max(0,this.cleanliness - 10);

        if(hunger + 25 > 100){
            this.weight++;
            this.happiness = Math.max(happiness - 10, 0);
            this.health = Math.max(health - 5 ,0);
            this.hunger = 100;
            System.out.println("Character overfed");
        }
        else{
            this.hunger = Math.min(100,this.hunger + 25);
            this.happiness = Math.min(100, this.happiness + 5);

            this.experience = this.experience + 4;
            System.out.println("Character is happy to eat");
        }
    }
    /**
    * Getting character cleaner
    * If the character is already clean enough then it maks the character sad and drains energy
    * If the character needs a little shower then adding an appropriate value to a cleanliness value
     */
    public void clean(){
        if(cleanliness + 30 > 100){
            this.happiness = Math.max(this.happiness - 10, 0);
            this.energy = Math.max(0,this.energy - 10);
            this.cleanliness = 100;
            System.out.println("There is no need to clean the character");
        }
        else {
            this.cleanliness = Math.min(100,this.cleanliness + 30);
            this.happiness = Math.min(this.happiness + 10, 100);

            this.experience = this.experience + 5;
            System.out.println("Character is cleaner");
        }
    }
    /**
    * Getting a character to sleep.
    * Setting sleeping field to false and saving a time when a character went to sleep
     */
    public void sleep(){
        this.sleeping = true;
        this.bedTime = LocalTime.now();
        System.out.println("Character is going to sleep");
    }
    /**
    * Waking up a character from a sleep, and making character's go up according to the amount of minutes that elapsed from character going to sleep.
    * Setting a sleeping field to false
     */
    public void wakeUp() {
        LocalTime now = LocalTime.now();

        Duration timeElapsed = Duration.between(getBedTime(), now);
        long sleepingTime = timeElapsed.toMinutes();
        if(this.energy + (int) sleepingTime > 100) this.energy = Math.min(100, this.energy + (int) sleepingTime);
        else this.energy = this.energy + (int) sleepingTime;
        this.sleeping = false;
        this.experience = this.experience + 2;
        System.out.println("Character woke up");
    }
    /**
    * Playing with a character which makes it happier, hungrier, dirtier, healthier and lighter. WHAT A COMBO!
    * When the character doesn't have enough energy for that activity it loses some health and is getting sadder because of it
     * @param funAmount the amount of fun the game provides
     */
    public void play(int funAmount){
        if(energy <= 10){
            this.happiness = Math.max(0, this.happiness - 20 + funAmount);
            this.health = Math.max(0, this.health - 10);

            this.weight = Math.max(0, weight - 1);
            System.out.println("Character is too tired");
            System.out.println("Don't force it to play");
        }
        else {
            this.happiness = Math.min(100, this.happiness + funAmount);
            this.health = Math.min(100,this.health + 1);
            this.experience = this.experience + 10;
            System.out.println("Character played a game");
        }

        this.energy = Math.max(this.energy - 10, 0);
        this.hunger = Math.max(0,this.hunger - 5);
        this.cleanliness = Math.max(0, this.cleanliness - 10);
    }
    /**
    * Character level is going up. Only when the experience level is above 100
     * Otherwise we do nothing
     */
    public void levelUp(){
        if(experience >= 100){
            this.level++;
            this.experience = this.experience - 100;
            System.out.println("LVL UP!");
        }
        else {
            System.out.println("Not enough experience to level up");
        }
    }
    public void timePassed(){
        this.cleanliness-=1;
        this.hunger-=1;
        this.experience+=4;
        this.happiness-=1;
        this.energy-=1;
        levelUp();
        if(this.health <= 0){
            this.health = 0;
            this.alive = false;
            System.out.println("Character died :/");
        }
    }
    @Override
    public String toString() {

        return "Character Data\n" +
                "---------------------------\n" +
                "Hunger   =   " + hunger +
                "\n---------------------------\n" +
                "Cleanliness   =   " + cleanliness +
                "\n---------------------------\n" +
                "Weight   =   " + weight +
                "\n---------------------------\n" +
                "Energy   =   " + energy +
                "\n---------------------------\n" +
                "Health   =   " + health +
                "\n---------------------------\n" +
                "Level   =   " + level +
                "\n---------------------------\n" +
                "Experience   =   " + experience +
                "\n---------------------------\n" +
                "Age   =   " + age +
                "\n---------------------------\n" +
                "Happiness   =   " + happiness +
                "\n---------------------------\n" +
                "Sleeping   =   " + sleeping +
                "\n---------------------------\n" +
                "BedTime   =   " + bedTime;
    }
}
