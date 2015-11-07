package breakthrough;

public class MVCData {
    public int stunde;
    public int minute;
    public int sekunde;

    public MVCData() {
          stunde = minute = sekunde = 0;
    } // Konstruktor

    public MVCData(int stunde, int minute, int sekunde) {
          this.stunde = stunde;
          this.minute = minute;
          this.sekunde = sekunde;
    } // Konstruktor
    
	public int getHours() {
		return stunde;
	} // getHours
	
	public int getMinutes() {
		return minute;
	} // getMinutes

	public int getSeconds() {
		return sekunde;
	} // getSeconds
}
