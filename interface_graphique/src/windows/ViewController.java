package windows;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewController {//Classe permettant de controller ce qui se passe quand on appuie sur un bouton, c'est elle qui
	//interrogera la base de donn�e 
	private Window window;

public ViewController(Window window){
	this.window = window;
}

public void bouttonBarman(){//M�thode appel�e quan on appui sur Barman sur l'�cran d'acceuil
	ViewBarmanHome vbh = new ViewBarmanHome(this);
	 window.setContentPane(vbh);
	window.validate();
}

public void bouttonGestionnaire(){//{//M�thode appel�e quan on appui sur Barman sur l'�cran d'acceuil
	ViewBossHome vbh = new ViewBossHome();
	 window.setContentPane(vbh);
	 window.validate();
}

public void imprimerNote(){//M�thode appel�e si on appuie sur imprimer note dans l'�cran du Barman
	//Code pour le test, il faudra demander a la base de donn�e de nous fournir la note pour le serveur donn�
	JPanel pan = new JPanel();
	pan.add(new JLabel("r�ussi"));
	window.setContentPane(pan);
	window.validate();
}

public void retirerGoulot(){
	//Demander quel goulot il faut prendre pour l'instant il ferme la fen�tre pour le test
	window.dispose();
	
}

public void motDePasse(String message){
	//Il faut interroger la base de donn�e pour savoir si le mot de passe est le bon 
	JPanel pan = new JPanel();
	pan.add(new JLabel(message));
	window.setContentPane(pan);
	window.validate();
	
}
}
