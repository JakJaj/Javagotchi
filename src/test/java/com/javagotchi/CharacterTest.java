package com.javagotchi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    private Character testCharacter;
    @BeforeEach
    void init(){
        testCharacter = new Character();
        testCharacter.setHunger(50);
        testCharacter.setCleanliness(50);
        testCharacter.setWeight(10);
        testCharacter.setEnergy(50);
        testCharacter.setHealth(50);
        testCharacter.setLevel(10);
        testCharacter.setExperience(50);
        testCharacter.setAge(10);
        testCharacter.setHappiness(50);
        testCharacter.setSleeping(false);
    }
    @Test
    void eat() {
        testCharacter.eat();
        assertEquals(75,testCharacter.getHunger());
        assertEquals(55,testCharacter.getHappiness());
        assertEquals(54,testCharacter.getExperience());
        assertEquals(10,testCharacter.getWeight());
        testCharacter.eat();
        assertEquals(100,testCharacter.getHunger());
        assertEquals(60,testCharacter.getHappiness());
        assertEquals(58,testCharacter.getExperience());
        assertEquals(10,testCharacter.getWeight());
        testCharacter.eat();
        assertEquals(100,testCharacter.getHunger());
        assertEquals(50,testCharacter.getHappiness());
        assertEquals(58,testCharacter.getExperience());
        assertEquals(11,testCharacter.getWeight());
    }

    @Test
    void clean() {
        testCharacter.clean();
        assertEquals(80,testCharacter.getCleanliness());
        assertEquals(60,testCharacter.getHappiness());
        assertEquals(53,testCharacter.getExperience());
        assertEquals(50,testCharacter.getEnergy());
        testCharacter.clean();
        assertEquals(100,testCharacter.getCleanliness());
        assertEquals(50,testCharacter.getHappiness());
        assertEquals(53,testCharacter.getExperience());
        assertEquals(40,testCharacter.getEnergy());
    }

    @Test
    void sleep() {
    }

    @Test
    void wakeUp() {
    }

    @Test
    void play() {
    }

    @Test
    void levelUp() {
    }
}