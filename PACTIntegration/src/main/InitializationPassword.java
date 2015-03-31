package main;

import java.io.File;
import java.io.PrintStream;

import javax.swing.JOptionPane;

public abstract class InitializationPassword 
{
	public static void initialiserPassword(String mdp) 
	{	
		
		try
		{
			int crypted = mdp.hashCode() ;
			PrintStream ps =new PrintStream("datas" + File.separator + "Password") ; 
			ps.print(crypted);
			JOptionPane.showMessageDialog(null, "Password has been successfully updated", "youpi", JOptionPane.INFORMATION_MESSAGE);
			ps.close() ;
		}
		catch(Exception e)
		{
			System.err.println(e) ;
			e.printStackTrace(System.err);
			JOptionPane.showMessageDialog(null, "MainPassWordGenerator failed", "Fatal Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
}

