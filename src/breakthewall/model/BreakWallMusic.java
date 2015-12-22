package breakthewall.model;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import breakthewall.BreakWallConfig;

public class BreakWallMusic {
	
	private boolean play = false;
	private String[] musicClips;
	private String musicString, randomMusicFile;
	AudioInputStream audioFile;
	Clip audioClip;
	LineListener audioListener;
	
	public BreakWallMusic() {
		musicClips = BreakWallConfig.backgroundMusic;
		initMusic();
	}
	
	public void initMusic() {
		try {
			randomMusicFile = getRandomClip();
			AudioInputStream audioFile = AudioSystem.getAudioInputStream(BreakWallMusic.class.getResourceAsStream(randomMusicFile));
			audioClip = AudioSystem.getClip();
			audioClip.open(audioFile);
			audioListener = new customLineListener();
			audioClip.addLineListener(audioListener);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println("Please check if music file paths are correct.");
		} 	
	}
	
	public String getRandomClip() {
		try {
		int musicClipLength = musicClips.length;
		int randomClipIndex = (BreakWallModel.randomFromRange(1, musicClipLength)) - 1;
		musicString = musicClips[randomClipIndex];		
		} catch(Exception e) {
			System.out.println("No music file references were defined. Please check Configuration.");
		}
		return musicString;
	}
	
	public void playMusic(boolean play) {
		this.play = play;
		if(play == true) {
			audioClip.start();
		} else {
			audioClip.stop();
		}
		
	}
	
	private class customLineListener implements LineListener  {

		@Override
		public void update(LineEvent event) {
			if((event.getType().toString().equals("Stop")) && (play == true)) {
				initMusic();
				playMusic(true);
			}
		}
		
	}

}
