
public class Livraison 
{
	private final int codeBarre;
	private final int volume; //en bouteilles
	public Livraison(int codeBarre, int volume)
	{
		this.codeBarre=codeBarre;
		this.volume=volume;
	}
	public int getcodeBarre() {
		return codeBarre;
	}
	public int getVolume() {
		return volume;
	}
}
