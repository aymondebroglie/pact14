import java.sql.*;
import java.util.ArrayList;
import java.util.Date;





public class BDD implements BDDInterface
{
	Connection con ;
    Statement st,st2 ;
    ResultSet rs,rs2 ;
    
    private String echapper(String string)
    {
		return string.replace("'", "''");
    	
    }
	public BDD(String dbname, String user, String mdp)
	{
		 try {
			 Class.forName("com.mysql.jdbc.Driver").newInstance();
	            con = DriverManager.getConnection("jdbc:mysql://localhost/"+dbname+"?" +
                        "user="+user+"&password="+mdp);
	            st = con.createStatement();
	            st2 =con.createStatement();
               System.out.println("Connection avec la Base De Données "+dbname+" établie.");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}
	
	@Override
	public boolean ajouterConsommation(int bluetoothID, int rFID, int volume) 
	{
		int cPK=0, ancienVolume;
		long codeBarre=0;
		try
		{
			rs=st.executeQuery("SELECT CodeBarre FROM Associe WHERE Associe.BluetoothID='"+bluetoothID+"'");
			if(!rs.next())
				throw(new Exception("Pas de goulot associé à l'identifiant bluetooth "+bluetoothID));
			codeBarre=rs.getLong(1);
			
			rs=st.executeQuery("SELECT CPK FROM Barman WHERE Barman.RFID='"+rFID+"'");
			if(!rs.next())
				throw(new Exception("Pas de barman associé à l'identifiant RFID "+rFID));
			cPK=rs.getInt(1);
			if(codeBarre==0)
				throw(new Exception("Pas de boisson associée au goulot d'identifiant bluetooth "+bluetoothID));
			if(cPK==0)
			{
				rs=st.executeQuery("SELECT CPK FROM Servi");
				while(rs.next())
					cPK=Math.max(rs.getInt(1),cPK);
				cPK++;
				st.executeUpdate("INSERT INTO Commande (CPK,Date,Prix) VALUES ('"+cPK+"','"+new Timestamp(0)+"','0.0')");
				st.executeUpdate("UPDATE Barman SET CPK="+cPK+" WHERE RFID="+rFID);
			}
			st.executeUpdate("UPDATE Commande SET Date='"+new Timestamp(new Date().getTime())+"' WHERE CPK="+cPK);
			rs=st.executeQuery("SELECT Volume " +
					"FROM Composition " +
					"WHERE Composition.CPK="+cPK+" AND Composition.CodeBarre="+codeBarre);
			if(rs.next())
			{	
				ancienVolume=rs.getInt(1);
				st.executeUpdate("UPDATE Composition SET Volume="+(ancienVolume+volume)+" WHERE Composition.CPK="+cPK+" AND Composition.CodeBarre="+codeBarre);
			}
			else
			st.executeUpdate("INSERT INTO Composition (CodeBarre, CPK, Volume) VALUES ('"+codeBarre+"','"+cPK+"','"+volume+"')");
			System.out.println("La consommation a bien été ajoutée.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean finDeCommande(int rFID) {
		// TODO Auto-generated method stub
		//ajouter une entrée dans Servi
		//passer le CPK de barman à 0
		long cpk;
		try{
		rs=st.executeQuery("SELECT CPK FROM Barman WHERE RFID="+rFID);
		if(!rs.next())
			throw(new Exception("Pas de Barman associé à l'identifiant rFID "+rFID));
		cpk=rs.getLong(1);
		}catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
//********************************
		try {
			st.executeUpdate("INSERT INTO Servi(CPK, RFID )" + 
					"VALUES ('"+cpk+"','"+rFID+"')");
			String updateSql = "UPDATE Barman SET CPK="+0+" WHERE RFID="+rFID;
		int updateResultat=st.executeUpdate(updateSql);
		System.out.println("UPDATE:" + updateResultat);
			} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
			}
//****************************************
		System.out.println("La commande numéro "+ cpk +" a bien été enregistrée");
		
		return true;
	}


	@Override
	public boolean ajouterGoulot(int bluetooth) 
	{
	/*Dans 'Associe', on a une liste de Goulots, à chaque changement, 
	 on change BluettoothID associé a chaque goulot*/
		try {
			st.executeUpdate("INSERT INTO Goulots (BluetoothID, EnCharge, NiveauDeCharge )" +
					"VALUES ('"+bluetooth+"','0','1.0')");
			st.executeUpdate("INSERT INTO Associe (CodeBarre, BluetoothID )" + 
					"VALUES ('0','"+bluetooth+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("Un goulot d'identifiant bluetooh "+bluetooth+" a été ajouté.");
		return true;
		
	}
	
	public boolean modifierGoulot(int bluetooth,int enCharge, float niveauDeCharge)
	{ /*Ici on modifie pas bluetooth d'un goulot*/  
		String updateSql = "UPDATE Goulots SET EnCharge="+enCharge+",NiveauDeCharge="
				+ niveauDeCharge+ "WHERE BluetoothID ="+bluetooth;
		try {
			int updateResultat=st.executeUpdate(updateSql);
			System.out.println("UPDATE:" + updateResultat);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
	}
		System.out.println("Le goulot "+bluetooth+" a été mis à jour avec succès");
		return true;
	}
	
	public boolean supprimerGoulot(int bluetooth)
	{  /*si bien supprimer, on affiche 1*/
		String sql = "DELETE FROM Goulots WHERE BluetoothID = "+bluetooth;
		try
		{
		int deleteRes = st.executeUpdate(sql);
		st.executeUpdate("DELETE FROM Associe WHERE BluetoothID = "+bluetooth);
		 System.out.print("DELETE:" + deleteRes);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
	}return true;
	}

	@Override
	public boolean ajouterBarman(int rFID, String nom, String prenom,
			int age, java.util.Date dateEmbauche) 
	{
		java.sql.Timestamp sqlTime= new java.sql.Timestamp(dateEmbauche.getTime());
		String sql = "INSERT INTO Barman (RFID, Nom, Prenom,Age, DateEmbauche,CPK)" +
				"VALUES ('"+rFID+"','"+echapper(nom)+"','"+echapper(prenom)+"','"+age+"','"+sqlTime+"','0')";
		try {
			st.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("Barman ajouté.");
		return true;
	}

	@Override
	public boolean modifierInformationBarman(int rFID, String nom,
			String prenom, int age, java.util.Date dateEmbauche) 
	{/*Ici on suppose dateEmbauche doit être mis à maintenant quand on fait modification*/
		java.sql.Timestamp sqlTime= new java.sql.Timestamp(dateEmbauche.getTime());
		String updateSql = "UPDATE Barman SET Nom='"
				+ echapper(nom)+"', Prenom='"+echapper(prenom)+"',Age= '"+age+"',DateEmbauche='" +sqlTime+"' WHERE RFID='"+rFID+"'";
		try {
		int updateResultat=st.executeUpdate(updateSql);
		if(updateResultat==0){
			System.out.println("Barman introuvable");
			return false;
		 }
		 System.out.println("Barman mis à jour");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
}return true;

	}


	@Override
	public boolean supprimerBarman(int rFID) {
		String sql = "DELETE FROM Barman WHERE RFID = "+rFID;
		try
		{
		int deleteRes = st.executeUpdate(sql);
		 if(deleteRes==0){
			System.out.println("Barman introuvable");
			return false;
		 }
		 System.out.println("Barman supprimé");
		 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
	}return true;
	}

	@Override
/*NEGLIGER LES COMMENTAIRES DE CE METHODE, TOUT EST BON MAINTENANT*/
	public boolean ajouterBoisson(long codeBarre, String nom, String marque,int volume,
			int degre) 
	{
	 /*PAS CLAIRE POUR FONCTIONNALITE(-Yunzhi)*/
	 /*Ici on crée dernierDate dans ce méthode, et on juste ajoute 1 line d'information,
	   donc généralement, on a juste un boisson lié à un data. Modifier après ou 
	   rest comme ça*/
		java.sql.Timestamp derniereDate=new java.sql.Timestamp(0), maintenant=new java.sql.Timestamp(new java.util.Date().getTime());
		String sql = "INSERT INTO Boisson (CodeBarre,Nom,Marque,Volume, Degre)" +
				"VALUES ('"+codeBarre+"','"+echapper(nom)+"','"+echapper(marque)+"','"+volume+"','"+degre+"')";
		
		try {
			st.executeUpdate(sql);
			rs=st.executeQuery("SELECT Date FROM Stock");
			while(rs.next())
			{
	/*?*/			derniereDate=rs.getTimestamp(1).after(derniereDate)?rs.getTimestamp(1):derniereDate;
			}
			st.executeUpdate("INSERT INTO Stock (Date) VALUES ('"+maintenant+"')");
			rs=st.executeQuery("SELECT CodeBarre, Volume FROM Disponibilite WHERE Date='"+derniereDate+"'");
			
			while(rs.next())
			{
				st2.executeUpdate("INSERT INTO Disponibilite (Date, CodeBarre, Volume) VALUES ('"+maintenant+"','"+rs.getLong(1)+"','"+rs.getInt(2)+"')");
			}
			st.executeUpdate("INSERT INTO Disponibilite (Date, CodeBarre, Volume) VALUES ('"+maintenant+"','"+codeBarre+"','0')");
			System.out.println("La boisson "+nom+" a bien été ajoutée");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}return true;
	}
	
//	public boolean ajouterBoisson(long codeBarre, String nom, String marque,int volume,
//			int degre) 
//	{/*PAS CLAIRE POUR FONCTIONNALITE(-Yunzhi)*/
//	 /*Ici on crée dernierDate dans ce méthode, et on juste ajoute 1 line d'information,
//	   donc généralement, on a juste un boisson lié à un data. Modifier après ou 
//	   rest comme ça*/
//
///*Dans cette méthode, je pense on dois supposer le Boisson n'existe pas dans notre base de donnes*/
//		java.sql.Timestamp  maintenant=new java.sql.Timestamp(new java.util.Date().getTime());
//		String sql = "INSERT INTO Boisson (CodeBarre,Nom,Marque,Volume, Degre)" +
//				"VALUES ('"+codeBarre+"','"+echapper(nom)+"','"+echapper(marque)+"','"+volume+"','"+degre+"')";
//		
//		try {
//			st.executeUpdate(sql);
//			st.executeUpdate("INSERT INTO Stock (Date) VALUES ('"+maintenant+"')");
//			st.executeUpdate("INSERT INTO Disponibilite (Date, CodeBarre, Volume) VALUES ('"+maintenant+"','"+codeBarre+"','0')");
//			System.out.println("La boisson "+nom+" a bien été ajoutée");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}return true;
//	}

	@Override
	public boolean bouteilleFinie(int bluetoothID) 
	{
		//A COMPLETER
		java.sql.Timestamp derniereDate=new java.sql.Timestamp(0), maintenant=new java.sql.Timestamp(new java.util.Date().getTime());
		long codeBarre=0;
		int ancienVolume;
		
		try {
			rs=st.executeQuery("SELECT Date FROM Stock");
			while(rs.next())
			{
				derniereDate=rs.getTimestamp(1).after(derniereDate)?rs.getTimestamp(1):derniereDate;
			}
			rs=st.executeQuery("SELECT CodeBarre, Volume FROM Disponibilite WHERE Date="+derniereDate);
			st.executeUpdate("INSERT INTO Stock (Date) VALUES ('"+maintenant+"'");
			while(rs.next())
			{/*a cause de un date a juste un boisson, donc on a qu'une resultat;
			  Et bien remarquer l'importance d'utilisation de st2*/
				st2.executeUpdate("INSERT INTO Disponibilite (Date, CodeBarre, Volume) VALUES ('"+maintenant+"','"+rs.getLong(1)+"','"+rs.getInt(2));
			}
			////
			rs=st.executeQuery("SELECT CodeBarre FROM Associe WHERE BluetoothID="+bluetoothID);
			if(!rs.next())
				throw new Exception("Il n'y a pas de goulot associé au bluetooth "+bluetoothID);
			codeBarre=rs.getLong(1);
			
			rs=st.executeQuery("SELECT Volume FROM Disponibilite WHERE CodeBarre="+codeBarre+" AND Date="+maintenant);
			if(!rs.next())
				throw new Exception("Boisson introuvable dans les stocks");
			ancienVolume=rs.getInt(1);
			st.executeUpdate("UPDATE Disponibilite SET Volume="+ancienVolume--+" WHERE CodeBarre="+codeBarre+" AND Date="+maintenant);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

//	@Override
//	public boolean bouteilleFinie(int bluetoothID) 
//	{ /*il faut trouver derniereDate concernant un boisson que l'on consomme maintenant;
//	    De plus, je suis pas sur table 'Stock' est nécessaire ou pas*/
//		//A COMPLETER
//		java.sql.Timestamp derniereDate=new java.sql.Timestamp(0), maintenant=new java.sql.Timestamp(new java.util.Date().getTime());
//		long codeBarre=0;
//		int ancienVolume=0;
//		
//		try {
//			rs=st.executeQuery("SELECT CodeBarre FROM Associe WHERE BluetoothID="+bluetoothID);
//			if(!rs.next())
//				throw new Exception("Il n'y a pas de goulot associé au bluetooth "+bluetoothID);
//			codeBarre=rs.getLong(1);
//			
//			rs=st.executeQuery("SELECT Date FROM Disponibilite Where CodeBarre="+codeBarre);
//			if(!rs.next())
//				throw new Exception("Boisson introuvable dans les stocks");
//			while(rs.next())
//			{
///*?*/				derniereDate=rs.getTimestamp(1).after(derniereDate)?rs.getTimestamp(1):derniereDate;
//			}
//			rs=st.executeQuery("SELECT CodeBarre, Volume FROM Disponibilite WHERE Date="+derniereDate);
//			st.executeUpdate("INSERT INTO Stock (Date) VALUES ('"+maintenant+"'");
//			/*à verifier si INSERT ne retourne pas de résultat dans statement*/
//			while(rs.next())
//			{/*a cause de un date a juste un boisson, donc on a qu'une resultat;
//			  Et bien remarquer l'importance d'utilisation de st2*/
//				ancienVolume=rs.getInt(2);
//				st2.executeUpdate("INSERT INTO Disponibilite (Date, CodeBarre, Volume) VALUES ('"+maintenant+"','"+rs.getLong(1)+"','"+rs.getInt(2));
//			}
//
//			st.executeUpdate("UPDATE Disponibilite SET Volume="+ancienVolume--+" WHERE CodeBarre="+codeBarre+" AND Date="+maintenant);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//	}

	@Override
	public boolean livraison(ArrayList<Livraison> livraison) {
		// TODO Auto-generated method stub
	/**/
		try{
			java.sql.Timestamp derniereDate=new java.sql.Timestamp(0), maintenant=new java.sql.Timestamp(new java.util.Date().getTime());			
				rs=st.executeQuery("SELECT Date FROM Stock");
				while(rs.next())
				{
					derniereDate=rs.getTimestamp(1).after(derniereDate)?rs.getTimestamp(1):derniereDate;
				}
				rs=st.executeQuery("SELECT CodeBarre, Volume FROM Disponibilite WHERE Date="+derniereDate);
				st.executeUpdate("INSERT INTO Stock (Date) VALUES ('"+maintenant+"'");
				while(rs.next())
				{/*a cause de un date a juste un boisson, donc on a qu'une resultat;
				  Et bien remarquer l'importance d'utilisation de st2*/
					st2.executeUpdate("INSERT INTO Disponibilite (Date, CodeBarre, Volume) VALUES ('"+maintenant+"','"+rs.getLong(1)+"','"+rs.getInt(2));
				}
				
			int  ancienVolume=0;
			for(Livraison temp:livraison)
			{
				rs=st.executeQuery("SELECT Volume FROM Disponibilite WHERE CodeBarre="+temp.getcodeBarre()+" AND Date="+maintenant);
				if(!rs.next())
					throw new Exception("Boisson introuvable dans les stocks");
				ancienVolume=rs.getInt(1);
				st.executeUpdate("UPDATE Disponibilite SET Volume="+(ancienVolume+temp.getVolume())+" WHERE CodeBarre="+temp.getcodeBarre()+" AND Date="+maintenant);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean associerGoulot(int bluetoothID, long codeBarre) {
		String sql = "UPDATE Associe SET CodeBarre="+codeBarre+" WHERE BluetoothID="+bluetoothID;
		
		try {
			int res=st.executeUpdate(sql);
			if(res==0){
				System.out.println("Goulot introuvable");
				return false;
			 }
			 System.out.println("Goulot associé");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}return true;
	}
	@Override
	public ArrayList<DispoBoisson> etatDesStocks(Date date) 
	{
		java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime()),derniereDate=new java.sql.Timestamp(0);
		ArrayList<DispoBoisson> result = new ArrayList<DispoBoisson>();
		String nom;
		try {
			rs=st.executeQuery("SELECT Date FROM Stock WHERE Date <= '"+sqlTime+"'");
			while(rs.next())
			{
				System.out.println(sqlTime);
				derniereDate=rs.getTimestamp(1).after(derniereDate)?rs.getTimestamp(1):derniereDate;

			}
			rs=st.executeQuery("SELECT CodeBarre, Volume FROM Disponibilite WHERE Date='"+derniereDate+"'");
			while(rs.next())
			{
				rs2=st2.executeQuery("SELECT Nom FROM Boisson WHERE CodeBarre='"+rs.getLong(1)+"'");			
				rs2.next();
				nom=rs2.getString(1);
				result.add(new DispoBoisson(nom,rs.getInt(2)));
			}
			return result;
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	@Override
	public int attributionDeGoulot() {
		// TODO Auto-generated method stub
		/*On suppose il va distribuer qu'une goulot*/
		int codeBarre=0;
		try{
			float niveauChargeMax=0;
			rs=st.executeQuery("SELECT BluetoothID,NiveauDeCharge FROM Goulots");
			while(rs.next())
			{
				if(rs.getFloat(2)>niveauChargeMax)
				{
					niveauChargeMax=rs.getFloat(2);
					codeBarre=rs.getInt(1);
				}
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return codeBarre;
	}
	@Override
	public boolean ajoueterCocktail(long coPK, String nom, float prix,
			ArrayList<DispoBoisson> recette) {
	/*On change l'attribut dans table Recette de CodeBarre a Nom?*/
		try{
			for(DispoBoisson temp:recette)
			{
				st.executeQuery("INSERT INTO Recette(Nom,CoPK,Volume) VALUES ('"+echapper(temp.getBoisson())+"','"+coPK+"','"+temp.getVolume());
			}
			st.executeQuery("INSERT INTO Cocktail(CoPK,Nom,Prix) VALUES ('"+coPK+"','"+echapper(nom)+"','"+prix);
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
