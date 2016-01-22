package breakthewall.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import org.w3c.dom.Document;

public interface RemoteHighscore extends Remote {
	
    public String setRemoteHighscore(String highscoreString) throws RemoteException;
    public String getRemoteHighscore() throws RemoteException;
    public String setHighscoreListLock(boolean isLocked) throws RemoteException;
    public String getHighscoreListLock() throws RemoteException;
}