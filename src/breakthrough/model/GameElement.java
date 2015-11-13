package breakthewall.model;

public interface GameElement {
	
	public int getXCoord();
	
	public void setXCoord(int xCoord);
	
	public int getYCoord();
	
	public void setYCoord(int yCoord);
	
	public int getHeight();
	
	public void setHeight(int height);
	
	public int getWidth();
	
	public void setWidth(int width);	
	
	public void setImage(String imagePath);
	
	public String getImage();
	
	public String getId();
	
	public void setId(String id);
	
	public boolean getDestroyedState();
	
	public void setDestroyedState(boolean isDestroyed);

}
