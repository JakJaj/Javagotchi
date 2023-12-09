package com.javagotchi;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CharacterTest {
    private Character testCharacter;
    @BeforeEach
    void init(){
        testCharacter = new Character();
        testCharacter.setHunger(50);
        testCharacter.setCleanliness(50);
        testCharacter.setWeight(50);
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
        assertEquals(50,testCharacter.getWeight());
        testCharacter.eat();
        assertEquals(100,testCharacter.getHunger());
        assertEquals(60,testCharacter.getHappiness());
        assertEquals(58,testCharacter.getExperience());
        assertEquals(50,testCharacter.getWeight());
        testCharacter.eat();
        assertEquals(100,testCharacter.getHunger());
        assertEquals(50,testCharacter.getHappiness());
        assertEquals(58,testCharacter.getExperience());
        assertEquals(51,testCharacter.getWeight());
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
        LocalTime sleepStart = testCharacter.sleep();
        assertTrue(testCharacter.isSleeping());
    }

    @Test
    void wakeUp() {
        LocalTime sleepStart = testCharacter.sleep();
        LocalTime test = sleepStart.minusMinutes(30);
        testCharacter.wakeUp(test);

        assertEquals(80,testCharacter.getEnergy());
        assertFalse(testCharacter.isSleeping());
        assertEquals(52,testCharacter.getExperience());
    }

    @Test
    void play() {
        testCharacter.play();
        assertEquals(60,testCharacter.getHappiness());
        assertEquals(55,testCharacter.getExperience());
        assertEquals(35,testCharacter.getEnergy());
        assertEquals(40,testCharacter.getHunger());
        assertEquals(40,testCharacter.getCleanliness());
        assertEquals(51,testCharacter.getHealth());
        assertEquals(50,testCharacter.getWeight());
        testCharacter.play();
        assertEquals(70,testCharacter.getHappiness());
        assertEquals(60,testCharacter.getExperience());
        assertEquals(20,testCharacter.getEnergy());
        assertEquals(30,testCharacter.getHunger());
        assertEquals(30,testCharacter.getCleanliness());
        assertEquals(52,testCharacter.getHealth());
        assertEquals(50,testCharacter.getWeight());
        testCharacter.play();
        assertEquals(80,testCharacter.getHappiness());
        assertEquals(65,testCharacter.getExperience());
        assertEquals(5,testCharacter.getEnergy());
        assertEquals(20,testCharacter.getHunger());
        assertEquals(20,testCharacter.getCleanliness());
        assertEquals(53,testCharacter.getHealth());
        assertEquals(50,testCharacter.getWeight());
        testCharacter.play();
        assertEquals(70,testCharacter.getHappiness());
        assertEquals(65,testCharacter.getExperience());
        assertEquals(0,testCharacter.getEnergy());
        assertEquals(10,testCharacter.getHunger());
        assertEquals(10,testCharacter.getCleanliness());
        assertEquals(43,testCharacter.getHealth());
        assertEquals(49,testCharacter.getWeight());
    }

    @Test
    void levelUp() {
        testCharacter.levelUp();
        assertEquals(10,testCharacter.getLevel());
        assertEquals(50,testCharacter.getExperience());
        testCharacter.setExperience(103);
        testCharacter.levelUp();
        assertEquals(11,testCharacter.getLevel());
        assertEquals(3, testCharacter.getExperience());
        testCharacter.setExperience(100);
        testCharacter.levelUp();
        assertEquals(12,testCharacter.getLevel());
        assertEquals(0,testCharacter.getExperience());
    }
}