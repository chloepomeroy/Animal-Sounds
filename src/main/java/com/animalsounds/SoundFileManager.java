// package com.github.m0bilebtw;

// import lombok.extern.slf4j.Slf4j;
// import net.runelite.client.RuneLite;
// import okhttp3.HttpUrl;
// import okhttp3.OkHttpClient;
// import okhttp3.Request;
// import okhttp3.Response;

// import java.io.BufferedInputStream;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileNotFoundException;
// import java.io.IOException;
// import java.io.InputStream;
// import java.nio.file.Files;
// import java.nio.file.Path;
// import java.nio.file.Paths;
// import java.nio.file.StandardCopyOption;
// import java.util.Arrays;
// import java.util.HashSet;
// import java.util.Set;
// import java.util.stream.Collectors;

// import javax.inject.Inject;

// @Slf4j
// public abstract class SoundFileManager {

// private static final File DOWNLOAD_DIR = new
// File(RuneLite.RUNELITE_DIR.getPath() + File.separator + "animal-sounds");
// private static final String DELETE_WARNING_FILENAME =
// "EXTRA_FILES_WILL_BE_DELETED_BUT_FOLDERS_WILL_REMAIN";
// private static final File DELETE_WARNING_FILE = new File(DOWNLOAD_DIR,
// DELETE_WARNING_FILENAME);
// private static final HttpUrl RAW_GITHUB =
// HttpUrl.parse("https://raw.githubusercontent.com/chloepomeroy/animal-sounds/sounds");

// @Inject
// private AnimalSoundsConfig config;

// private List<String> getTrueConfigs() {
// if (config.hearChickens()) {

// }
// }

// private boolean getConfigSetting(String settingName) {
// switch (settingName) {
// case "hearCows":
// return config.hearCows();
// case "hearChickens":
// return config.hearChickens();
// case "hearDucks":
// return config.hearDucks();
// case "hearSheep":
// return config.hearSheep();
// case "hearFrogs":
// return config.hearFrogs();
// default:
// return false;
// }
// }

// @SuppressWarnings("ResultOfMethodCallIgnored")
// public static void ensureDownloadDirectoryExists() {
// if (!DOWNLOAD_DIR.exists()) {
// DOWNLOAD_DIR.mkdirs();
// }
// try {
// DELETE_WARNING_FILE.createNewFile();
// } catch (IOException ignored) { }
// }

// public static void downloadAllMissingSounds(final OkHttpClient okHttpClient,
// boolean downloadStreamerTrolls) {
// // Get set of existing files in our dir - existing sounds will be skipped,
// unexpected files will be deleted
// Set<String> filesPresent = getFilesPresent();

// // Download any sounds that are not there
// for (Sound sound : getDesiredSoundList(downloadStreamerTrolls)) {
// String fileNameToDownload = sound.getResourceName();
// if (filesPresent.contains(fileNameToDownload)) {
// filesPresent.remove(fileNameToDownload);
// continue;
// }

// if (RAW_GITHUB == null) {
// log.error("Animal Sounds could not download sounds due to an unexpected null
// RAW_GITHUB value");
// return;
// }
// HttpUrl soundUrl =
// RAW_GITHUB.newBuilder().addPathSegment(fileNameToDownload).build();
// Path outputPath = Paths.get(DOWNLOAD_DIR.getPath(), fileNameToDownload);
// try (Response res = okHttpClient.newCall(new
// Request.Builder().url(soundUrl).build()).execute()) {
// if (res.body() != null)
// Files.copy(new BufferedInputStream(res.body().byteStream()), outputPath,
// StandardCopyOption.REPLACE_EXISTING);
// } catch (IOException e) {
// log.error("Animal Sounds Completed could not download sounds", e);
// return;
// }
// }

// // delete undesired files
// for (String filename : filesPresent) {
// File toDelete = new File(DOWNLOAD_DIR, filename);
// toDelete.delete();
// }
// }

// private static Set<String> getFilesPresent() {
// File[] downloadDirFiles = DOWNLOAD_DIR.listFiles();
// if (downloadDirFiles == null || downloadDirFiles.length == 0)
// return new HashSet<>();

// return Arrays.stream(downloadDirFiles)
// .filter(file -> !file.isDirectory())
// .map(File::getName)
// .filter(filename -> !DELETE_WARNING_FILENAME.equals(filename))
// .collect(Collectors.toSet());
// }

// private static Set<Sound> getDesiredSoundList(boolean includeStreamerTrolls)
// {
// return Arrays.stream(Sound.values())
// .filter(sound -> getConfigSetting(sound.getConfigName())==true)
// .collect(Collectors.toSet());
// }

// public static InputStream getSoundStream(Sound sound) throws
// FileNotFoundException {
// return new FileInputStream(new File(DOWNLOAD_DIR, sound.getResourceName()));
// }
// }