package mains;

import java.io.File;
import java.io.PrintStream;

import javax.swing.JOptionPane;

public class ReenitialisationMDP {

	public static void main(String[] args) 
	{
		try
        {
			int crypted = "poney".hashCode() ;
			PrintStream ps =new PrintStream("datas" + File.separator + "Password") ; 
        	ps.print(crypted);
        	JOptionPane.showMessageDialog(null, "MainPassWordGenerator has been successful", "youpi", JOptionPane.INFORMATION_MESSAGE);
        	ps.close() ;
        }
        catch(Exception e)
        {
        	System.err.println(e) ;
            e.printStackTrace(System.err);
            JOptionPane.showMessageDialog(null, "MainPassWordGenerator failed", "Fatal Error", JOptionPane.ERROR_MESSAGE);
        }
		

			System.out.println("poney".hashCode()) ;
		

	}

}
