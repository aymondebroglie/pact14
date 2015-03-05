package mains;

import window.Window;
import BDD.BDDInterface;

public class Main {

	public static void main(String[] args) 
	{
			BDDInterface bdd = null;
			@SuppressWarnings("unused")
			Window window = new Window(bdd);
	}
}
