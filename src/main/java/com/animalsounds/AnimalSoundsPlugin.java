package com.animalsounds;

import com.google.inject.Provides;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import okhttp3.OkHttpClient;

import net.runelite.api.events.AreaSoundEffectPlayed;
import javax.sound.sampled.*;

@Slf4j
@PluginDescriptor(name = "Animal Sounds")
public class AnimalSoundsPlugin extends Plugin {
	public Clip clip;

	@Inject
	private Client client;

	@Inject
	private AnimalSoundsConfig config;

	@Inject
	private ScheduledExecutorService executor;

	@Inject
	private OkHttpClient okHttpClient;

	public boolean isPlaying = false;

	public void silentAnimalsPlaySound() {
		log.info("silent");
		List<String> NPCNames = getNPCNamesInRange();

		for (Sound animalSound : Sound.values()) {
			boolean configSetting = getConfigSetting(animalSound.getConfigName());

			// Checks if animal is found nearby
			boolean animalFound = NPCNames.contains(animalSound.getAnimalName());

			// Checks if alternate version of animal is found nearby (eg. "Blue Sheep")
			boolean alternateAnimalFound = NPCNames.stream()
					.anyMatch(npcName -> npcName.contains(" " + animalSound.getAnimalName()));

			if (configSetting && (alternateAnimalFound || animalFound)) {
				playAnimalSound(animalSound, (config.volume()));
			}
		}
	};

	private List<NPC> getNPCsInRange() {
		LocalPoint currentPosition = client.getLocalPlayer().getLocalLocation();
		return client.getNpcs()
				.stream()
				.filter(npc -> (npc.getLocalLocation().distanceTo(currentPosition) / 128) <= config.radius())
				.collect(Collectors.toList());
	}

	private List<String> getNPCNamesInRange() {
		List<NPC> NPCs = getNPCsInRange();
		return NPCs.stream()
				.map(NPC::getName)
				.collect(Collectors.toList());
	}

	private boolean getConfigSetting(String settingName) {
		switch (settingName) {
			case "hearCows":
				return config.hearCows();
			case "hearChickens":
				return config.hearChickens();
			case "hearDucks":
				return config.hearDucks();
			case "hearSheep":
				return config.hearSheep();
			case "hearFrogs":
				return config.hearFrogs();
			default:
				return false;
		}
	};

	@Override
	protected void startUp() {
		log.info("startUp");
		int delay = ThreadLocalRandom.current().nextInt(0, 11);
		executor.submit(() -> {
			SoundFileManager.ensureDownloadDirectoryExists();
			SoundFileManager.downloadAllMissingSounds(okHttpClient, config);
		});
		executor.scheduleAtFixedRate(this::silentAnimalsPlaySound, delay, 9, TimeUnit.SECONDS);
	}

	@Override
	protected void shutDown() throws Exception {
		// executor.shutdown();
		log.info("Example stopped!");
	}

	@Subscribe
	public void onAreaSoundEffectPlayed(AreaSoundEffectPlayed event) {
		// Get soundID of the sound that's playing
		int soundId = event.getSoundId();
		log.info(String.valueOf(soundId));

		// Get the Player's location and the sound's location
		LocalPoint currentPosition = client.getLocalPlayer().getLocalLocation();
		LocalPoint soundPosition = LocalPoint.fromScene(event.getSceneX(), event.getSceneY());

		// Only replace the sound if it matches config settings
		for (Sound animalSound : Sound.values()) {
			boolean configSetting = getConfigSetting(animalSound.getConfigName());

			if (configSetting && soundId == animalSound.getSoundId()
					&& currentPosition.distanceTo(soundPosition) / 128 <= config.radius()) {
				event.consume();
				playAnimalSound(animalSound, (config.volume()));
			}
		}
	}

	private void playAnimalSound(Sound sound, int volume) {
		// Get the sound file
		String soundFile = sound.getResourceName();
		if (soundFile.length() == 0) {
			return;
		}

		// Don't play the clip if we're already playing a clip
		if (isPlaying) {
			log.info("Already playing");
			return;
		}

		log.info("Playing sound");

		// Close the clip if necessary
		if (clip != null) {
			clip.close();
		}

		// Try to create the input stream
		AudioInputStream inputStream = null;
		try {
			URL url = Paths.get(soundFile).toUri().toURL();
			inputStream = AudioSystem.getAudioInputStream(url);
		} catch (UnsupportedAudioFileException | IOException e) {
			log.warn("Unable to create audio input stream: ", e);
		}

		if (inputStream == null) {
			return;
		}

		// Try to open the clip
		try {
			clip = AudioSystem.getClip();
			clip.open(inputStream);
		} catch (LineUnavailableException | IOException e) {
			log.warn("Could not load sound file: ", e);
		}

		// Set the clip's volume
		FloatControl soundVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float volumeValue = config.volume() - 100;
		soundVolume.setValue(volumeValue);

		// Play the clip
		clip.loop(0);
		isPlaying = true;

		// Change isPlaying when the clip has ended
		clip.addLineListener(e -> {
			if (e.getType() == LineEvent.Type.STOP) {
				isPlaying = false;
				log.info("Done playing sound");
			}
		});
	}

	@Provides
	AnimalSoundsConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(AnimalSoundsConfig.class);
	}
}
