# Animal Sounds Plugin
##### A plugin for [RuneLite](https://runelite.net/)
Animals occasionally make sounds when you're nearby
___

## Customize your Sounds

### 1. Locate your `.runelite` folder

On windows this is likely to be here: `C:\Users\<your username>\.runelite`. The sound files are located in the `animal-sounds` folder within the runelite folder.

### 2. Prepare your sound files

Make sure your files are all converted to `.wav` format, and that the file name __exactly__ matches the name of the existing file you want to replace.

The filenamess are just named as the name of the corresponding animal (eg. "Cow.wav"). Note that any files with unexpected names in the `animal-sounds` folder will be deleted

### 3. Reverting to Default

If you replace an existing file in `animal-sounds` using exactly the same file name, your sound will be loaded instead. To revert to the default sound file, delete the custom sound file and the default file will be re-downloaded the next time the plugin starts up.

## Configuration Options

- Radius (how close you need to be to the animal for the sound to trigger, default 10)
- Volume (volume the sound will play at, default 75)
- Cows
- Chickens
- Sheep
- Frogs
- Ducks

### Potential future expansions

- Adding multiple sound variations per animal type
- Adding support for additional animals
