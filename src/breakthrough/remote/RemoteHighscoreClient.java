package breakthewall.remote;

public class RemoteHighscoreClient implements RemoteHighscore {

	@Override
	public HighscoreEntry[] getHighscore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEntry(int score) {
		System.out.println("A new highscore entry has been added to the server. NOT.");
	}

}
