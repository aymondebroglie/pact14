package visu;

import java.sql.Date;
import java.util.ArrayList;

import bdd.DispoBoisson;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<DispoBoisson> data = new ArrayList<DispoBoisson>();
		Date date = new Date((long) 1.1);
		Graphique graphique = new Graphique(data, date);

	}

}
