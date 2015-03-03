package Interface;

public class Livraison 
{
	private final int rFID;
	private final int volume; //en bouteilles
	public Livraison(int rFID, int volume)
	{
		this.rFID=rFID;
		this.volume=volume;
	}
	public int getrFID() {
		return rFID;
	}
	public int getVolume() {
		return volume;
	}
}
