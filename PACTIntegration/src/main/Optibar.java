package main;
import ui.ViewWelcome;
import ui.Window;
import ui.Controller;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import materiel.SerialTest;

import bdd.*;
import visu.*;

public class Optibar {

	public static void main(String[] args)
	{
		BDDInterface bdd = new BDD("BAR", "root", "12345678");
		Window window =new Window(bdd);
		
		
		SerialTest main = new SerialTest();
		main.initialize();
		Thread t=new Thread() {
			public void run() {
				//the following line will keep this app alive for 1000 seconds,
				//waiting for events to occur and responding to them (printing incoming messages to console).
				try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
			}
		};
		t.start();
	}

}
