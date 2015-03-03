package bdd;


public class Livraison 
{
	private final long codeBarre;
	private final int volume; //en bouteilles
	public Livraison(long codeBarre, int volume)
	{
		this.codeBarre=codeBarre;
		this.volume=volume;
	}
	public long getcodeBarre() {
		return codeBarre;
	}
	public int getVolume() {
		return volume;
	}
}
