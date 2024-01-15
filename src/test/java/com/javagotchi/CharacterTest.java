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
        testCharacter = Character.getInstance();
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
        assertEquals(55,testCharacter.getExperience());
        assertEquals(50,testCharacter.getEnergy());
        testCharacter.clean();
        assertEquals(100,testCharacter.getCleanliness());
        assertEquals(50,testCharacter.getHappiness());
        assertEquals(55,testCharacter.getExperience());
        assertEquals(40,testCharacter.getEnergy());
    }

    @Test
    void sleep() {
        testCharacter.sleep();
        assertTrue(testCharacter.isSleeping());
    }

    @Test
    void wakeUp() {
        testCharacter.sleep();
        testCharacter.setBedTime(testCharacter.getBedTime().minusMinutes(30));
        testCharacter.wakeUp();

        assertEquals(80,testCharacter.getEnergy());
        assertFalse(testCharacter.isSleeping());
        assertEquals(52,testCharacter.getExperience());
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