package breakthewall.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import breakthewall.BreakWallConfig;

/**
 * Class to manage background music of the game.
 * Provides methods to choose a music clip from a given list
 * and methods to start, stop and pause the music.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BreakWallMusic extends Thread {
	
	private float durationSongMillis;
	private boolean play = false;
	private boolean loopMusic = true;
	private ArrayList<String> musicClips;
	private String musicString, randomMusicFile;
	AudioInputStream audioFile;
	Clip audioClip;
	
	/**
	 * Constructor gets a list of music clip references from the configuration class
	 */
	public BreakWallMusic() {
		musicClips = BreakWallConfig.backgroundMusic;
	}
	
	/**
	 * Private method streams a random clip ad an audio clip and calculates the length of the clip.
	 * Also handles various types of possible exceptions.
	 */
	private void initMusicClip() {
		try {		
			AudioInputStream audioFile = AudioSystem.getAudioInputStream(BreakWallMusic.class.getResourceAsStream(randomMusicFile));
			audioClip = AudioSystem.getClip();
			durationSongMillis = 1000 * audioFile.getFrameLength() / audioFile.getFormat().getFrameRate();
			audioClip.open(audioFile);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
			System.out.println("The format of the following audio file is not supported: " + randomMusicFile);
		} catch (IOException e2) {
			e2.printStackTrace();
			System.out.println("File could not be read.");
		} catch (NullPointerException e3) {
			e3.printStackTrace();
			System.out.println("Could not load music file. Please check if music file paths are correct: " + randomMusicFile);
		} catch (LineUnavailableException e4) {
			e4.printStackTrace();
		} 	
	}
	
	/**
	 * Private method retrieves a random sound reference from the list of references.
	 * Throws and exception when no files are found.
	 * 
	 * @return musicString a random music file reference
	 */
	private String getRandomClip() {
		try {
		int musicClipLength = musicClips.size();		
		int randomClipIndex = (BreakWallModel.randomFromRange(1, musicClipLength)) - 1;
		musicString = musicClips.get(randomClipIndex);		
		} catch(Exception e) {
			System.out.println("No music file references were defined. Please check Configuration.");
		}
		return musicString;
	}
	
	/**
	 * Public method plays or pauses the music depending a the parameter:
	 * "true" means play and "false" means pause.
	 * 
	 * @param play parameter to play or pause the music
	 */
	public void playMusic(boolean play) {
		this.play = play;		
	}
	
	/**
	 * Public method stops and closes a music clip entirely.
	 */
	public void stopMusic() {
		audioClip.close();
	}
	
	/**
	 * When starting the thread, the run()-Method constantly checks
	 * if the music should be played or paused. 
	 * The user can pause the background music and continue later on.
	 */
	public void run() {
		Date startTime = new Date();
		Date currentTime = new Date();
		long timeAtThisLoop = 0;
		long delayTime = 0;	
		long tempDelayTime = 0;
		long timeDiff = 0;
		// loop through this while the thread is alive
		while(loopMusic) {
			// after a music clip has been finished, a new clip is randomly chosen
			if((timeDiff == 0) || (timeDiff > durationSongMillis)) {
				randomMusicFile = getRandomClip();
				initMusicClip();
				startTime = new Date();
				delayTime = 0;
			}
			// start the clip when "play" is true
			if(play) {
				audioClip.start();
			}
			// loop while "play" is true and the music clip has not been finished
			do {
				currentTime = new Date();				 
				timeDiff = currentTime.getTime() - (startTime.getTime() + delayTime);
				timeAtThisLoop = currentTime.getTime();
			} while((timeDiff < durationSongMillis) && (play));
			// stop clip
			audioClip.stop();
			// count the elapsed time while clip is paused
			// this loop makes sure the clip may be started
			// where it has been stopped
			do {
				tempDelayTime = 0;
				currentTime = new Date();
				tempDelayTime = currentTime.getTime() - timeAtThisLoop;				
			} while(!play);
			delayTime = delayTime + tempDelayTime;			
		}
	}
}
