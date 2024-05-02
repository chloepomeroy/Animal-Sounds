package com.animalsounds;

public enum Sound {

    COW("Cow.wav", "hearCows", 3044, true,"Cow"),
    CHICKEN("Chicken.wav", "hearChickens", 0, false,"Chicken"),
    DUCK("Duck.wav", "hearDucks", 413, true,"Duck"),
    SHEEP("Sheep.wav", "hearSheep", 2053, true,"Sheep"),
    FROG("Frog.wav", "hearFrogs", 0, false,"Frog");

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
