import java.util.Date;


public class HistoBoisson 
{
	private Date date;
	private int volume;
	public HistoBoisson(Date date, int volume) {
		this.date = date;
		this.volume = volume;
	}
	public Date getDate() {
		return date;
	}
	public int getVolume() {
		return volume;
	}
	
}
