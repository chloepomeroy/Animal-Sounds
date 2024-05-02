package com.animalsounds;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup("example")
public interface AnimalSoundsConfig extends Config
{

	@ConfigItem(
			keyName = "radius",
			name = "Radius",
			description = "When an animal is within this radius from you, their sound will play",
			position = 0
	)
	@Range(
			min=0
	)

	default int radius(){return 10;}

	@ConfigItem(
			keyName = "volume",
			name = "Volume",
			description = "Adjust how loud the animal sounds are",
			position = 1
	)

	default int volume() {
		return 75;
	}

	@ConfigItem(
			keyName = "hearCows",
			name = "Cows",
			description = "A cow sound will play when you are close to a cow",
			position = 2
	)
	default boolean hearCows()
	{
		return true;
	}

	@ConfigItem(
			keyName = "hearChickens",
			name = "Chickens",
			description = "A chicken sound will play when you are close to a chicken",
			position = 3
	)
	default boolean hearChickens()
	{
		return true;
	}

	@ConfigItem(
			keyName = "hearDucks",
			name = "Ducks",
			description = "A duck sound will play when you are close to a duck",
			position = 4
	)
	default boolean hearDucks()
	{
		return true;
	}

	@ConfigItem(
			keyName = "hearSheep",
			name = "Sheep",
			description = "A sheep sound will play when you are close to a sheep",
			position = 5
	)
	default boolean hearSheep()
	{
		return true;
	}

	@ConfigItem(
			keyName = "hearFrogs",
			name = "Frogs",
			description = "A frog sound will play when you are close to a frog",
			position = 6
	)
	default boolean hearFrogs()
	{
		return true;
	}
}
