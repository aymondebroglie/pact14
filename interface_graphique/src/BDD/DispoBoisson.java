package BDD;

public class DispoBoisson {
	private final String boisson;
	private final int volume; // en nombre de bouteilles non finies (je sais
								// normalement il faudrait faire en nombre de
								// bouteilles non entamées mais bon)

	public DispoBoisson(String boisson, int volume) {
		this.boisson = boisson;
		this.volume = volume;
	}

	public String getBoisson() {
		return boisson;
	}

	public int getVolume() {
		return volume;
	}

}
