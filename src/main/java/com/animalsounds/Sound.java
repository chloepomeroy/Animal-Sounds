package com.animalsounds;

import com.google.inject.Provides;
import javax.inject.Inject;

import javax.inject.Inject;

public enum Sound {

    COW("C:\\Users\\Lo\\Downloads\\mooing-cow-122255.wav", "hearCows", 3044, true,"Cow"),
    CHICKEN("C:\\Users\\Lo\\Downloads\\mooing-cow-122255.wav", "hearChickens", 0, false,"Chicken"),
    DUCK("C:\\Users\\Lo\\Downloads\\mooing-cow-122255.wav", "hearDucks", 413, true,"Duck"),
    SHEEP("C:\\Users\\Lo\\Downloads\\mooing-cow-122255.wav", "hearSheep", 2053, true,"Sheep"),
    FROG("C:\\Users\\Lo\\Downloads\\mooing-cow-122255.wav", "hearFrogs", 0, false,"Frog");

    private final String resourceName;

    private final String configName;

    private final int soundId;

    private final boolean makesAtmosphericSound;

    private final String animalName;

    Sound(String resNam, String confName, int Id, boolean makesSound, String name) {
        resourceName = resNam;
        configName = confName;
        soundId = Id;
        makesAtmosphericSound = makesSound;
        animalName = name;
    }

    String getResourceName() {
        return resourceName;
    }

    String getConfigName() {
        return configName;
    }

    int getSoundId() {
        return soundId;
    }

    boolean getMakesSound() {
        return makesAtmosphericSound;
    }

    String getAnimalName() {
        return animalName;
    }
}
