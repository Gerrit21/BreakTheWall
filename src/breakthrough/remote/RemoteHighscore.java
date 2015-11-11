package breakthewall.remote;

public interface RemoteHighscore {
	
	public HighscoreEntry[] getHighscore();
    public void addEntry(int score);

}
