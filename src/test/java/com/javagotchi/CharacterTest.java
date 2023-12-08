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

    }

    @Test
    void clean() {
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