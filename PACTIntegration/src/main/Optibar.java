package main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import bdd.*;
import visu.*;

public class Optibar {

	public static void main(String[] args)
	{
		BDDInterface bdd = new BDD("BAR", "root", "12345678");
		long dayMilli=86400000L;
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(new Date());
		Date maintenant=calendar.getTime();
		calendar.setTimeInMillis(calendar.getTime().getTime()-3*dayMilli);
		Date threeDaysBefore =calendar.getTime();
		ArrayList<ArrayList<HistoBoisson>> data= new ArrayList<ArrayList<HistoBoisson>>();
		data.add(bdd.evolutionDesStocks("Curacao",maintenant,threeDaysBefore));
		ArrayList<String> noms = new ArrayList<String>();
		noms.add("Curacao");
		graphique graph= new graphique(data,noms);
		
	}

}
