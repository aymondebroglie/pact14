package bdd;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

import websem.OFFInterface;
import websem.OpenFoodFacts;





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
               System.out.println("Connection avec la Base De Donnée "+dbname+" etablie.");
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }
	}
	
	@Override
	public boolean ajouterConsommation(String bluetoothID, int rFID, int volume) 
	{
		int cPK=0, ancienVolume;
		long codeBarre=0;
		try
		{
			rs=st.executeQuery("SELECT CodeBarre FROM Associe WHERE Associe.BluetoothID="+bluetoothID);
			if(!rs.next())
				throw(new Exception("Pas de goulot associe a� l'identifiant bluetooth "+bluetoothID));
			codeBarre=rs.getLong(1);
			
			rs=st.executeQuery("SELECT CPK FROM Barman WHERE Barman.RFID='"+rFID+"'");
			if(!rs.next())
				throw(new Exception("Pas de barman associe a� l'identifiant RFID "+rFID));
			cPK=rs.getInt(1);
			if(codeBarre==0)
				throw(new Exception("Pas de boisson associee au goulot d'identifiant bluetooth "+bluetoothID));
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
			System.out.println("La consommation a bien ete ajoutee.");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public float finDeCommande(int rFID) {
		// TODO Auto-generated method stub
		//ajouter une entrée dans Servi
		//passer le CPK de barman à 0
		long cpk;
		float prixTotal=0.00f;
		try{
		rs=st.executeQuery("SELECT CPK FROM Barman WHERE RFID="+rFID);
		if(!rs.next())
			throw(new Exception("Pas de Barman associe à l'identifiant rFID "+rFID));
		cpk=rs.getLong(1);
			st.executeUpdate("INSERT INTO Servi(CPK, RFID )" + 
					"VALUES ('"+cpk+"','"+rFID+"')");
			String updateSql = "UPDATE Barman SET CPK="+0+" WHERE RFID="+rFID;
		int updateResultat=st.executeUpdate(updateSql);
		System.out.println("UPDATE:" + updateResultat);
		
/*Ici pour prix*/
		long codeBarre=0;
		int volume=0;
		rs=st.executeQuery("SELECT CodeBarre,Volume From Composition WHERE CPK="+cpk);
		while(rs.next())
		{
			codeBarre=rs.getLong(1);
			volume=rs.getInt(2);
			rs2=st2.executeQuery("SELECT Prix From Boisson WHERE CodeBarre="+codeBarre);
			if(rs2.next())
				prixTotal+=volume*rs2.getFloat(1);
		}
		prixTotal=Math.round(100*prixTotal)/100.0f;
		st.executeUpdate("UPDATE Commande SET Prix="+prixTotal+"WHERE CPK="+cpk);
/*fin de prix*/

			} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 0f;
			}
//****************************************
		System.out.println("La commande numero "+ cpk +" a bien ete enregistree");
		
		return prixTotal;
	}


	@Override
	public boolean ajouterGoulot(String bluetooth) 
	{
	/*Dans 'Associe', on a une liste de Goulots, à chaque changement, 
	 on change BluettoothID associé a chaque goulot*/
		try {
			st.executeUpdate("INSERT INTO Goulots (BluetoothID, EnCharge, NiveauDeCharge )" +
					"VALUES ('"+bluetooth+"','1','1.0')");
			st.executeUpdate("INSERT INTO Associe (CodeBarre, BluetoothID )" + 
					"VALUES ('0','"+bluetooth+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("Un goulot d'identifiant bluetooh "+bluetooth+" a ete ajoute.");
		return true;
		
	}
	
	public boolean modifierGoulot(String bluetooth,int enCharge, float niveauDeCharge)
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
		System.out.println("Le goulot "+bluetooth+" a ete mis à jour avec succes");
		return true;
	}
	
	public boolean supprimerGoulot(String bluetooth)
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
		System.out.println("Barman ajoute.");
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
		 System.out.println("Barman supprime");
		 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
	}return true;
	}

	@Override
/*NEGLIGER LES COMMENTAIRES DE CE METHODE, TOUT EST BON MAINTENANT*/
	public boolean ajouterBoisson(long codeBarre, String nom, String marque,int volume,
			float degre) 
	{
	 /*PAS CLAIRE POUR FONCTIONNALITE(-Yunzhi)*/
	 /*Ici on crée dernierDate dans ce méthode, et on juste ajoute 1 line d'information,
	   donc généralement, on a juste un boisson lié à un data. Modifier après ou 
	   rest comme ça*/
		java.sql.Timestamp derniereDate=new java.sql.Timestamp(0), maintenant=new java.sql.Timestamp(new java.util.Date().getTime());
		String sql = "INSERT INTO Boisson (CodeBarre,Nom,Marque,Volume, Degre, Prix)" +
				"VALUES ('"+codeBarre+"','"+echapper(nom)+"','"+echapper(marque)+"','"+volume+"','"+degre+"','0.0')";
		
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
			System.out.println("La boisson "+nom+" a bien ete ajoutee");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return false;
		}return true;
	}

	@Override
	public boolean bouteilleFinie(String bluetoothID) 
	{
		java.sql.Timestamp derniereDate=new java.sql.Timestamp(0), maintenant=new java.sql.Timestamp(new java.util.Date().getTime());
		long millisecondes=maintenant.getTime()%1000;
		millisecondes=millisecondes>500?maintenant.getTime()-millisecondes:maintenant.getTime()-millisecondes+1000;
		maintenant=new java.sql.Timestamp(millisecondes);
		long codeBarre=0;
		int ancienVolume;
		
		try {
			rs=st.executeQuery("SELECT Date FROM Stock");
			while(rs.next())
			{
				derniereDate=rs.getTimestamp(1).after(derniereDate)?rs.getTimestamp(1):derniereDate;
			}
			st.executeUpdate("INSERT INTO Stock (Date) VALUES ('"+maintenant+"')");
			rs=st.executeQuery("SELECT CodeBarre, Volume FROM Disponibilite WHERE Date='"+derniereDate+"'");
			while(rs.next())
			{//on a autant de résulats que de boisson
				st2.executeUpdate("INSERT INTO Disponibilite (Date, CodeBarre, Volume) VALUES ('"+maintenant+"','"+rs.getLong(1)+"','"+rs.getInt(2)+"')");
			}
			////
			rs=st.executeQuery("SELECT CodeBarre FROM Associe WHERE BluetoothID="+bluetoothID);
			if(!rs.next())
				throw new Exception("Il n'y a pas de goulot associe au bluetooth "+bluetoothID);
			codeBarre=rs.getLong(1);
			
			rs=st.executeQuery("SELECT Volume FROM Disponibilite WHERE CodeBarre="+codeBarre+" AND Date='"+maintenant+"'");
			if(!rs.next())
				throw new Exception("Boisson introuvable dans les stocks");
			ancienVolume=rs.getInt(1);
			st.executeUpdate("UPDATE Disponibilite SET Volume="+(ancienVolume-1)+" WHERE CodeBarre="+codeBarre+" AND Date='"+maintenant+"'");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean livraison(ArrayList<Livraison> livraison) {
		// TODO Auto-generated method stub
	/**/
		try{
			java.sql.Timestamp derniereDate=new java.sql.Timestamp(0), maintenant=new java.sql.Timestamp(new java.util.Date().getTime());	
			long millisecondes=maintenant.getTime()%1000;
			millisecondes=millisecondes>500?maintenant.getTime()-millisecondes:maintenant.getTime()-millisecondes+1000;
			maintenant=new java.sql.Timestamp(millisecondes);
			rs=st.executeQuery("SELECT Date FROM Stock");
			while(rs.next())
			{
				derniereDate=rs.getTimestamp(1).after(derniereDate)?rs.getTimestamp(1):derniereDate;
			}
			st.executeUpdate("INSERT INTO Stock (Date) VALUES ('"+maintenant+"')");
			rs=st.executeQuery("SELECT CodeBarre, Volume FROM Disponibilite WHERE Date='"+derniereDate+"'");
			
			while(rs.next())
			{
				st2.executeUpdate("INSERT INTO Disponibilite (Date, CodeBarre, Volume) VALUES ('"+maintenant+"','"+rs.getLong(1)+"','"+rs.getInt(2)+"')");
			}
				
			int  ancienVolume=0;
			for(Livraison temp:livraison)
			{
				String query="SELECT Volume FROM Disponibilite WHERE CodeBarre="+temp.getcodeBarre()+" AND Date='"+maintenant+"'"  ;
				rs=st.executeQuery(query);
				if(!rs.next())
					throw new Exception("Boisson " +temp.getcodeBarre()+" introuvable dans les stocks");
					
				ancienVolume=rs.getInt(1);
				st.executeUpdate("UPDATE Disponibilite SET Volume="+(ancienVolume+temp.getVolume())+" WHERE CodeBarre="+temp.getcodeBarre()+" AND Date='"+maintenant+"'");
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		System.out.println("Livraison bien enregistree");
		return true;
	}

	@Override
	public boolean associerGoulot(String bluetoothID, long codeBarre) {
		String sql = "UPDATE Associe SET CodeBarre="+codeBarre+" WHERE BluetoothID="+bluetoothID;
		
		try {
			int res=st.executeUpdate(sql);
			if(res==0){
				System.out.println("Goulot introuvable");
				return false;
			 }
			 System.out.println("Goulot associe");
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
	public String attributionDeGoulot() {
		// TODO Auto-generated method stub
		/*On suppose il va distribuer qu'une goulot*/
		String bluetoothID="0000000000";
		try{
			float niveauChargeMax=0;
			rs=st.executeQuery("SELECT BluetoothID,NiveauDeCharge,EnCharge FROM Goulots");
			while(rs.next())
			{
				if(rs.getFloat(2)>niveauChargeMax&&rs.getInt(3)==1)
				{
					niveauChargeMax=rs.getFloat(2);
					bluetoothID=rs.getString(1);
				}
			}
			st.executeUpdate("UPDATE Goulots SET EnCharge=0 WHERE BluetoothID="+bluetoothID);
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "0000000000";
		}
		
		return bluetoothID;
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
	@Override
	public boolean boissonConnue(long codeBarre) 
	{
		try {
			rs=st.executeQuery("SELECT CodeBarre FROM Boisson WHERE CodeBarre=+"+codeBarre);
			if(!rs.next())
				return false;
			return true;
		} catch (SQLException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public ArrayList<HistoBoisson> evolutionDesStocks(String boisson, Date dateDebut, Date dateFin) 
	{
		long codeBarre;
		Timestamp debut=new Timestamp(dateDebut.getTime()), fin = new Timestamp(dateFin.getTime());
		ArrayList<HistoBoisson> result=new ArrayList<HistoBoisson>();
		try {
			rs=st.executeQuery("SELECT CodeBarre FROM Boisson WHERE Nom='"+echapper(boisson)+"'");
			if(!rs.next())
				throw new Exception("Boisson introuvable");
			codeBarre=rs.getLong(1);
			rs=st.executeQuery("SELECT Date,Volume FROM Disponibilite WHERE CodeBarre="+codeBarre+" AND Disponibilite.Date BETWEEN '"+ debut+"' AND '"+fin+"'");
			while(rs.next())
				result.add(new HistoBoisson(new Date(rs.getTimestamp(1).getTime()),rs.getInt(2)));
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<String> listeDesBoissons() {
		ArrayList<String> result = new ArrayList<String>();
		try {
			rs=st.executeQuery("SELECT Nom FROM Boisson");
			while(rs.next())
				result.add(rs.getString(1));
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean ajouterBoissonParWeb(long codeBarre) {
		OFFInterface openfood=new OpenFoodFacts(this);
		openfood.ajouterBoisson(codeBarre);
		return true;
	}
	@Override
	public boolean initialisation() {
		try
		{
		st.executeUpdate("DELETE FROM Associe");
		st.executeUpdate("DELETE FROM Barman");
		st.executeUpdate("DELETE FROM Boisson");
		st.executeUpdate("DELETE FROM Cocktail");
		st.executeUpdate("DELETE FROM Commande");
		st.executeUpdate("DELETE FROM Composition");
		st.executeUpdate("DELETE FROM Contenue");
		st.executeUpdate("DELETE FROM Disponibilite");
		st.executeUpdate("DELETE FROM Goulots");
		st.executeUpdate("DELETE FROM Recette");
		st.executeUpdate("DELETE FROM Servi");
		st.executeUpdate("DELETE FROM Stock");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
	}return true;
	}
	
	@Override
	public boolean setPrixParBoisson(long codeBarre,float prix) {	
	    try{ 
		rs=st.executeQuery("SELECT Prix From Boisson WHERE CodeBarre="+codeBarre);
		if(!rs.next())
			throw new Exception("Boisson introuvable");
		 st.executeUpdate("UPDATE Boisson SET Prix="+prix+" WHERE CodeBarre="+codeBarre);	
	    }catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
        }return true;
    }

	@Override
	public ArrayList<DetailDeCommand> imprimerCommande(int rFID) {
		long cpk;
		ArrayList<DetailDeCommand> list =new ArrayList<DetailDeCommand>();
		try{
		rs=st.executeQuery("SELECT CPK FROM Barman WHERE RFID="+rFID);
		if(!rs.next())
			throw(new Exception("Pas de Barman associe à l'identifiant rFID "+rFID));
		cpk=rs.getLong(1);
		rs=st.executeQuery("SELECT CodeBarre, Volume FROM Composition WHERE CPK="+cpk);
		while(rs.next())
		{
			rs2=st2.executeQuery("SELECT Nom,Prix FROM Boisson WHERE CodeBarre="+rs.getLong(1));
			rs2.next();
			list.add(new DetailDeCommand(rs2.getString(1),rs.getInt(2),Math.round(100*(rs.getInt(2)*rs2.getFloat(2)))/100.0f));//ici j'arrondi à deux chiffre car par exemple j'avais mis 5*0.24 et obtenu 1.999999 
			
		}}catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
			
		return list;
	}
	@Override
	public long codeBarreDeBoisson(String nom) {
		long code;
		try{
			rs=st.executeQuery("SELECT CodeBarre FROM Boisson WHERE Nom='"+echapper(nom)+"'");
			if(!rs.next())
				throw(new Exception("Pas de CodeBarre associe à "+nom));
			code=rs.getLong(1);
		}catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
		return code;
	}
	@Override

	public int volumeDateBoisson(Date date, String boisson) 
	{
		java.sql.Timestamp sqlTime=new java.sql.Timestamp(date.getTime()),derniereDate=new java.sql.Timestamp(0);
		long codeBarre=this.codeBarreDeBoisson(boisson);
		String nom;
		try {
			rs=st.executeQuery("SELECT Date FROM Stock WHERE Date <= '"+sqlTime+"'");
			while(rs.next())
			{
				derniereDate=rs.getTimestamp(1).after(derniereDate)?rs.getTimestamp(1):derniereDate;

			}
			rs=st.executeQuery("SELECT Volume FROM Disponibilite WHERE Date='"+derniereDate+"' AND CodeBarre="+codeBarre);
			if(!rs.next())
				throw new Exception("Boisson introuvable");
			return rs.getInt(1);
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public ArrayList<HistoBoisson> boissonCommande(String boisson,
			Date dateDebut, Date dateFin) {
		long codeBarre=this.codeBarreDeBoisson(boisson);
		Timestamp debut=new Timestamp(dateDebut.getTime()), fin = new Timestamp(dateFin.getTime());
		ArrayList<HistoBoisson> result=new ArrayList<HistoBoisson>();
		ArrayList<Integer> cPKs = new ArrayList<Integer>();
		ArrayList<Date> dates = new ArrayList<Date>();
		try {
			rs=st.executeQuery("SELECT CPK,Date FROM Commande WHERE Date BETWEEN '"+ debut+"' AND '"+fin+"'");
			while(rs.next())
			{
				cPKs.add(rs.getInt(1));
				dates.add(new Date(rs.getTimestamp(2).getTime()));
			}
			for(int i=0; i<cPKs.size();i++)
			{
				rs=st.executeQuery("SELECT Volume FROM Composition WHERE CPK="+cPKs.get(i)+" AND CodeBarre="+codeBarre);
				if(rs.next())
						result.add(new HistoBoisson(dates.get(i),rs.getInt(1)));
			}
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		// TODO Auto-generated method stub
	}
	@Override
	public boolean setVolumeDeBoisson(int volume,long codebarre) {
		try{
			st.executeUpdate("UPDATE Boisson SET Volume="+volume+" WHERE CodeBarre="+codebarre);	
	}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
        }return true;
	}
}
