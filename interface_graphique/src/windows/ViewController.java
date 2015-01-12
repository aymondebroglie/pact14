package windows;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ViewController {//Classe permettant de controller ce qui se passe quand on appuie sur un bouton, c'est elle qui
	//interrogera la base de donnée 
	private Window window;

public ViewController(Window window){
	this.window = window;
}

public void bouttonBarman(){//Méthode appelée quan on appui sur Barman sur l'écran d'acceuil
	ViewBarmanHome vbh = new ViewBarmanHome(this);
	 window.setContentPane(vbh);
	window.validate();
}

public void bouttonGestionnaire(){//{//Méthode appelée quan on appui sur Barman sur l'écran d'acceuil
	ViewBossHome vbh = new ViewBossHome();
	 window.setContentPane(vbh);
	 window.validate();
}

public void imprimerNote(){//Méthode appelée si on appuie sur imprimer note dans l'écran du Barman
	//Code pour le test, il faudra demander a la base de donnée de nous fournir la note pour le serveur donné
	JPanel pan = new JPanel();
	pan.add(new JLabel("réussi"));
	window.setContentPane(pan);
	window.validate();
}

public void retirerGoulot(){
	//Demander quel goulot il faut prendre pour l'instant il ferme la fenêtre pour le test
	window.dispose();
	
}

public void motDePasse(String message){
	//Il faut interroger la base de donnée pour savoir si le mot de passe est le bon 
	JPanel pan = new JPanel();
	pan.add(new JLabel(message));
	window.setContentPane(pan);
	window.validate();
	
}
}
