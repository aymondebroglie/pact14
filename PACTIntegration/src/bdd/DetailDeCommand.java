package bdd;

public class DetailDeCommand 
{
	String boisson;
	int volume;
	float prix;
	public DetailDeCommand(String nom, int volume, float prix) 
	{
		this.volume=volume;
		this.boisson=nom;
		this.prix=prix;
		// TODO Auto-generated constructor stub
	}

	public String getBoisson() 
	{
		// TODO Auto-generated method stub
		return boisson;
	}

	public int getVolume() 
	{
		// TODO Auto-generated method stub
		return volume;
	}

	public float getPrix() 
	{
		// TODO Auto-generated method stub
		return prix;
	}
	public String toString()
	{
		return "Boisson: "+boisson+"\t Volume: "+volume+"cL\t Prix: "+prix+"�\n";
	}

}
