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

public class BreakWallMusic extends Thread {
	
	private float durationSongMillis;
	private boolean play = false;
	private boolean loopMusic = true;
	private ArrayList<String> musicClips;
	private String musicString, randomMusicFile;
	AudioInputStream audioFile;
	Clip audioClip;
	
	public BreakWallMusic() {
		musicClips = BreakWallConfig.backgroundMusic;
		// initMusicClip();
	}
	
	public void initMusicClip() {
		try {		
			AudioInputStream audioFile = AudioSystem.getAudioInputStream(BreakWallMusic.class.getResourceAsStream(randomMusicFile));
			audioClip = AudioSystem.getClip();
			durationSongMillis = 1000 * audioFile.getFrameLength() / audioFile.getFormat().getFrameRate();
			audioClip.open(audioFile);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		} catch (NullPointerException e3) {
			e3.printStackTrace();
			System.out.println("Please check if music file paths are correct: " + randomMusicFile);
		} catch (LineUnavailableException e4) {
			e4.printStackTrace();
		} 	
	}
	
	public String getRandomClip() {
		try {
		int musicClipLength = musicClips.size();		
		int randomClipIndex = (BreakWallModel.randomFromRange(1, musicClipLength)) - 1;
		musicString = musicClips.get(randomClipIndex);		
		} catch(Exception e) {
			System.out.println("No music file references were defined. Please check Configuration.");
		}
		return musicString;
	}
	
	public void playMusic(boolean play) {
		this.play = play;		
	}
	
	public void stopMusic() {
		audioClip.close();
	}
	
	public void run() {
		Date startTime = new Date();
		Date currentTime = new Date();
		long timeAtThisLoop = 0;
		long delayTime = 0;	
		long tempDelayTime = 0;
		long timeDiff = 0;
		while(loopMusic) {
			if((timeDiff == 0) || (timeDiff > durationSongMillis)) {
				randomMusicFile = getRandomClip();
				initMusicClip();
				startTime = new Date();
				delayTime = 0;
			}
			if(play) {
				audioClip.start();
			}
			do {
				currentTime = new Date();				 
				timeDiff = currentTime.getTime() - (startTime.getTime() + delayTime);
				timeAtThisLoop = currentTime.getTime();
			} while((timeDiff < durationSongMillis) && (play));
			audioClip.stop();
			do {
				tempDelayTime = 0;
				currentTime = new Date();
				tempDelayTime = currentTime.getTime() - timeAtThisLoop;				
			} while(!play);
			delayTime = delayTime + tempDelayTime;			
		}
	}
}
